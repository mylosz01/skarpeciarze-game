package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Craftable;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;

import java.util.ArrayList;

public enum BuildingType implements Craftable {
    EMPTY(TerrainType.WATER,null,""),
    MINESHAFT(TerrainType.MOUNTAINS,Mineshaft.getBuildingCost(),"Can be placed on Stone. Generates Gold."),
    SAWMILL(TerrainType.GRASS_LAND,Sawmill.getBuildingCost(),"Can be placed on Grass. Generates Wood."),
    QUARRY(TerrainType.MOUNTAINS,Quarry.getBuildingCost(),"Can be placed on Stone. Generates Stone.");
    private final ArrayList<Item> cost;
    private final String description;
    private final TerrainType terrain;

    BuildingType(TerrainType terrain, ArrayList<Item> cost, String description) {
        this.terrain = terrain;
        this.cost = cost;
        this.description = description;
    }

    public Building newBuilding() {
        return switch(this) {
            case EMPTY -> null;
            case MINESHAFT -> new Mineshaft();
            case SAWMILL -> new Sawmill();
            case QUARRY -> new Quarry();
        };
    }
    @Override
    public ArrayList<Item> getCost(){
        return cost;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public String getDescription() {
        return description;
    }
    public TerrainType placableTerrain(){
        return terrain;
    }
}