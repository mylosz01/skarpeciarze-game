package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.GoldItem;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Mineshaft extends Building {
    public Mineshaft(Point position) {
        super("mineshaft",position);
        this.type= BuildingType.MINESHAFT;
        this.item = new GoldItem(100);
    }
}
