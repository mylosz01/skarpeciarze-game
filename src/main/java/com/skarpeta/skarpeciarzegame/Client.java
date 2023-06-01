package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

    private final Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT_NUMBER = 5555;
    private WorldMap worldMap;

    public Client() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(IP_ADDRESS,PORT_NUMBER);
        OutputStream out = clientSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = clientSocket.getInputStream();
        inputStream = new ObjectInputStream(in);

        //otrzymanie pakietu inicjalizacyjnego
        receiveData();
    }

    public static void sendData(Point newPosition) throws IOException {
        DataPacket newDataPacket = new DataPacket(PacketType.MOVE,PlayerMove.MOVE,newPosition);
        outputStream.writeObject(newDataPacket);
        System.out.println("#CLIENT# Send: "+ newPosition);
    }

    public void receiveData() throws IOException, ClassNotFoundException {
        DataPacket dataPacket = (DataPacket) inputStream.readObject();

        if(dataPacket.packetType == PacketType.INITIAL){
            worldMap = new WorldMap(dataPacket.sizeMap,dataPacket.seedMap);
        }
        else if(dataPacket.packetType == PacketType.NEW_PLAYER){
            System.out.println("Nowy gracz");

            Player player = new Player(worldMap,dataPacket.playerLocation);
            PlayerManager.addPlayer(player);

            worldMap.getChildren().add(player);

        }

        System.out.println("#CLIENT# Receive: "+ dataPacket);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
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

    public static boolean checkServerRunning(){

        try{
            Socket test = new Socket(IP_ADDRESS,PORT_NUMBER);
            System.out.println("#SERVER# Is active...");
            test.close();
        } catch (Exception e) {
            System.out.println("#SERVER# Is not active...");
            return false;
        }

        return true;
    }

}
