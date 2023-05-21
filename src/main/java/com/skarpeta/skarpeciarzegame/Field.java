package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.Resource;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.StrokeType;

public class Field extends Group {

    private WorldMap worldMap;
    public Point position;
    public Hexagon hexagon;
    public Building building;
    public Resource resource;
    public TerrainType terrain;

    public Field(WorldMap worldMap, Point position, double fieldSize, TerrainType terrain) {
        hexagon = new Hexagon(fieldSize);
        this.worldMap = worldMap;
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
            worldMap.selectField(this);
        }
    }
    public void addBuilding(Building building) {
        if(hasBuilding())
            return;
        this.building = building;
        building.allignTo(this);
        getChildren().add(building);
    }
    public void addResource(Resource resource) {
        if(hasResource())
            return;
        this.resource = resource;
        resource.allignTo(this);
        getChildren().add(resource);
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