package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Resource extends Asset {
    public Resource() {
        super(new ImageView(ImageManager.getImage("tree")));
    }
    public void add(Field field){
        if(field.hasResource())
            return;
        super.add(field);
        field.resource = this;
    }
}
