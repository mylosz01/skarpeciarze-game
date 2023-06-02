package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.inventory.StoneItem;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.image.ImageView;

import java.util.Random;

public class StoneResource extends Resource{
    public StoneResource() {
        super(new ImageView(ImageManager.getImage("stones.png",128,128)));
        this.item = new StoneItem(new Random().nextInt(maxResourceRange));
        this.type = ResourceType.STONE;
    }
}
