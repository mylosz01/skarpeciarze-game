package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Quarry extends Building {

    static {
        buildingCost = new WoodItem(1);
    }

    public Quarry(Point position) {
        super("quarry",position);
        this.type = BuildingType.QUARRY;
        this.producedItem = new StoneItem(100);
    }

    public static Item getBuildingCost() {
        return buildingCost;
    }
}
