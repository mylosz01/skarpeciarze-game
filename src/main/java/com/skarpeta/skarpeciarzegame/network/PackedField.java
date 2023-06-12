package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import com.skarpeta.skarpeciarzegame.buildings.BuildingType;
import com.skarpeta.skarpeciarzegame.worldmap.Field;

import java.io.Serializable;

public class PackedField implements Serializable {
    public final Point position;
    public final ResourceType resourceType;
    public final BuildingType buildingType;

    public PackedField(Field field) {
        this.position = field.getPosition();
        this.resourceType = field.getResourceType();
        this.buildingType = field.getBuildingType();
    }
}
