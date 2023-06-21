package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import com.skarpeta.skarpeciarzegame.worldmap.WorldMap;
import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {

    private static final Double RESOURCE_SPAWN_DELAY = 2.5;
    private static final Double CHANCE_FOR_STONE_SPAWN = 0.2;
    private final ServerSocket serverSocket;
    static Map<Integer, ClientThread> clientList;
    private static int portNumber = 5555;
    private static int seed;
    private static int mapSize = 40;

    public static WorldMap worldMap;

    public Server(int portNumber, int mapSize, int seed) throws IOException {
        this.portNumber = portNumber;
        this.mapSize = mapSize;
        this.seed = seed;
        worldMap = new WorldMap(mapSize, seed);
        worldMap.generateResources();
        clientList = Collections.synchronizedMap(new TreeMap<>());
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

        Thread resourceSpawning = new Thread(this::spawnResources);
        resourceSpawning.setDaemon(true);
        resourceSpawning.start();
    }

    private void spawnResources() {
        try {
            Random random = new Random(seed);
            while (true){

                Thread.sleep((long) (RESOURCE_SPAWN_DELAY * 1000));

                ResourceType resourceType = random.nextInt((int) (1.0 / CHANCE_FOR_STONE_SPAWN - 1)) == 0 ? ResourceType.STONE : ResourceType.FOREST;
                Point randomPosition = worldMap.getRandomPosition(resourceType.getTerrain());
                worldMap.getField(randomPosition).addResource(resourceType.newResource());

                Packet spawnResource = new Packet(PacketType.SPAWN_RESOURCE, resourceType, randomPosition);
                sendToAllClients(spawnResource);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void commandHandler() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            switch (scan.nextLine()) {
                case "seed" -> System.out.println(worldMap.getSeed());
                case "q","quit","exit","end","stop" -> stopServer();
            }
        }
    }

    public void connectClients() {
        int playerID = 1;
        System.out.println(" ============ SERVER OPEN AT PORT " + portNumber + " ============");
        try {
            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Player" + playerID + " joined the game");

                Point playerPos = worldMap.placePlayer();
                ClientThread newClient = new ClientThread(playerID, playerPos, playerSocket);
                List<PackedField> packedWorld = packWorld();
                new Packet(PacketType.INIT_MAP, mapSize, seed, packedWorld).sendTo(newClient.getOutputStream());
                new Packet(PacketType.INIT_PLAYER, playerID, playerPos).sendTo(newClient.getOutputStream());
                Packet nicknamePacket = (Packet) newClient.getInputStream().readObject();
                newClient.setNickname(nicknamePacket.string);

                for (ClientThread clientThread : clientList.values()) {
                    new Packet(PacketType.NEW_PLAYER, clientThread.getID(), clientThread.getPosition(),clientThread.getNickname()).sendTo(newClient.getOutputStream());//wysylanie starych graczy do nowego
                    new Packet(PacketType.NEW_PLAYER, playerID, playerPos, newClient.getNickname()).sendTo(clientThread.getOutputStream());//wysylanie nowego gracza do starych graczy
                }
                clientList.put(playerID++, newClient);
                new Thread(newClient).start();
            }
        } catch (IOException e) {
            System.out.println("Server closed. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PackedField> packWorld() {
        return new ArrayList<>(worldMap.getFields().stream().filter(field -> field.hasResource() || field.hasBuilding()).map(PackedField::new).toList());
    }

    public static void sendToAllClients(Packet packet) {
        for (ClientThread clientThread : clientList.values()) {
            packet.sendTo(clientThread.getOutputStream());
        }
    }

    public void stopServer() {
        try {
            System.out.println("CLOSING SERVER");
            clientList.values().forEach(ClientThread::closeConnection);
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error occurred while stopping the server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new Thread(new Server(portNumber,mapSize,new Random().nextInt())).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
