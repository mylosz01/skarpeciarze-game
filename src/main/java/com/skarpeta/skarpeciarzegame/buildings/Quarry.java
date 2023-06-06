package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

import java.util.ArrayList;

public class Quarry extends Building {

    static {
        buildingCost = new ArrayList<>();
        buildingCost.add(new WoodItem(2));
        buildingCost.add(new StoneItem(2));
    }

    public Quarry() {
        super("quarry");
        this.type = BuildingType.QUARRY;
        this.producedItem = new StoneItem(1);
    }

    public static ArrayList<Item> getBuildingCost() {
        return buildingCost;
    }
}
