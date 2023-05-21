package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.ImageManager;
import javafx.scene.image.ImageView;

public class Sawmill extends Building {
    public Sawmill() {
        super(new ImageView(ImageManager.getImage("sawmill")));
    }
}
