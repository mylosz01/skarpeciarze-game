package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.util.Random;

public class ForestResource extends Resource {

    public ForestResource() {
        super("forest");
        this.item = new WoodItem(new Random().nextInt(maxResourceRange));
        this.type = ResourceType.FOREST;
    }
}
