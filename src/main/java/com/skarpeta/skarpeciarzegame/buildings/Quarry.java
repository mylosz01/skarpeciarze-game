package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

public class Quarry extends Building {
    public Quarry() {
        super(new ImageView(ImageManager.getImage("quarry")));
    }
}
