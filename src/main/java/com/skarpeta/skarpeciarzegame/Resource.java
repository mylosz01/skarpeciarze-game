package com.skarpeta.skarpeciarzegame;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Resource extends Pane {
    Rectangle rectangle;
    public Resource() {
        this.rectangle = new Rectangle(50,50);
        getChildren().add(rectangle);
    }
}
