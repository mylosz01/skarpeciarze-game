package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.util.Random;

public class ForestResource extends Resource {

    public ForestResource() {
        super(ResourceType.FOREST, "forest" + new Random().nextInt(2));
        this.item = new WoodItem(generateAmount());
    }
}
