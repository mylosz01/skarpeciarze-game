package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.GoldItem;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

import java.util.ArrayList;

public class Mineshaft extends Building {

    static {
        buildingCost = new ArrayList<>();
        buildingCost.add(new WoodItem(2));
        buildingCost.add(new StoneItem(2));
    }

    public Mineshaft(Point position) {
        super("mineshaft",position);
        this.type = BuildingType.MINESHAFT;
        this.producedItem = new GoldItem(1);
    }

    public static ArrayList<Item> getBuildingCost() {
        return buildingCost;
    }
}
