package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Sawmill extends Building {

    public static Item itemCost = new WoodItem(312);

    public Sawmill(Point position) {
        super("sawmill",position);
        this.type= BuildingType.SAWMILL;
        this.item = new WoodItem(100);
    }
}
