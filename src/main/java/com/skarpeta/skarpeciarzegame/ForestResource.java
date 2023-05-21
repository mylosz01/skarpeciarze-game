package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class ForestResource extends Resource {
    public ForestResource() {
        super(new ImageView(ImageManager.getImage("forest")));
    }
}
