package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;
import com.skarpeta.skarpeciarzegame.app.Catana;
import com.skarpeta.skarpeciarzegame.worldmap.WorldMap;
import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.*;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.application.Platform;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {

    private final Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private WorldMap worldMap;
    public PlayerManager playerList = new PlayerManager();
    private final List<Building> playerBuildingList = Collections.synchronizedList(new ArrayList<>());
    Player player = null;

    public Client() throws IOException {
        try {
            clientSocket = new Socket(Catana.ipAddress, Catana.portNumber);
            OutputStream out = clientSocket.getOutputStream();
            outputStream = new ObjectOutputStream(out);
            InputStream in = clientSocket.getInputStream();
            inputStream = new ObjectInputStream(in);

            Catana.katana.setOnCloseRequest(e -> new Packet(PacketType.DISCONNECT).sendTo(outputStream));

            receiveData();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeMove(int playerID, Point newPosition) {
        new Packet(PacketType.MOVE, playerID, newPosition).sendTo(outputStream);
    }

    public void sendBuildBuilding(Point fieldPosition, BuildingType buildingType) {
        new Packet(PacketType.BUILD, player.playerID, buildingType, fieldPosition).sendTo(outputStream);
    }

    public void sendRemoveBuilding(Point fieldPosition) {
        new Packet(PacketType.DESTROY_BUILDING, player.playerID, fieldPosition).sendTo(outputStream);
    }

    public void sendRemoveResource(Point position) {
        new Packet(PacketType.DESTROY_RESOURCE, player.playerID, position).sendTo(outputStream);
    }

    public void receiveData() throws IOException, ClassNotFoundException {

        Packet packet = (Packet) inputStream.readObject();
        System.out.println("<-  received " + packet.packetType);
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
        if (Catana.playerUI!=null)
            Catana.playerUI.updateButtonUI();
    }

    private void initMap(Packet packet) {
        worldMap = new WorldMap(packet.sizeMap, packet.seedMap);
        packet.fieldInfo.forEach(e -> {
            Resource resource = switch (e.resourceType) {
                case EMPTY -> null;
                case FOREST -> new ForestResource();
                case STONE -> new StoneResource();
            };
            Building building = switch (e.buildingType) {
                case EMPTY -> null;
                case QUARRY -> new Quarry(packet.position);
                case MINESHAFT -> new Mineshaft(packet.position);
                case SAWMILL -> new Sawmill(packet.position);
            };
            worldMap.getField(e.point).addResource(resource);
            worldMap.getField(e.point).addBuilding(building);
        });
    }

    private void movePlayer(Packet packet) {
        playerList.getPlayer(packet.playerID).moveTo(worldMap.getField(packet.position));
    }

    private void placeBuilding(Packet packet) {
        Building building = switch (packet.buildingType) {
            case EMPTY -> null;
            case SAWMILL -> new Sawmill(packet.position);
            case MINESHAFT -> new Mineshaft(packet.position);
            case QUARRY -> new Quarry(packet.position);
        };
        if(packet.playerID == player.playerID){
            playerBuildingList.add(building);
        }
        Platform.runLater(() -> worldMap.getField(packet.position).addBuilding(building));
    }

    private void destroyBuilding(Packet packet) {
        if(packet.playerID == player.playerID){
            playerBuildingList.remove(worldMap.getField(packet.position).building);
        }
        Platform.runLater(() -> worldMap.getField(packet.position).destroyBuilding());
    }

    private void removeResource(Packet packet) {
        Platform.runLater(() -> {
            if (packet.playerID == player.playerID) {
                player.collectResource();
                Catana.playerUI.renderInventory(this.player);
            }
            worldMap.getField(packet.position).destroyResource();
        });
    }

    private void initPlayer(Packet packet) {
        if (this.player == null) {
            this.player = new Player(worldMap.getField(packet.position), packet.playerID);
            System.out.println("Joined as Player" + player.playerID);
            playerList.addPlayer(player.playerID, player);
            Platform.runLater(() -> {
                Catana.renderPlayer(player);
                Catana.playerUI.renderInventory(player);
                Catana.katana.setTitle("Katana - Player" + player.playerID);
                Catana.katana.getIcons().add(player.getTexture().getImage());
            });
        }
    }

    private void newPlayerJoined(Packet packet) {
        Player player = new Player(worldMap.getField(packet.position), packet.playerID);
        playerList.addPlayer(packet.playerID, player);
        System.out.println("Player" + packet.playerID + " JOINED THE GAME");
        Platform.runLater(() -> Catana.renderPlayer(player));
    }

    private void playerLeft(Packet packet) {
        Player player = playerList.getPlayer(packet.playerID);
        playerList.removePlayer(packet.playerID);
        System.out.println("Player" + packet.playerID + " LEFT THE GAME");
        Platform.runLater(() -> Catana.playersGroup.getChildren().remove(player));
    }

    @Override
    public void run() {

        System.out.println("#CLIENT# Start listening...");
        try {
            new Thread(this::buildingThread).start();
            while (true) {
                receiveData();
            }
        } catch (IOException e) {
            System.out.println("#CLIENT# Lost connection. "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildingThread(){
        while (true){
            try {
                Thread.sleep(1000);
                playerBuildingList.forEach((value) -> player.getInventory().increaseItemAmount(value.item.getName(),value.item.getAmount()));
                Platform.runLater(() -> Catana.playerUI.renderInventory(player));
            }catch (InterruptedException e) {
                System.out.println("Err");
            }
        }
    }

    public WorldMap getWorldMap() {
        return worldMap;
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
