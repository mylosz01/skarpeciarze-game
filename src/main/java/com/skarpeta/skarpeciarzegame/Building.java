package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Building extends Asset {
    public Building() {
        super(new ImageView(ImageManager.getImage("sawmill")));
    }
    public void add(Field field){
        if(field.hasBuilding())
            return;
        super.add(field);
        field.building = this;
    }
}
