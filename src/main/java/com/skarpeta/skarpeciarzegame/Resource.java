package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;

public class Resource extends Asset {
    public Resource() {
        super(new ImageView(ImageManager.getImage("forest")));
    }
    public void allignTo(Field field) {
        super.allignTo(field,-16.2,16);
    }
}
