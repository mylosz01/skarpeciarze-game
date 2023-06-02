package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientList;
    private final List<FieldInfoPacket> fieldInfo;
    private static final int PORT_NUMBER = 5555;
    private static final int MAP_SEED = new Random().nextInt();
    private static final int MAP_SIZE = 40;

    public static WorldMap worldMap;

    Server() throws IOException {
        worldMap = new WorldMap(MAP_SIZE,MAP_SEED);
        worldMap.generateResources();
        clientList = Collections.synchronizedList(new ArrayList<>());
        fieldInfo = Collections.synchronizedList(new ArrayList<>());
        worldMap.forEach(e->{
            FieldInfoPacket info = new FieldInfoPacket(e.position);
            if(e.hasResource())
                info.resourceType=e.resource.type;
            if(e.hasBuilding())
                info.buildingType=e.building.type;
            fieldInfo.add(info);
        });
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

                fieldInfo.clear();
                worldMap.forEach(e->{
                    FieldInfoPacket info = new FieldInfoPacket(e.position);
                    if(e.hasResource())
                        info.setResourceType(e.resource.type);
                    else
                        info.setResourceType(ResourceType.EMPTY);


                    if(e.hasBuilding())
                        info.setBuildingType(e.building.type);
                    else
                        info.setBuildingType(BuildingType.EMPTY);

                    fieldInfo.add(info);
                });

                ClientHandler newClient = new ClientHandler(playerID,playerPos,playerSocket, clientList);

                //inicjalizacja mapy
                newClient.sendData(new DataPacket(PacketType.INIT_MAP,MAP_SIZE, MAP_SEED, fieldInfo));
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
