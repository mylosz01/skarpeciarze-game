package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;

public class DataPacket implements Serializable {

    int seedMap;
    int sizeMap;
    PacketType packetType;
    public Point position;
    public PlayerMove playerMove;
    public int playerID;
    public ServerValidMove validiti;

    //packiet inicjalizacyjny
    public DataPacket(PacketType packetType, int mapSize, int mapSeed) {
        this.packetType=packetType;
        this.seedMap=mapSeed;
        this.sizeMap=mapSize;
    }

    //pakiet ruchu gracza
    DataPacket(PacketType packetTyp,PlayerMove playMove, Point playerCords){
        this.packetType = packetTyp;
        this.playerMove = playMove;
        this.position = new Point(playerCords);
    }

    //dolaczyl nowy gracz
    public DataPacket(PacketType packetType, int playerID, Point playerPos) {
        this.packetType = packetType;
        this.playerID = playerID;
        this.position = playerPos;
    }


    public String toString(){
        return "\nPlayer location: " + position + "\n" +
                "Player move: "+playerMove + "\n"+
                "Player Num: " + playerID + "\n"+
                "Packet type: " + packetType + "\n" +
                "Seed map: " + seedMap + "\n" +
                "Size map: " + sizeMap;
    }
}
