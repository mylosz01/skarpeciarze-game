package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

public class Mineshaft extends Building {
    public Mineshaft() {
        super(new ImageView(ImageManager.getImagePNG("mineshaft")));
    }
}
