package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.buildings.BuildingType;
import com.skarpeta.skarpeciarzegame.app.Catana;
import com.skarpeta.skarpeciarzegame.inventory.ItemType;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.worldmap.Field;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import com.skarpeta.skarpeciarzegame.worldmap.WorldMap;
import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;

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
    public Player player = null;

    public Client() throws IOException {
        try {
            clientSocket = new Socket(Catana.ipAddress, Catana.portNumber);
            OutputStream out = clientSocket.getOutputStream();
            outputStream = new ObjectOutputStream(out);
            InputStream in = clientSocket.getInputStream();
            inputStream = new ObjectInputStream(in);

            receiveData();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void leaveGame() {
        Packet packet = new Packet(PacketType.DISCONNECT,player.playerID);
        packet.sendTo(outputStream);
        playerLeft(packet);
    }

    public void moveUseKey(KeyEvent event){
        //System.out.println(" #### " + event.getCode());
        try {
            switch (event.getCode()) {
                case W -> player.sendMove(worldMap.getField(new Point(player.playerField.getPosition().x,player.playerField.getPosition().y - 1)));
                case S -> player.sendMove(worldMap.getField(new Point(player.playerField.getPosition().x,player.playerField.getPosition().y + 1)));
                case Q -> player.sendMove(worldMap.getField(new Point(player.playerField.getPosition().x - 1, player.playerField.getPosition().x % 2 == 0 ? player.playerField.getPosition().y - 1 : player.playerField.getPosition().y)));
                case E -> player.sendMove(worldMap.getField(new Point(player.playerField.getPosition().x + 1, player.playerField.getPosition().x % 2 == 0 ? player.playerField.getPosition().y - 1 : player.playerField.getPosition().y)));
                case A -> player.sendMove(worldMap.getField(new Point(player.playerField.getPosition().x - 1, player.playerField.getPosition().x % 2 == 0 ? player.playerField.getPosition().y : player.playerField.getPosition().y + 1)));
                case D -> player.sendMove(worldMap.getField(new Point(player.playerField.getPosition().x + 1, player.playerField.getPosition().x % 2 == 0 ? player.playerField.getPosition().y : player.playerField.getPosition().y + 1)));
                case Z -> sendRemoveResource(player.playerField.getPosition());
                case X -> sendRemoveBuilding(player.playerField.getPosition());
                case DIGIT1 -> sendBuildBuilding(player.playerField.getPosition(),BuildingType.SAWMILL);
                case DIGIT2 -> sendBuildBuilding(player.playerField.getPosition(),BuildingType.QUARRY);
                case DIGIT3 -> sendBuildBuilding(player.playerField.getPosition(),BuildingType.MINESHAFT);
                case F -> player.getInventory().craft(ItemType.BOAT);
            }
        }catch (IOException | InvalidMoveException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void makeMove(int playerID, Point newPosition) {
        new Packet(PacketType.MOVE, playerID, newPosition).sendTo(outputStream);
    }

    public void sendBuildBuilding(Point fieldPosition, BuildingType buildingType) {

       Field field = worldMap.getField(fieldPosition);

        if (field.getTerrain() == buildingType.placableTerrain() && !field.hasBuilding() && player.getInventory().hasEnoughMaterials(buildingType.getCost())) {
            new Packet(PacketType.BUILD, player.playerID, buildingType, fieldPosition).sendTo(outputStream);
        }
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
            case NICKNAME -> setMyNickname(packet);
        }
        if (Catana.playerUI!=null)
            Catana.playerUI.updateButtons();
    }

    private void setMyNickname(Packet packet) {
        playerList.getPlayer(packet.playerID).setNickname(packet.string);
    }

    private void initMap(Packet packet) {
        worldMap = new WorldMap(packet.sizeMap, packet.seedMap);
        //new WorldGenerator(packet.seedMap).setBiomes(worldMap); kolorowanie wysp

        packet.fieldInfo.forEach(e -> {
            worldMap.getField(e.position).addResource(e.resourceType.newResource());
            worldMap.getField(e.position).addBuilding(e.buildingType.newBuilding());
        });

        Thread buildingThread = new Thread(this::buildingThread);
        buildingThread.setDaemon(true);
        buildingThread.start();
    }

    private void movePlayer(Packet packet) {
        Player packetPlayer = playerList.getPlayer(packet.playerID);
        packetPlayer.moveTo(worldMap.getField(packet.position));
        if (packetPlayer.playerField.getTerrain() == TerrainType.WATER)
            packetPlayer.getOnBoat();
        else if (packetPlayer.isOnBoat()) {
            packetPlayer.getOffBoat();

            if(packet.playerID == player.playerID){
                player.getInventory().decreaseItemAmount(ItemType.BOAT, 1);
                Platform.runLater(() -> Catana.playerUI.renderInventory(packetPlayer));
            }
        }
    }

    private void placeBuilding(Packet packet) {
        Building building = packet.buildingType.newBuilding();
        if(packet.playerID == player.playerID){
            ArrayList<Item> cost = packet.buildingType.getCost();
            player.getInventory().decrease(cost);
            playerBuildingList.add(building);
            Platform.runLater(() -> Catana.playerUI.renderInventory(this.player));
        }
        Platform.runLater(() -> worldMap.getField(packet.position).addBuilding(building));
    }

    private void destroyBuilding(Packet packet) {
        //usuwa budynek z listy budynkow gracza. jezeli budynek nie nalezy do tego gracza, to nie bylo go w tej lisie, wiec nic sie nie zmieni
        playerBuildingList.remove(worldMap.getField(packet.position).getBuilding());
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
            this.player = new Player(worldMap.getField(packet.position), packet.playerID, Catana.getNickname());
            System.out.println("Joined as Player" + player.playerID);
            playerList.addPlayer(player.playerID, player);
            Platform.runLater(() -> {
                Catana.renderPlayer(player);
                Catana.playerUI.renderInventory(player);
                Catana.katana.setTitle("Katana - Player" + player.playerID);
                Catana.katana.getIcons().add(player.getTexture().getImage());
                Catana.playerUI.updatePlayerList(playerList.getPlayers());
            });
            new Packet(PacketType.NICKNAME,player.playerID,Catana.getNickname()).sendTo(outputStream);
        }
    }

    private void newPlayerJoined(Packet packet) {
        Player player = new Player(worldMap.getField(packet.position), packet.playerID, packet.string);
        playerList.addPlayer(packet.playerID, player);
        System.out.println("Player" + packet.playerID + " JOINED THE GAME");
        Platform.runLater(() -> {
            Catana.renderPlayer(player);
            Catana.playerUI.updatePlayerList(playerList.getPlayers());
        });
    }

    private void playerLeft(Packet packet) {
        Player player = playerList.getPlayer(packet.playerID);
        playerList.removePlayer(packet.playerID);
        System.out.println("Player" + packet.playerID + " LEFT THE GAME");
        Platform.runLater(() -> {
            Catana.playersGroup.getChildren().remove(player);
            Catana.playerUI.updatePlayerList(playerList.getPlayers());
        });
    }

    @Override
    public void run() {

        System.out.println("#CLIENT# Start listening...");
        try {
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
                playerBuildingList.forEach((value) -> player.getInventory().increaseItemAmount(value.producedItem.getType(),value.producedItem.getAmount()));
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
