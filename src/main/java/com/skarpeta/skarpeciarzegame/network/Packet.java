package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.buildings.BuildingType;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.io.*;
import java.util.List;

public class Packet implements Serializable {

    List<PackedField> fieldInfo;
    BuildingType buildingType;
    PacketType packetType;
    ResourceType resourceType;
    int sizeMap;
    int seedMap;
    public int playerID;
    public Point position;
    public String string;

    public Packet(PacketType init, int mapSize, int mapSeed, List<PackedField> fieldInfo) {
        this.packetType = init;
        this.seedMap = mapSeed;
        this.sizeMap = mapSize;
        this.fieldInfo = fieldInfo;
    }

    public Packet(PacketType building, int playerID, BuildingType buildingType, Point position) {
        this.packetType = building;
        this.playerID = playerID;
        this.buildingType = buildingType;
        this.position = position;
    }
    //MOVE, DESTROY, INIT_PLAYER
    public Packet(PacketType packetType, int playerID, Point position) {
        this.packetType = packetType;
        this.playerID = playerID;
        this.position = position;
    }

    public Packet(PacketType playerLeft, int playerID) {
        this.packetType = playerLeft;
        this.playerID = playerID;
    }

    public Packet(PacketType nickname, int playerID, String name) {
        this.packetType = nickname;
        this.playerID = playerID;
        this.string = name;
    }

    public Packet(PacketType newPlayer, int playerID, Point position, String nickname) {
        this.packetType = newPlayer;
        this.playerID = playerID;
        this.position = position;
        this.string = nickname;
    }

    public Packet(PacketType spawnResource, ResourceType resource, Point position) {
        this.packetType = spawnResource;
        this.resourceType = resource;
        this.position = position;
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
