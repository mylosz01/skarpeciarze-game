package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.Field;
import javafx.scene.image.ImageView;

public abstract class Building extends Asset {
    public Building(ImageView texture) {
        super(texture);
    }

    public void allignTo(Field field) {
        super.allignTo(field,-10,0.5);
    }
}
