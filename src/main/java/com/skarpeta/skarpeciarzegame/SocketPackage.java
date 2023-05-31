package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;

public class SocketPackage implements Serializable {

    public Point playerLocation;
    public PlayerMove playerMove;
    public int plyerNum;
    public ServerValidMove validiti;

    SocketPackage(PlayerMove playMove,Point playerCords){
        playerMove = playMove;
        playerLocation = new Point(playerCords);
        validiti = null;
    }

    SocketPackage(int num,int x,int y){
        plyerNum = num;
        playerLocation = new Point(x,y);
    }

    public String toString(){
        return "\nPlayer location: " + playerLocation + "\n" +
                "Player move: "+playerMove + "\n"+
                "Player Num: " + plyerNum + "\n"+
                "Player valid: " + validiti;
    }
}
