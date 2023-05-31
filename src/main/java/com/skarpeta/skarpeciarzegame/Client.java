package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {

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

    public static void sendData(Point newPosition) throws IOException {
        SocketPackage newSocketPackage = new SocketPackage(PlayerMove.MOVE_UP,newPosition);
        outputStream.writeObject(newSocketPackage);
        System.out.println("#CLIENT# Send: "+ newPosition);
    }

    public void receiveData() throws IOException, ClassNotFoundException {
        SocketPackage socketPackage = (SocketPackage) inputStream.readObject();
        System.out.println("#CLIENT# Receive: "+ socketPackage);
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
