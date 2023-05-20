package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Building extends Pane {
    ImageView texture;
    public Building() {
        this.texture = new ImageView(ImageManager.getDomekPng());
        getChildren().add(texture);
    }
}
