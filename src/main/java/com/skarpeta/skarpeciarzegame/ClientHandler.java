package com.skarpeta.skarpeciarzegame;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public ClientHandler(Socket cliSocket) throws IOException {
        clientSocket = cliSocket;
        OutputStream out = cliSocket.getOutputStream();
        outputStream = new ObjectOutputStream(out);
        InputStream in = cliSocket.getInputStream();
        inputStream = new ObjectInputStream(in);
    }

    @Override
    public void run() {

    }
}
