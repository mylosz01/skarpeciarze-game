package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;

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
        System.out.println("#CLIENT HANDLER " + playerID + " # RECEIVE: " + dataPacket.packetType);
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

                if(dataPacket.packetType.equals(PacketType.MOVE) && dataPacket.playerID == playerID)
                    position = dataPacket.position;

                sendToAllClients(dataPacket);

            } catch (IOException e) {
                System.out.println("blad poalczenia");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e); //to sie nigdy nie wykona jak cos
            }
        }
    }
}
