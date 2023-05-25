package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
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

    public void sendData(){

    }

    public void receiveData(){

    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    @Override
    public void run() {

        while (true){
            receiveData();
        }
    }

    public static boolean checkServerRunning(){

        try{
            Socket test = new Socket(IP_ADDRESS,PORT_NUMBER);
            System.out.println("Server jest aktywny...");
            test.close();
        } catch (Exception e) {
            System.out.println("Server nie jest dostępny1...");
            return false;
        }

        return true;
    }

}
