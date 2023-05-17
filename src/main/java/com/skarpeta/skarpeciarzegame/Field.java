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
        if(p.x%2==0)
            relocate(p.x * width*(2.25/3), p.y * height);
        else
            relocate(p.x * width*(2.25/3), p.y * height+height/2);
    }
}