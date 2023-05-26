package com.skarpeta.skarpeciarzegame;

import java.io.*;

public class SocketData implements Serializable {

    public Field playerField;
    int plyerNum;

    SocketData(int num){
        plyerNum = num;
    }
}
