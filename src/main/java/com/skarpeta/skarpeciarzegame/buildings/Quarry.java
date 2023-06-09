package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;

import java.util.ArrayList;

public class Quarry extends Building {

    static {
        buildingCost = new ArrayList<>();
        buildingCost.add(new WoodItem(100));
        buildingCost.add(new StoneItem(40));
    }

    public Quarry() {
        super(BuildingType.QUARRY);
        this.producedItem = new StoneItem(1);
    }

    public static ArrayList<Item> getBuildingCost() {
        return buildingCost;
    }
}
