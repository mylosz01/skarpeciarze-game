package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    int number;

    public ClientHandler(int num,Socket cliSocket) throws IOException {
        number = num;
        clientSocket = cliSocket;
        OutputStream out = cliSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = cliSocket.getInputStream();
        inputStream = new ObjectInputStream(in);
    }

    public void sendData() throws IOException {
        SocketData data = new SocketData(number,1,1);
        outputStream.writeObject(data);
    }

    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    @Override
    public void run() {
        while(true){

            try {
                sendData();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("client handler err1");
            } catch (IOException e) {
                try {
                    closeConnection();
                    System.out.println("Connection closed");
                } catch (IOException ex) {
                    System.out.println("Connection closed err");
                }
                System.out.println("client handler err2");
                return;
            }
            System.out.println("check client handler");
        }

    }

}
