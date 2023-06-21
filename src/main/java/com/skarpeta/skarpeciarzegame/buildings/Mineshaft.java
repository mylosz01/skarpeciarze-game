package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.GoldItem;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;

import java.util.ArrayList;

public class Mineshaft extends Building {

    static {
        buildingCost = new ArrayList<>();
        buildingCost.add(new WoodItem(200));
        buildingCost.add(new StoneItem(100));
    }

    public Mineshaft() {
        super(BuildingType.MINESHAFT);
        this.producedItem = new GoldItem(1);
    }

    public static ArrayList<Item> getBuildingCost() {
        return buildingCost;
    }
}
