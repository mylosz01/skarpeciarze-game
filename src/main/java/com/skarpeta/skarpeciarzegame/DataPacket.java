package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;
import java.util.List;

public class DataPacket implements Serializable {

    List<FieldInfoPacket> fieldInfo;
    BuildingType buildingType;
    int seedMap;
    int sizeMap;
    PacketType packetType;
    public Point position;
    public PlayerMove playerMove;
    public int playerID;

    //packiet inicjalizacyjny
    public DataPacket(PacketType packetType, int mapSize, int mapSeed, List<FieldInfoPacket> fieldInfo) {
        this.packetType=packetType;
        this.seedMap=mapSeed;
        this.sizeMap=mapSize;
        this.fieldInfo=fieldInfo;
    }
    //pakiet do wysylania typu budynku
    public DataPacket(PacketType packetType, int playerID, BuildingType buildingType, Point fieldPosition) {
        this.packetType = packetType;
        this.playerID = playerID;
        this.buildingType = buildingType;
        this.position = fieldPosition;
    }
    //MOVE, DESTROY, INIT_PLAYER
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
