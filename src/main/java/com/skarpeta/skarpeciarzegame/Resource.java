package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Resource extends Pane {
    ImageView texture;
    public Resource() {
        this.texture = new ImageView(ImageManager.getTree());
        getChildren().add(texture);
    }
}
