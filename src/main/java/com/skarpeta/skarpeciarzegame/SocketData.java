package com.skarpeta.skarpeciarzegame;

import java.io.*;

public class SocketData implements Serializable {

    int plyerNum;
    int xPlayer;
    int yPlayer;

    SocketData(int num,int x,int y){
        this.plyerNum = num;
        this.xPlayer = x;
        this.yPlayer = y;
    }
}
