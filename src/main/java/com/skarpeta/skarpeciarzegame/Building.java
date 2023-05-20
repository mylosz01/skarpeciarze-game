package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Building extends Asset {
    public Building() {
        super(new ImageView(ImageManager.getSawmill()));
    }
    public void check(Field field){
        if(!field.hasBuilding())
            super.check(field);
    }
}
