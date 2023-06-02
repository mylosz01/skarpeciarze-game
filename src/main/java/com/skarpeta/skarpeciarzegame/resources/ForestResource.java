package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.inventory.WoodItem;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.image.ImageView;

import java.util.Random;

public class ForestResource extends Resource {

    public ForestResource() {
        super(new ImageView(ImageManager.getImage("forest" + new Random().nextInt(2) +".png",128,128)));
        this.item = new WoodItem(new Random().nextInt(maxResourceRange));
        this.type = ResourceType.FOREST;
    }
}
