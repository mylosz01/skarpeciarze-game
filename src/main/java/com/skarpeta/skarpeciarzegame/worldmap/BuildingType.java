package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.buildings.Building;
import com.skarpeta.skarpeciarzegame.buildings.Mineshaft;
import com.skarpeta.skarpeciarzegame.buildings.Quarry;
import com.skarpeta.skarpeciarzegame.buildings.Sawmill;
import com.skarpeta.skarpeciarzegame.inventory.Item;

import java.util.ArrayList;

public enum BuildingType {
    EMPTY(null),
    MINESHAFT(Mineshaft.getBuildingCost()),
    SAWMILL(Sawmill.getBuildingCost()),
    QUARRY(Quarry.getBuildingCost());
    private final ArrayList<Item> cost;

    BuildingType(ArrayList<Item> cost) {
        this.cost = cost;
    }
    public ArrayList<Item> getCost(){
        return cost;
    }

    public Building newBuilding() {
        return switch(this) {
            case EMPTY -> null;
            case MINESHAFT -> new Mineshaft();
            case SAWMILL -> new Sawmill();
            case QUARRY -> new Quarry();
        };
    }
}