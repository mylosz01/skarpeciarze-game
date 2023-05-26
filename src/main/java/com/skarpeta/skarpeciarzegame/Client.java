package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {

    private static Player player;
    private final Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT_NUMBER = 5555;

    public Client() throws IOException {
        clientSocket = new Socket(IP_ADDRESS,PORT_NUMBER);
        OutputStream out = clientSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = clientSocket.getInputStream();
        inputStream = new ObjectInputStream(in);
    }

    public void setPlayer(Player gamer){
        player = gamer;
    }

    public static void sendData() throws IOException {
        int rn = new Random().nextInt(100);
        SocketData newSocketData = new SocketData(rn);
        //newSocketData.playerField = player.playerField;
        outputStream.writeObject(newSocketData);
        System.out.println("#CLIENT# Send: "+ rn);
    }

    public void receiveData() throws IOException, ClassNotFoundException {
        SocketData socketData = (SocketData) inputStream.readObject();
        System.out.println("#CLIENT# Receive: "+ socketData.plyerNum);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    @Override
    public void run() {

        System.out.println("#CLIENT# Start listening...");
        while (true){
            try {
                receiveData();
            } catch (IOException e) {
                System.out.println("#CLIENT# Receive error");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("#CLIENT# SocketData class not found");
                break;
            }
        }
    }

    public static boolean checkServerRunning(){

        try{
            Socket test = new Socket(IP_ADDRESS,PORT_NUMBER);
            System.out.println("#SERVER# Is active...");
            test.close();
        } catch (Exception e) {
            System.out.println("#SERVER# Is not active...");
            return false;
        }

        return true;
    }

}
