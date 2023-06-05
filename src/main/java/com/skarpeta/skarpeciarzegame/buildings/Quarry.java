package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.GoldItem;
import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

public class Quarry extends Building {
    public Quarry(Point position) {
        super("quarry",position);
        this.type= BuildingType.QUARRY;
        this.item = new StoneItem(100);
    }
}
