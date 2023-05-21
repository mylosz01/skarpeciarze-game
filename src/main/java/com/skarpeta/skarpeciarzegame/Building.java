package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public abstract class Building extends Asset {
    public Building(ImageView texture) {
        super(texture);
    }

    public void allignTo(Field field) {
        super.allignTo(field,-10,0.5);
    }
}
