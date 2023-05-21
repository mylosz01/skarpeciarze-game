package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.ImageManager;
import javafx.scene.image.ImageView;

public class StoneResource extends Resource{
    public StoneResource() {
        super(new ImageView(ImageManager.getImage("stones")));
    }
}