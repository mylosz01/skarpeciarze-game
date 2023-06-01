package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientsList;
    private static final int PORT_NUMBER = 5555;

    private static final int MAP_SEED = 400;
    private static final int MAP_SIZE = 50;

    public WorldMap worldMap = new WorldMap(MAP_SIZE,MAP_SEED);

    Server() throws IOException {
        clientsList = Collections.synchronizedList(new ArrayList<>());
        serverSocket = new ServerSocket(PORT_NUMBER);
    }

    @Override
    public void run() {

        int test=0;
        while(true){
            System.out.println("#SERVER# Waiting for players...");
            try {
                Socket newClientSocket = serverSocket.accept();
                System.out.println("NEW PLAYER ACCEPTED..." + test);
                ClientHandler newClient = new ClientHandler(test++,newClientSocket,clientsList);

                newClient.sendData(new DataPacket(PacketType.INITIAL,MAP_SIZE, MAP_SEED));
                newClient.sendToAllClients(new DataPacket(PacketType.NEW_PLAYER,worldMap.placePlayer()));
                Thread newClientThread = new Thread(newClient);
                newClientThread.setDaemon(true);
                newClientThread.start();

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
