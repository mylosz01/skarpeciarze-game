package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.BuildingType;
import com.skarpeta.skarpeciarzegame.Catana;
import com.skarpeta.skarpeciarzegame.WorldMap;
import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.*;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.application.Platform;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

    private final Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT_NUMBER = 5555;
    private WorldMap worldMap;
    public PlayerManager playerList = new PlayerManager();
    Player player = null;

    public Client() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(IP_ADDRESS,PORT_NUMBER);
        OutputStream out = clientSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = clientSocket.getInputStream();
        inputStream = new ObjectInputStream(in);

        //otrzymanie pakietu inicjalizacyjnego
        receiveData();
    }

    public static void makeMove(int playerID,Point newPosition) throws IOException {
        Packet newPacket = new Packet(PacketType.MOVE,playerID,newPosition);
        outputStream.writeObject(newPacket);
        System.out.println(" -> sent "+ newPosition);
    }

    public void sendBuildBuilding(Point fieldPosition, BuildingType buildingType){
        try {
            Packet newPacket = new Packet(PacketType.BUILD, player.playerID,buildingType, fieldPosition);
            outputStream.writeObject(newPacket);
            System.out.println("#CLIENT# Send: " + fieldPosition);
        }
        catch (IOException e){
            System.out.println("Err");
        }
    }

    public void sendRemoveBuilding(Point fieldPosition){
        try {
            new Packet(PacketType.DESTROY_BUILDING, player.playerID, fieldPosition).sendTo(outputStream);
        }
        catch (IOException e){
            System.out.println("Err");
        }
    }

    public void sendRemoveResource(Point position) {
        try {
            Packet newPacket = new Packet(PacketType.DESTROY_RESOURCE,player.playerID, position);
            outputStream.writeObject(newPacket);
            System.out.println("#CLIENT# Send: " + position);
        }
        catch (IOException e){
            System.out.println("Err");
        }
    }

    public void receiveData() throws IOException, ClassNotFoundException {

        Packet packet = (Packet) inputStream.readObject();
        System.out.println("received packet! + "+ packet.packetType);
        switch (packet.packetType) {
            case INIT_MAP -> initMap(packet);
            case INIT_PLAYER -> initPlayer(packet);
            case NEW_PLAYER -> newPlayerJoined(packet);
            case MOVE -> movePlayer(packet);
            case BUILD -> placeBuilding(packet);
            case DESTROY_BUILDING -> destroyBuilding(packet);
            case DESTROY_RESOURCE -> removeResource(packet);
            case DISCONNECT -> playerLeft(packet);
        }
        if(packet.packetType != PacketType.INIT_PLAYER)
            Catana.updateButtonUI();
    }

    private void initMap(Packet packet) {
        {
            worldMap = new WorldMap(packet.sizeMap, packet.seedMap);
            packet.fieldInfo.forEach(e->{
                Resource resource = switch (e.resourceType){
                    case EMPTY -> null;
                    case FOREST -> new ForestResource();
                    case STONE -> new StoneResource();
                };
                worldMap.getField(e.point).addResource(resource);
                Building building = switch (e.buildingType){
                    case EMPTY -> null;
                    case QUARRY -> new Quarry();
                    case MINESHAFT -> new Mineshaft();
                    case SAWMILL -> new Sawmill();
                };
                worldMap.getField(e.point).addBuilding(building);
            });
        }
    }

    private void movePlayer(Packet packet) {
        playerList.getPlayer(packet.playerID).moveTo(worldMap.getField(packet.position));
    }

    private void placeBuilding(Packet packet){
        Building building = switch (packet.buildingType){
            case EMPTY -> null;
            case SAWMILL -> new Sawmill();
            case MINESHAFT -> new Mineshaft();
            case QUARRY ->  new Quarry();
        };
        Platform.runLater(() -> worldMap.getField(packet.position).addBuilding(building));
    }

    private void destroyBuilding(Packet packet){
        Platform.runLater(() -> worldMap.getField(packet.position).destroyBuilding());
    }

    private void removeResource(Packet packet) {

        Platform.runLater(() -> {
            if(packet.playerID == player.playerID) {
                player.collectResource();
                Catana.renderInventory(this.player);
            }
            worldMap.getField(packet.position).destroyResource();
        });
    }

    private void initPlayer(Packet packet) {
        if(this.player == null){
            this.player = new Player(worldMap.getField(packet.position), packet.playerID);
            System.out.println("moje id to "+ player.playerID);
            playerList.addPlayer(player.playerID, player);
            worldMap.setPlayer(player);
            Platform.runLater(()->{
                worldMap.getChildren().add(player);
                Catana.renderInventory(player);
                Catana.katana.setTitle("Katana - "+"Player"+player.playerID);
                Catana.katana.getIcons().add(ImageManager.getImage("player"+player.playerID+".png",32,32));
            });
        }
    }

    private void newPlayerJoined(Packet packet) {
        {
            Player player = new Player(worldMap.getField(packet.position), packet.playerID);
            playerList.addPlayer(packet.playerID, player);
            System.out.println("Player"+ packet.playerID+" JOINED THE GAME");
            Platform.runLater(()-> worldMap.getChildren().add(player));
        }
    }

    private void playerLeft(Packet packet) {
        Player player = playerList.getPlayer(packet.playerID);
        playerList.removePlayer(packet.playerID);
        System.out.println("Player"+ packet.playerID+" LEFT THE GAME");
        Platform.runLater(()-> worldMap.getChildren().remove(player));
    }

    @Override
    public void run() {

        System.out.println("#CLIENT# Start listening...");
        while (true){
            try {
                receiveData();
            } catch (IOException e) {
                System.out.println("#CLIENT# Receive error");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("#CLIENT# SocketData class not found");
                break;
            }
        }
    }

    public WorldMap getWorldMap(){
        return worldMap;
    }

    public static boolean checkServerRunning() {

        try {
            Socket test = new Socket(IP_ADDRESS, PORT_NUMBER);
            System.out.println("#SERVER# Is active...");
            test.close();
        } catch (Exception e) {
            System.out.println("#SERVER# Is not active...");
            return false;
        }

        return true;
    }
    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    public Player getPlayer() {
        return player;
    }
}
