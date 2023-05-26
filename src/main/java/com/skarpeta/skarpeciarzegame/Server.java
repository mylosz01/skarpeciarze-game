package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientsList;
    private final WorldMap worldMAP;
    private static final int PORT_NUMBER = 5555;

    Server(WorldMap worldMap) throws IOException {
        worldMAP = worldMap;
        clientsList = Collections.synchronizedList(new ArrayList<>());
        serverSocket = new ServerSocket(PORT_NUMBER);
    }

    @Override
    public void run() {

        int test=0;
        while(true){
            System.out.println("#SERVER# Waiting for players...");
            try {
                Socket newClientSocket = serverSocket.accept();
                System.out.println("NEW PLAYER ACCEPTED..." + test);
                ClientHandler newClient = new ClientHandler(test++,newClientSocket,clientsList);

                Thread newClientThread = new Thread(newClient);
                newClientThread.setDaemon(true);
                newClientThread.start();

            } catch (IOException e) {
                System.out.println("#SERVER# Error...");
            }

        }
    }
}
