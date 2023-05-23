package com.skarpeta.skarpeciarzegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/** Kształt heksagona definiowant poprzez zbiór punktów. */
public class Hexagon extends Polygon {
    /** Odległość między górną a dolną podstawą */
    double height;
    /** Odległość pomiędzy skrajnymi punktami po stronie lewej i prawej */
    double width;
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    Hexagon(double width) {
        this.width= width;
        this.height = width*(13.0/15);
        getPoints().addAll(
                width/2,  height/2, //1
                width/4,       height,//2
                -width/4,      height,//3
                -width/2,     height/2,//4
                -width/4,      0.0,//5
                width/4,       0.0//6
        );
    }
}