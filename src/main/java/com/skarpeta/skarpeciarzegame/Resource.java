package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Resource extends Asset {
    public Resource() {
        super(new ImageView(ImageManager.getTree()));
    }
    public void check(Field field){
        if(!field.hasResource())
            super.check(field);
    }
}
