package com.skarpeta.skarpeciarzegame;

import com.almasb.fxgl.core.collection.Array;

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

    public void receiveData() throws IOException, ClassNotFoundException {
        SocketData socketData = (SocketData) inputStream.readObject();
        System.out.println("#CLIENT HANDLER " + number + " # RECEIVE: " + socketData.plyerNum);
    }

    public void sendData() throws IOException {
        SocketData data = new SocketData(number);
        outputStream.writeObject(data);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    private void sendToAllClients(){
        ArrayList<ClientHandler> toRemove = new ArrayList<>();
        for(ClientHandler clientHandler : clientList){
            try {
                clientHandler.sendData();
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

                receiveData();
                sendToAllClients();
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
