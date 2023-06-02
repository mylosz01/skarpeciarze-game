package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;

public class DataPacket implements Serializable {

    BuildingType buildingType;
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
    DataPacket(PacketType packetTyp, PlayerMove playMove, Point playerCords){
        this.packetType = packetTyp;
        this.playerMove = playMove;
        this.position = new Point(playerCords);
    }

    //pakiet do wysylania typu budynku
    DataPacket(PacketType packetType, BuildingType buildingType, Point position){
        this.packetType = packetType;
        this.buildingType = buildingType;
        this.position = position;
    }

    //field position
    DataPacket(PacketType packetType, Point resourceCords){
        this.packetType = packetType;
        this.position = resourceCords;
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
