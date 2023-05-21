package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Building extends Asset {
    public Building() {
        super(new ImageView(ImageManager.getImage("sawmill")));
    }
    public void allignTo(Field field) {
        super.allignTo(field,-10,0.5);
    }
}
