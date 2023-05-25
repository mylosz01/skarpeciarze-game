package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final WorldMap worldMAP;
    private static final int PORT_NUMBER = 5555;

    Server(WorldMap worldMap) throws IOException {
        worldMAP = worldMap;
        serverSocket = new ServerSocket(PORT_NUMBER);
    }

    @Override
    public void run() {

        int test=0;
        while(true){
            System.out.println("Server waiting for player...");
            try {
                Socket newClientSocket = serverSocket.accept();

                ClientHandler newClient = new ClientHandler(test++,newClientSocket);

                Thread newClientThread = new Thread(newClient);
                newClientThread.setDaemon(true);
                newClientThread.start();

            } catch (IOException e) {
                System.out.println("Server thread error...");
            }

        }
    }

    private void sendToAllPlayers(){

    }
}
