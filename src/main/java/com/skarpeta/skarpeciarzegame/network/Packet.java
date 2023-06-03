package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.BuildingType;
import com.skarpeta.skarpeciarzegame.FieldInfoPacket;
import com.skarpeta.skarpeciarzegame.PlayerMove;
import com.skarpeta.skarpeciarzegame.tools.Point;
import java.io.*;
import java.util.List;

public class Packet implements Serializable {

    List<FieldInfoPacket> fieldInfo;
    BuildingType buildingType;
    int seedMap;
    int sizeMap;
    PacketType packetType;
    public Point position;
    public PlayerMove playerMove;
    public int playerID;

    //packiet inicjalizacyjny
    public Packet(PacketType init, int mapSize, int mapSeed, List<FieldInfoPacket> fieldInfo) {
        this.packetType = init;
        this.seedMap = mapSeed;
        this.sizeMap = mapSize;
        this.fieldInfo = fieldInfo;
    }
    //pakiet do wysylania typu budynku
    public Packet(PacketType packetType, int playerID, BuildingType buildingType, Point fieldPosition) {
        this.packetType = packetType;
        this.playerID = playerID;
        this.buildingType = buildingType;
        this.position = fieldPosition;
    }
    //MOVE, DESTROY, INIT_PLAYER
    public Packet(PacketType packetType, int playerID, Point playerPos) {
        this.packetType = packetType;
        this.playerID = playerID;
        this.position = playerPos;
    }

    public Packet(PacketType playerLeft, int playerID) {
        this.packetType = playerLeft;
        this.playerID = playerID;
    }

    public Packet(PacketType leaveServer) {
        this.packetType = leaveServer;
    }


    public String toString(){
        return "\nPlayer location: " + position + "\n" +
                "Player move: "+playerMove + "\n"+
                "Player Num: " + playerID + "\n"+
                "Packet type: " + packetType + "\n" +
                "Seed map: " + seedMap + "\n" +
                "Size map: " + sizeMap;
    }

    public void sendTo(ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(this);
            System.out.println(" -> sent " + this.packetType);
        } catch (IOException e) {
            System.out.println("Could not sent packet. " + e.getMessage());
        }
    }
}
