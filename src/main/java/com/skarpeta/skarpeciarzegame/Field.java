package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.Resource;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/** Heksagonalne pole, składnik mapy,
 * definiowany poprzez typ terenu (TerrainType)
 */
public class Field extends Group {

    private final WorldMap worldMap;
    public Point position;
    public Hexagon hexagon;
    public Building building;
    public Resource resource;
    public TerrainType terrain;
    /**do przyszlego wykorzystania, przechowuje noise channel */
    public double height;

    /** Tworzy heksagonalne pole mapy worldMap, w punkcie (tablicowym) position, o terenie terrain
     *  Pozycja na ekranie jest zmieniana poprzez move()
     */
    public Field(WorldMap worldMap, Point position, double fieldSize, TerrainType terrain, double height) {
        hexagon = new Hexagon(fieldSize);
        this.height = height;
        this.worldMap = worldMap;
        this.position = position;
        setTerrain(terrain);
        move(position);
        hexagon.setStrokeType(StrokeType.INSIDE);
        hexagon.setStrokeWidth(fieldSize * 0.05);
        setOnMouseClicked(this::click);
        getChildren().add(hexagon);
    }

    /** Zmienia teren pola i ustawia jego wygląd */
    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
        hexagon.setFill(terrain.getColor().primary);
        if(terrain != TerrainType.WATER)
            hexagon.setStroke(terrain.getColor().darker);
    }

    /** Override koloru, nie ma powrotu (debug only!!!!1)*/
    public void setColor(Color color) {
        hexagon.setFill(color);
    }

    /** Wykrywa przyciśnięcie pola lub leżących na nim obiektów */
    private void click(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            System.out.println("clicked field: "+position);
            System.out.println("mosue coords: "+getLayoutX()+", "+getLayoutY());
            worldMap.selectField(this);
        }
    }
    /** Dodaje budynek do pola */
    public void addBuilding(Building building) {
        if(hasBuilding())
            return;
        this.building = building;
        getChildren().add(building);
    }
    /** Dodaje materiały do pola, gotowe do zebrania przez gracza */
    public void addResource(Resource resource) {
        if(hasResource())
            return;
        this.resource = resource;
        getChildren().add(resource);
    }
    /** Zwraca true gdy pole posiada wybudowany budynek */
    public boolean hasBuilding() {
        return building != null;
    }
    /** Zwraca true gdy pole posiada wygenerowane materiały */
    public boolean hasResource() {
        return resource != null;
    }

    /** Konwersja koordynatów tablicowych całkowitych (Point) na poprawne wyświetlanie heksagonów na ekranie.
     *  Nieparzyste rzędy przesuwane są o połowe wysokości w dół
     */
    public void move(Point p) {
        double x = p.x * hexagon.width * 0.75;
        double y = p.y * hexagon.height;
        if(p.x%2 == 1)
            y += hexagon.height * 0.5;
        relocate(x, y);
    }

    /** Wizualne przyciemnienie pola o podaną value */
    public void darken(double value) {
        Color color = (Color) hexagon.getFill();
        Color stroke = (Color) hexagon.getStroke();
        double interpolate = (value)*4;
        hexagon.setFill(color.interpolate(terrain.getColor().darker,interpolate));
        hexagon.setStroke(stroke.interpolate(terrain.getColor().darker,interpolate));
    }
}