package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.*;
import java.io.*;
import java.util.Map;

public class Client implements Runnable {

    private final Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT_NUMBER = 5555;
    private WorldMap worldMap;
    public PlayerManager playerList = new PlayerManager();
    int playerID =-1;

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
        DataPacket newDataPacket = new DataPacket(PacketType.MOVE,playerID,newPosition);
        outputStream.writeObject(newDataPacket);
        System.out.println("#CLIENT# Send: "+ newPosition + playerID);
    }

    public void receiveData() throws IOException, ClassNotFoundException {

        DataPacket dataPacket = (DataPacket) inputStream.readObject();
        System.out.println("received packet! + "+dataPacket.packetType);
        switch (dataPacket.packetType) {
            case INIT_MAP -> worldMap = new WorldMap(dataPacket.sizeMap, dataPacket.seedMap);
            case INIT_PLAYER -> initPlayer(dataPacket);
            case NEW_PLAYER -> newPlayerJoined(dataPacket);
            case MOVE -> playerList.getPlayer(dataPacket.playerID).moveTo(worldMap.getField(dataPacket.position));
        }
    }

    private void initPlayer(DataPacket dataPacket) {
        if(playerID ==-1){
            this.playerID = dataPacket.playerID;
            System.out.println("moje id to "+ playerID);
            Player player = new Player(worldMap.getField(dataPacket.position), dataPacket.playerID);
            playerList.addPlayer(playerID, player);
            worldMap.setPlayer(player);
            Platform.runLater(()->{
                worldMap.getChildren().add(player);
                Catana.renderInventory(player);
            });
        }
    }

    private void newPlayerJoined(DataPacket dataPacket) {
        {
            Player player = new Player(worldMap.getField(dataPacket.position), dataPacket.playerID);
            playerList.addPlayer(dataPacket.playerID, player);
            System.out.println("nowy gracz dolaczyl "+dataPacket.playerID);
            Platform.runLater(()->{
                worldMap.getChildren().add(player);
            });
        }
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
}
