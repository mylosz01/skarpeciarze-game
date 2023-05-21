package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Resource extends Asset {
    public Resource() {
        super(new ImageView(ImageManager.getImage("forest")));
        setTranslateX(-15);
        setTranslateY(25);
    }
    public void add(Field field){
        if(field.hasResource())
            return;
        super.add(field);
        field.resource = this;
    }
}
