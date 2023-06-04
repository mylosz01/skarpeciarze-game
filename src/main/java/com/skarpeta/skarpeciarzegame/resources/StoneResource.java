package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.util.Random;

public class StoneResource extends Resource{
    public StoneResource() {
        super("stones");
        this.item = new StoneItem(new Random().nextInt(maxResourceRange));
        this.type = ResourceType.STONE;
    }
}
