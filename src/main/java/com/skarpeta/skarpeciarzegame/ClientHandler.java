package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.buildings.Building;
import com.skarpeta.skarpeciarzegame.buildings.Mineshaft;
import com.skarpeta.skarpeciarzegame.buildings.Quarry;
import com.skarpeta.skarpeciarzegame.buildings.Sawmill;
import com.skarpeta.skarpeciarzegame.tools.Point;

import javax.print.attribute.standard.Severity;
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final List<ClientHandler> clientList;
    int playerID;
    Point position;

    public ClientHandler(int playerID,Point position, Socket cliSocket, List<ClientHandler> clients) throws IOException {
        this.playerID = playerID;
        this.position =position;
        clientList = clients;
        clientSocket = cliSocket;
        OutputStream out = cliSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = cliSocket.getInputStream();
        inputStream = new ObjectInputStream(in);
    }

    public DataPacket receiveData() throws IOException, ClassNotFoundException {
        DataPacket dataPacket = (DataPacket) inputStream.readObject();
        System.out.println("<-  received " + dataPacket.packetType + " from Player" + playerID);
        return dataPacket;
    }

    public void sendData(DataPacket toSend) throws IOException {
        outputStream.writeObject(toSend);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    public void sendToAllClients(DataPacket dataPacket) throws IOException {
        ArrayList<ClientHandler> toRemove = new ArrayList<>();
        for(ClientHandler clientHandler : clientList){
            clientHandler.sendData(dataPacket);
        }
        clientList.removeAll(toRemove);
    }

    @Override
    public void run() {
        while(true){
            try {
                DataPacket dataPacket = receiveData();
                switch (dataPacket.packetType)
                {
                    case MOVE -> {
                        if(dataPacket.playerID == playerID)
                            position = dataPacket.position;
                    }
                    case DESTROY_BUILDING -> Server.worldMap.getField(dataPacket.position).destroyBuilding();
                    case DESTROY_RESOURCE -> Server.worldMap.getField(dataPacket.position).destroyResource();
                    case BUILD -> {
                        Building building = switch (dataPacket.buildingType){
                            case EMPTY -> null;
                            case SAWMILL -> new Sawmill();
                            case MINESHAFT -> new Mineshaft();
                            case QUARRY ->  new Quarry();
                        };
                        Server.worldMap.getField(dataPacket.position).addBuilding(building);
                    }
                }

                sendToAllClients(dataPacket);

            } catch (IOException e) {
                System.out.println("blad poalczenia");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e); //to sie nigdy nie wykona jak cos
            }
        }
    }
}
