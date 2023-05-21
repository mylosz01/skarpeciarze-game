package com.skarpeta.skarpeciarzegame;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.StrokeType;

public class Field extends Group {

    private Map map;
    public Point position;
    public Hexagon hexagon;
    public Building building;
    public Resource resource;
    public TerrainType terrain;

    public Field(Map map, Point position, double fieldSize, TerrainType terrain) {
        hexagon = new Hexagon(fieldSize);
        this.map = map;
        this.position = position;
        setTerrain(terrain);
        move(position);
        hexagon.setStrokeType(StrokeType.INSIDE);
        hexagon.setStrokeWidth(fieldSize * 0.05);
        setOnMouseClicked(this::click);
        getChildren().add(hexagon);
    }

    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
        hexagon.setFill(terrain.getColor().primary);
        if(terrain != TerrainType.WATER)
            hexagon.setStroke(terrain.getColor().darker);
    }

    private void click(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            System.out.println("clicked "+position);
            System.out.println(getLayoutX()+", "+getLayoutY());
            map.selectField(this);
        }
    }
    public void addAsset(Asset content) {
        content.add(this);
    }
    public boolean hasBuilding() {
        return building != null;
    }
    public boolean hasResource() {
        return resource != null;
    }


    public void move(Point p) {
        double x = p.x * hexagon.width * 0.75;
        double y = p.y * hexagon.height;
        if(p.x%2 == 1)
            y += hexagon.height * 0.5;
        relocate(x, y);
    }
}