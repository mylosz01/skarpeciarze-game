package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;

import java.io.*;

public class SocketData implements Serializable {

    public int playerX;
    public int playerY;
    public int plyerNum;

    SocketData(int num,int pX, int pY){
        plyerNum = num;
        playerX = pX;
        playerY = pY;
    }
}
