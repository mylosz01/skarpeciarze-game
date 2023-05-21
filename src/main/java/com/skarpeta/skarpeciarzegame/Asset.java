package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Asset extends Pane {
    private final ImageView texture;

    public Asset(ImageView texture) {
        this.texture=texture;
        getChildren().add(texture);
    }

    public ImageView getTexture() {
        return texture;
    }

    public void allignTo(Field field, double x, double y) {
        getTexture().setFitWidth(field.hexagon.width * 0.5);
        getTexture().setFitHeight(field.hexagon.width * 0.5);
        setTranslateX(x);
        setTranslateY(y);
    }
}
