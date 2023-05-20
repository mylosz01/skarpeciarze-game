package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Building extends Pane {
    ImageView texture;
    public Building() {
        this.texture = new ImageView(ImageManager.getHouse());
        getChildren().add(texture);
    }
}
