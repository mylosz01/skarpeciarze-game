package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.buildings.Mineshaft;
import com.skarpeta.skarpeciarzegame.buildings.Quarry;
import com.skarpeta.skarpeciarzegame.buildings.Sawmill;
import com.skarpeta.skarpeciarzegame.inventory.Item;

public enum BuildingType {
    EMPTY(null),
    MINESHAFT(Mineshaft.getBuildingCost()),
    SAWMILL(Sawmill.getBuildingCost()),
    QUARRY(Quarry.getBuildingCost());
    private final Item cost;

    BuildingType(Item cost) {
        this.cost = cost;
    }
    public Item getCost(){
        return cost;
    }
}