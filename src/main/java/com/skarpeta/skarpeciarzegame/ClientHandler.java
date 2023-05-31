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

    public SocketPackage receiveData() throws IOException, ClassNotFoundException {
        SocketPackage socketPackage = (SocketPackage) inputStream.readObject();
        System.out.println("#CLIENT HANDLER " + number + " # RECEIVE: " + socketPackage);
        return socketPackage;
    }

    public void sendData(SocketPackage toSend) throws IOException {
        //SocketPackage data = new SocketPackage(number,6,9);
        outputStream.writeObject(toSend);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    private void sendToAllClients(SocketPackage socketPackage){
        ArrayList<ClientHandler> toRemove = new ArrayList<>();
        for(ClientHandler clientHandler : clientList){
            try {
                //if(clientHandler != this)
                    clientHandler.sendData(socketPackage);
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

                SocketPackage socketPackage = receiveData();
                sendToAllClients(socketPackage);
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
