package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class StoneResource extends Resource{
    public StoneResource() {
        super(new ImageView(ImageManager.getImage("stones")));
    }
}
