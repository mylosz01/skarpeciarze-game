package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import com.skarpeta.skarpeciarzegame.buildings.BuildingType;

import java.io.Serializable;

public class FieldInfoPacket implements Serializable {
    public ResourceType resourceType = ResourceType.EMPTY;
    public BuildingType buildingType = BuildingType.EMPTY;
    public Point point;

    public FieldInfoPacket(Point point) {
        this.point = point;
    }

    public void setResourceType(ResourceType type) {
        this.resourceType =type;
    }

    public void setBuildingType(BuildingType type) {
        this.buildingType=type;
    }
}
