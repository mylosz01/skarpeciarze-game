package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.Asset;
import javafx.scene.image.ImageView;

public abstract class Building extends Asset {
    public Building(ImageView texture) {
        super(texture);
        align();
    }
    private void align() {
        super.allignTo(-10,0.5);
    }
}
