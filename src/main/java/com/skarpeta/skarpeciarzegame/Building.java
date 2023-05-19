package com.skarpeta.skarpeciarzegame;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Building extends Pane {
    //tymczasowa grafika budynku
    Rectangle rectangle;

    public Building() {
        this.rectangle = new Rectangle(50,50);
        getChildren().add(rectangle);
    }
}
