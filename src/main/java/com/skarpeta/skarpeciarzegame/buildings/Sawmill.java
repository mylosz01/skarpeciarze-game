package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.GoldItem;
import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Sawmill extends Building {
    public Sawmill(Point position) {
        super("sawmill",position);
        this.type= BuildingType.SAWMILL;
        this.item = new WoodItem(100);
    }
}
