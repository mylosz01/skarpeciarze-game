package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;

import java.util.ArrayList;

public class Sawmill extends Building {

    static {
        buildingCost = new ArrayList<>();
        buildingCost.add(new WoodItem(50));
    }

    public Sawmill() {
        super(BuildingType.SAWMILL);
        this.producedItem = new WoodItem(1);
    }

    public static ArrayList<Item> getBuildingCost() {
        return buildingCost;
    }
}
