package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

import java.util.ArrayList;

public class Sawmill extends Building {

    static {
        buildingCost = new ArrayList<>();
        buildingCost.add(new WoodItem(5));
        buildingCost.add(new StoneItem(2));
    }

    public Sawmill(Point position) {
        super("sawmill",position);
        this.type = BuildingType.SAWMILL;
        this.producedItem = new WoodItem(1);
    }

    public static ArrayList<Item> getBuildingCost() {
        return buildingCost;
    }
}
