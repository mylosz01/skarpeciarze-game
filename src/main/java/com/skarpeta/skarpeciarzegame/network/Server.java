package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.worldmap.WorldMap;
import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {

    private final ServerSocket serverSocket;
    static Map<Integer, ClientThread> clientList;
    private List<FieldInfoPacket> fieldInfo;
    private static int portNumber = 5555;
    private static int seed = new Random().nextInt();
    private static int mapSize = 40;

    public static WorldMap worldMap;

    public Server(int portNumber, int mapSize, int seed) throws IOException {
        this.portNumber = portNumber;
        this.mapSize = mapSize;
        this.seed = seed;
        worldMap = new WorldMap(mapSize, seed);
        worldMap.generateResources();
        clientList = Collections.synchronizedMap(new TreeMap<>());
        fieldInfo = packWorld();
        serverSocket = new ServerSocket(portNumber);
    }
    @Override
    public void run() {
        System.out.println("SERVER START");
        Thread serverAccept = new Thread(this::connectClients);
        serverAccept.setDaemon(true);
        serverAccept.start();

        Thread commandHandler = new Thread(this::commandHandler);
        commandHandler.setDaemon(true);
        commandHandler.start();
    }

    private void commandHandler() {
        Scanner scan = new Scanner(System.in);
        while (scan.nextLine().charAt(0) != 'q');
        stopServer();
    }

    public void connectClients() {
        int playerID = 1;
        System.out.println(" ============ SERVER OPEN AT PORT " + portNumber + " ============");
        try {
            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Player" + playerID + " joined the game");
                Point playerPos = worldMap.placePlayer();
                fieldInfo = packWorld();
                ClientThread newClient = new ClientThread(playerID, playerPos, playerSocket);

                //inicjalizacja mapy
                new Packet(PacketType.INIT_MAP, mapSize, seed, fieldInfo).sendTo(newClient.getOutputStream());
                new Packet(PacketType.INIT_PLAYER, playerID, playerPos).sendTo(newClient.getOutputStream());
                for (ClientThread clientThread : clientList.values()) {
                    new Packet(PacketType.NEW_PLAYER, clientThread.playerID, clientThread.position).sendTo(newClient.getOutputStream());//wysylanie starych graczy do nowego
                    new Packet(PacketType.NEW_PLAYER, playerID, playerPos).sendTo(clientThread.getOutputStream());//wysylanie nowego gracza do starych graczy
                }
                clientList.put(playerID++, newClient);
                new Thread(newClient).start();
            }
        } catch (IOException e) {
            System.out.println("Server closed. " + e.getMessage());
        }
    }

    private List packWorld() {
        List list = Collections.synchronizedList(new ArrayList<>());
        worldMap.forEach(e -> {
            FieldInfoPacket info = new FieldInfoPacket(e.position);
            if (e.hasResource())
                info.setResourceType(e.resource.type);
            if (e.hasBuilding())
                info.setBuildingType(e.building.type);
            list.add(info);
        });
        return list;
    }

    public static void sendToAllClients(Packet packet) {
        for (ClientThread clientThread : clientList.values()) {
            packet.sendTo(clientThread.getOutputStream());
        }
    }

    public void stopServer() {
        try {
            System.out.println("CLOSING SERVER");
            for (ClientThread clientThread : clientList.values()) {
                clientThread.closeConnection();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error occurred while stopping the server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new Thread(new Server(portNumber,mapSize,seed)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
