package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

public class StoneResource extends Resource{
    public StoneResource() {
        super(ResourceType.STONE, "stones");
        this.item = new StoneItem(generateAmount());
    }
}
