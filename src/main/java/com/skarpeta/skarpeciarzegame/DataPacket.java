package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;

public class DataPacket implements Serializable {

    int seedMap;
    int sizeMap;
    PacketType packetType;
    public Point playerLocation;
    public PlayerMove playerMove;
    public int plyerNum;
    public ServerValidMove validiti;

    //packiet inicjalizacyjny
    DataPacket(PacketType packetType,int size,int seed){
        this.packetType = packetType;
        this.seedMap = seed;
        this.sizeMap = size;
    }

    //pakiet ruchu gracza
    DataPacket(PacketType packetTyp,PlayerMove playMove, Point playerCords){
        this.packetType = packetTyp;
        this.playerMove = playMove;
        this.playerLocation = new Point(playerCords);
    }

    //pakiet nowego gracza
    DataPacket(PacketType packetTyp, Point playerCords){
        this.packetType = packetTyp;
        this.playerLocation = new Point(playerCords);
    }

    public String toString(){
        return "\nPlayer location: " + playerLocation + "\n" +
                "Player move: "+playerMove + "\n"+
                "Player Num: " + plyerNum + "\n"+
                "Packet type: " + packetType + "\n" +
                "Seed map: " + seedMap + "\n" +
                "Size map: " + sizeMap;
    }
}
