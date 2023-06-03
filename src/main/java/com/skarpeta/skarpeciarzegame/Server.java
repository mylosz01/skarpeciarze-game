package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private static List<ClientHandler> clientList;
    private List<FieldInfoPacket> fieldInfo;
    private static final int PORT_NUMBER = 5555;
    private static final int MAP_SEED = new Random().nextInt();
    private static final int MAP_SIZE = 40;

    public static WorldMap worldMap;

    Server() throws IOException {
        worldMap = new WorldMap(MAP_SIZE,MAP_SEED);
        worldMap.generateResources();
        clientList = Collections.synchronizedList(new ArrayList<>());
        fieldInfo = packWorld();
        serverSocket = new ServerSocket(PORT_NUMBER);
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
    @Override
    public void run() {

        int playerID=0;
        while(true){
            System.out.println("#SERVER# Waiting for players...");
            try {
                Socket playerSocket = serverSocket.accept();
                System.out.println("PLAYER "+playerID+" JOINED THE GAME");
                Point playerPos = worldMap.placePlayer();
                fieldInfo = packWorld();
                ClientHandler newClient = new ClientHandler(playerID,playerPos,playerSocket);

                //inicjalizacja mapy
                newClient.sendData(new Packet(PacketType.INIT_MAP,MAP_SIZE, MAP_SEED, fieldInfo));
                newClient.sendData(new Packet(PacketType.INIT_PLAYER,playerID,playerPos));
                for (ClientHandler clientHandler : clientList) {
                    newClient.sendData(new Packet(PacketType.NEW_PLAYER,clientHandler.playerID,clientHandler.position));//wysylanie starych graczy do nowego
                    clientHandler.sendData(new Packet(PacketType.NEW_PLAYER,playerID,playerPos));//wysylanie nowego gracza do starych graczy
                }

                clientList.add(newClient);

                new Thread(newClient).start();
                playerID++;
            } catch (IOException e) {
                System.out.println("#SERVER# Error...");
            }
        }
    }

    private List packWorld() {
        List list = Collections.synchronizedList(new ArrayList<>());
        worldMap.forEach(e->{
            FieldInfoPacket info = new FieldInfoPacket(e.position);
            if(e.hasResource())
                info.setResourceType(e.resource.type);
            if(e.hasBuilding())
                info.setBuildingType(e.building.type);
            list.add(info);
        });
        return list;
    }

    public static void sendToAllClients(Packet packet) throws IOException {
        for(ClientHandler clientHandler : clientList){
            clientHandler.sendData(packet);
        }
    }
}
