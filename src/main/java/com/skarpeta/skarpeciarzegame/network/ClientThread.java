package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;

public class ClientThread implements Runnable {

    private final int playerID;
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private String nickname;
    private Point position;

    public ClientThread(int playerID, Point position, Socket clientSocket) throws IOException {
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
                    case MOVE -> movePlayer(packet);
                    case BUILD -> addBuilding(packet);
                    case DESTROY_BUILDING -> Server.worldMap.getField(packet.position).destroyBuilding();
                    case DESTROY_RESOURCE -> Server.worldMap.getField(packet.position).destroyResource();
                    case NICKNAME -> setMyNickname(packet);
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
            //closeConnection();
        }
    }

    private void setMyNickname(Packet packet) {
        this.nickname = packet.string;
        Server.sendToAllClients(new Packet(PacketType.NICKNAME,playerID,nickname));
    }

    private void movePlayer(Packet packet) {
        if (packet.playerID == playerID) position = packet.position;
    }

    private void addBuilding(Packet packet) {
        Server.worldMap.getField(packet.position).addBuilding(packet.buildingType.newBuilding());
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

    public ObjectInput getInputStream() {
        return inputStream;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getID() {
        return playerID;
    }

    public String getNickname(){
        return nickname;
    }
    public Point getPosition() {
        return position;
    }
}
