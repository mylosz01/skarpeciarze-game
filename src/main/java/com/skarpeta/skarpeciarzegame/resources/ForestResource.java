package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.ImageManager;
import javafx.scene.image.ImageView;

public class ForestResource extends Resource {
    public ForestResource() {
        super(new ImageView(ImageManager.getImage("forest")));
    }
}
