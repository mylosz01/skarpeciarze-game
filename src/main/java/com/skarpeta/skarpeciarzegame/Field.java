package com.skarpeta.skarpeciarzegame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;


public class Field extends Group {

    public Point position;
    public Hexagon hexagon;
    public Building building;
    public Resource resource;
    public TerrainType terrain;

    public Field(Point position,double fieldSize) {
        hexagon = new Hexagon(fieldSize);
        this.position = position;
        setTerrain(TerrainType.GRASS_LAND);
        move(position);
        hexagon.setStrokeType(StrokeType.INSIDE);
        hexagon.setStrokeWidth(fieldSize * 0.05);
        setOnMouseClicked((e)->click());
        getChildren().add(hexagon);
    }

    public void setTerrain(TerrainType terrain) {
        this.terrain=terrain;
        Color color;
        color=switch (terrain){
            case MOUNTAINS -> Color.GRAY;
            case WATER -> Color.BLUE;
            case DESERT -> Color.YELLOW;
            case GRASS_LAND -> Color.LIME;
        };
        hexagon.setFill(color);
        hexagon.setStroke(color.darker());
    }

    public void click() {
        System.out.println("clicked "+position);
        System.out.println(getLayoutX()+", "+getLayoutY());

        //przykladowe uzycie addBuilding i addResource
        addBuilding(new Sawmill());
        addResource(new Resource());
    }

    public void addBuilding(Building building) {
        if(!hasBuilding()){
            this.building = building;
            getChildren().add(building);
        }
        else {
            System.out.println("budynek juz istnieje!");
        }
    }

    public void addResource(Resource resource) {
        if(!hasResource()){
            this.resource = resource;
            getChildren().add(resource);
        }
        else {
            System.out.println("zloze juz istnieje!");
        }
    }
    private boolean hasBuilding() {
        return !(building ==null);
    }
    private boolean hasResource() {
        return !(resource ==null);
    }


    public void move(Point p) {
        double x = p.x * hexagon.width * 0.75;
        double y = p.y * hexagon.height;
        if(p.x%2 == 1)
            y += hexagon.height * 0.5;
        relocate(x, y);
    }
}