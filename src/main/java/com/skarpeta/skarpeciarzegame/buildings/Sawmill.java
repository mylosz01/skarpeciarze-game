package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Sawmill extends Building {

    static {
        buildingCost = new WoodItem(1);
    }

    public Sawmill(Point position) {
        super("sawmill",position);
        this.type = BuildingType.SAWMILL;
        this.producedItem = new WoodItem(100);
    }

    public static Item getBuildingCost() {
        return buildingCost;
    }
}
