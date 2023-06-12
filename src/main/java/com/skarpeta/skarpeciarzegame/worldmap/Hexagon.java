package com.skarpeta.skarpeciarzegame.worldmap;

import javafx.scene.shape.Polygon;

/** Kształt heksagona definiowant poprzez zbiór punktów. */
public class Hexagon extends Polygon {
    /** Odległość między górną a dolną podstawą */
    private final double height;
    /** Odległość pomiędzy skrajnymi punktami po stronie lewej i prawej */
    private final double width;

    Hexagon(double width) {
        this.width= width;
        this.height = width*(13.0/15);
        setPoints(1);
    }

    void setPoints(double scale){
        getPoints().setAll(
             width*0.5  * scale,  height*0.5 * scale,
             width*0.25 * scale,  height     * scale,
            -width*0.25 * scale,  height     * scale,
            -width*0.5  * scale,  height*0.5 * scale,
            -width*0.25 * scale,  0.0,
             width*0.25 * scale,  0.0
        );
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}