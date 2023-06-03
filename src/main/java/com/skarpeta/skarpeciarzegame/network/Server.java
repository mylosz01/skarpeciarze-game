package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.FieldInfoPacket;
import com.skarpeta.skarpeciarzegame.WorldMap;
import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    static Map<Integer,ClientHandler> clientList;
    private List<FieldInfoPacket> fieldInfo;
    private static final int PORT_NUMBER = 5555;
    private static final int MAP_SEED = new Random().nextInt();
    private static final int MAP_SIZE = 40;

    public static WorldMap worldMap;

    Server() throws IOException {
        worldMap = new WorldMap(MAP_SIZE,MAP_SEED);
        worldMap.generateResources();
        clientList =  Collections.synchronizedMap(new TreeMap<>());
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
                new Packet(PacketType.INIT_MAP,MAP_SIZE, MAP_SEED, fieldInfo).sendTo(newClient.getOutputStream());
                new Packet(PacketType.INIT_PLAYER,playerID,playerPos).sendTo(newClient.getOutputStream());
                for (Map.Entry<Integer, ClientHandler> entry : clientList.entrySet()) {
                    ClientHandler clientHandler = entry.getValue();
                    new Packet(PacketType.NEW_PLAYER,clientHandler.playerID,clientHandler.position).sendTo(newClient.getOutputStream());//wysylanie starych graczy do nowego
                    new Packet(PacketType.NEW_PLAYER,playerID,playerPos).sendTo(clientHandler.getOutputStream());//wysylanie nowego gracza do starych graczy
                }

                clientList.put(playerID,newClient);

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
        for(Map.Entry<Integer, ClientHandler> entry : clientList.entrySet()){
            packet.sendTo(entry.getValue().getOutputStream());
        }
    }
}
