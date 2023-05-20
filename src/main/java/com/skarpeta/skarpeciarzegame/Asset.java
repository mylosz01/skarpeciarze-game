package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Asset extends Pane {
    private ImageView texture;
    public Asset(ImageView texture) {
        this.texture=texture;
        getChildren().add(texture);
    }

    public ImageView getTexture() {
        return texture;
    }

    public void check(Field field){
        this.getTexture().setFitWidth(field.hexagon.width/3);
        this.getTexture().setFitHeight(field.hexagon.width/3);
        field.getChildren().add(this);
    }
}
