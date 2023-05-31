package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;

public class DataPacket implements Serializable {

    int seedMap;
    PacketType packetType;
    public Point playerLocation;
    public PlayerMove playerMove;
    public int plyerNum;
    public ServerValidMove validiti;

    DataPacket(PacketType packetType,int seed){
        this.packetType = packetType;
        this.seedMap = seed;
    }

    DataPacket(PlayerMove playMove, Point playerCords){
        this.playerMove = playMove;
        this.playerLocation = new Point(playerCords);
        this.validiti = null;
    }

    public String toString(){
        return "\nPlayer location: " + playerLocation + "\n" +
                "Player move: "+playerMove + "\n"+
                "Player Num: " + plyerNum + "\n"+
                "Packet type: " + packetType + "\n" +
                "Seed map: " + seedMap;
    }
}
