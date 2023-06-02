package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.io.Serializable;

public class FieldInfoPacket implements Serializable {
    ResourceType resourceType = ResourceType.EMPTY;
    BuildingType buildingType = BuildingType.EMPTY;
    Point point;

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
