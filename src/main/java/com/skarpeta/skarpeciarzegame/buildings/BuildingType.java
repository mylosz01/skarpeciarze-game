package com.skarpeta.skarpeciarzegame.buildings;


import com.skarpeta.skarpeciarzegame.inventory.Item;

import java.util.ArrayList;

public enum BuildingType implements Craftable{
    EMPTY(null,""),
    MINESHAFT(Mineshaft.getBuildingCost(),"Can be placed on Stone. Generates Gold."),
    SAWMILL(Sawmill.getBuildingCost(),"Can be placed on Grass. Generates Wood."),
    QUARRY(Quarry.getBuildingCost(),"Can be placed on Stone. Generates Stone.");
    private final ArrayList<Item> cost;
    public final String description;

    BuildingType(ArrayList<Item> cost, String description) {
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
}