package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientList;
    private static final int PORT_NUMBER = 5555;
    private static final int MAP_SEED = 400;
    private static final int MAP_SIZE = 50;

    public WorldMap worldMap = new WorldMap(MAP_SIZE,MAP_SEED);

    Server() throws IOException {
        clientList = Collections.synchronizedList(new ArrayList<>());
        serverSocket = new ServerSocket(PORT_NUMBER);
    }

    @Override
    public void run() {

        int playerID=0;
        while(true){
            System.out.println("#SERVER# Waiting for players...");
            try {
                Socket playerSocket = serverSocket.accept();
                System.out.println("PLAYER "+playerID+" JOINED THE GAME");
                Point playerPos = worldMap.placePlayer();
                ClientHandler newClient = new ClientHandler(playerID,playerPos,playerSocket, clientList);

                //inicjalizacja mapy
                newClient.sendData(new DataPacket(PacketType.INIT_MAP,MAP_SIZE, MAP_SEED));
                for (ClientHandler clientHandler : clientList) {
                    newClient.sendData(new DataPacket(PacketType.NEW_PLAYER,clientHandler.playerID,clientHandler.position));//wysylanie starych graczy do nowego
                    clientHandler.sendData(new DataPacket(PacketType.NEW_PLAYER,playerID,playerPos));//wysylanie nowego gracza do starych graczy
                }
                newClient.sendData(new DataPacket(PacketType.INIT_PLAYER,playerID,playerPos));

                clientList.add(newClient);

                new Thread(newClient).start();
                playerID++;
            } catch (IOException e) {
                System.out.println("#SERVER# Error...");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("SERVER START");
        Server server = new Server();
        Thread serverAccept = new Thread(server);
        serverAccept.setDaemon(true);
        serverAccept.start();

        Scanner scan = new Scanner(System.in);
        while(scan.nextLine().equals("q"));

    }
}
