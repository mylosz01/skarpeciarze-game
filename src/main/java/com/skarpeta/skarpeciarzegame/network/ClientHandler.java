package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    int playerID;
    Point position;

    public ClientHandler(int playerID, Point position, Socket clientSocket) throws IOException {
        this.playerID = playerID;
        this.position = position;
        this.clientSocket = clientSocket;
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Packet packet = receiveData();
                switch (packet.packetType) {
                    case MOVE -> {if (packet.playerID == playerID) position = packet.position;}
                    case DESTROY_BUILDING -> Server.worldMap.getField(packet.position).destroyBuilding();
                    case DESTROY_RESOURCE -> Server.worldMap.getField(packet.position).destroyResource();
                    case BUILD -> addBuilding(packet);
                    case DISCONNECT -> throw new IOException("Player left the game");
                }
                Server.sendToAllClients(packet);
            }
        } catch (IOException e) {
            System.out.println("Lost connection with Player"+playerID +". "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //to sie nigdy nie wykona jak cos
        } finally {
            Server.clientList.remove(playerID);
            Server.sendToAllClients(new Packet(PacketType.DISCONNECT,playerID));
            closeConnection();
        }
    }

    private void addBuilding(Packet packet) {
        Building building = switch (packet.buildingType) {
            case EMPTY -> null;
            case SAWMILL -> new Sawmill();
            case MINESHAFT -> new Mineshaft();
            case QUARRY -> new Quarry();
        };
        Server.worldMap.getField(packet.position).addBuilding(building);
    }

    public Packet receiveData() throws IOException, ClassNotFoundException {
        Packet packet = (Packet) inputStream.readObject();
        System.out.println("<-  received " + packet.packetType + " from Player" + playerID);
        return packet;
    }

    public void closeConnection()  {
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException ignore){}
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
