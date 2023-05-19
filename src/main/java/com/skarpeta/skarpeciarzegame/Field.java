package com.skarpeta.skarpeciarzegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;


public class Field extends Hexagon {

    public Point position;

    public Field(Point position,double fieldSize,Color color) {
        super(fieldSize);
        this.position = position;
        move(position);
        setFill(color);
        setStrokeType(StrokeType.INSIDE);
        setStroke(color.darker());
        setStrokeWidth(2);
    }
    public void move(Point p) {
        double x = p.x * width * 0.75;
        double y = p.y * height;
        if(p.x%2 == 1)
            y += height * 0.5;
        relocate(x, y);
    }
}