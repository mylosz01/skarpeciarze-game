package com.skarpeta.skarpeciarzegame;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;
/** Asset to klasa przechowywujaca teksture obiektów w grze */
public class Asset extends Group {
    private final ImageView texture;

    public Asset(ImageView texture) {
        this.texture=texture;
        getChildren().add(texture);
    }

    public ImageView getTexture() {
        return texture;
    }

    /** align() słuzy do drobnych poprawek ustawienia assetu na ekranie */
    public void align(double x, double y) {
        getTexture().setFitWidth(FIELD_WIDTH * 0.5);
        getTexture().setFitHeight(FIELD_WIDTH * 0.5);
        setTranslateX(x);
        setTranslateY(y);
    }
}
