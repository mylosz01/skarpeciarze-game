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

    public void receiveData() throws IOException, ClassNotFoundException {
        SocketData socketData = (SocketData) inputStream.readObject();
        System.out.println("Odebrano dane Client: "+ socketData.plyerNum +" " + socketData.xPlayer + " " + socketData.yPlayer);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    @Override
    public void run() {

        while (true){
            try {
                receiveData();
            } catch (IOException e) {
                System.out.println("Client receive error");
            } catch (ClassNotFoundException e) {
                System.out.println("SocketData class not found");
            }
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
