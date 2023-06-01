package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final List<ClientHandler> clientList;
    int number;

    public ClientHandler(int num, Socket cliSocket, List<ClientHandler> clients) throws IOException {
        number = num;
        clientList = clients;
        clientSocket = cliSocket;
        OutputStream out = cliSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = cliSocket.getInputStream();
        inputStream = new ObjectInputStream(in);
        clientList.add(this);
    }

    public DataPacket receiveData() throws IOException, ClassNotFoundException {
        DataPacket dataPacket = (DataPacket) inputStream.readObject();
        System.out.println("#CLIENT HANDLER " + number + " # RECEIVE: " + dataPacket);
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

    public void sendToAllClients(DataPacket dataPacket){
        ArrayList<ClientHandler> toRemove = new ArrayList<>();
        for(ClientHandler clientHandler : clientList){
            try {
                //if(clientHandler != this)
                    clientHandler.sendData(dataPacket);
            } catch (IOException e){
                toRemove.add(clientHandler);
                System.out.println("client unreachable");
            }
        }
        clientList.removeAll(toRemove);
    }

    @Override
    public void run() {
        while(true){
            try {

                DataPacket dataPacket = receiveData();
                sendToAllClients(dataPacket);
            }catch (IOException e) {
                try {
                    closeConnection();
                } catch (IOException ex) {
                    System.out.println("#CLIENT HANDLER# Connection closed err");
                }
                System.out.println("#CLIENT HANDLER# Connection closed");
                return;
            } catch (ClassNotFoundException e) {
                System.out.println("#CLIENT HANDLER# Class SocketData not found");
                return;
            }
        }

    }
}
