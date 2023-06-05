package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.GoldItem;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Mineshaft extends Building {

    static {
        buildingCost = new StoneItem(1);
    }

    public Mineshaft(Point position) {
        super("mineshaft",position);
        this.type = BuildingType.MINESHAFT;
        this.producedItem = new GoldItem(100);
    }

    public static Item getBuildingCost() {
        return buildingCost;
    }
}
