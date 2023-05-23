package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;

public class Asset extends Pane {
    private final ImageView texture;

    public Asset(ImageView texture) {
        this.texture=texture;
        getChildren().add(texture);
    }

    public ImageView getTexture() {
        return texture;
    }

    public void allignTo(double x, double y) {
        getTexture().setFitWidth(FIELD_WIDTH * 0.5);
        getTexture().setFitHeight(FIELD_WIDTH * 0.5);
        setTranslateX(x);
        setTranslateY(y);
    }
}
