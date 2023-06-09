package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.Resource;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/** Heksagonalne pole, składnik mapy,
 * definiowany poprzez typ terenu (TerrainType)
 */
public class Field extends Group {

    private final Hexagon hexagon;
    private final Point position;
    private final double height;
    private Building building;
    private Resource resource;
    private TerrainType terrain;

    /** Tworzy heksagonalne pole mapy worldMap, w punkcie (tablicowym) position, o terenie terrain
     *  Pozycja na ekranie jest zmieniana poprzez move()
     */
    public Field(Point position, double fieldSize, TerrainType terrain, double height) {
        this.hexagon = new Hexagon(fieldSize);
        this.position = position;
        this.height = height;
        setTerrain(terrain);
        move(position);
        hexagon.setStrokeType(StrokeType.INSIDE);
        hexagon.setStrokeWidth(fieldSize * 0.05);
        getChildren().add(hexagon);
    }

    /** Zmienia teren pola i ustawia jego wygląd */
    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
        hexagon.setFill(terrain.getColor().primary);
        if(terrain != TerrainType.WATER)
            hexagon.setStroke(terrain.getColor().midway);
    }

    /** Override koloru, nie ma powrotu (debug only!!!!1)*/
    public void setColor(Color color) {
        hexagon.setFill(color);
    }

    /** Dodaje budynek do pola */
    public void addBuilding(Building building) {
        if(hasBuilding() || building==null)
            return;
        this.building = building;
        getChildren().add(building);
    }
    /** Dodaje materiały do pola, gotowe do zebrania przez gracza */
    public void addResource(Resource resource) {
        if(hasResource() || resource==null)
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
        double x = p.x * hexagon.getWidth() * 0.75;
        double y = p.y * hexagon.getHeight();
        if(p.x%2 == 1)
            y += hexagon.getHeight() * 0.5;
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

    public void destroyBuilding() {
        if(hasBuilding()){
            getChildren().remove(building);
            building = null;
        }
    }

    public void destroyResource() {
        if(hasResource()){
            getChildren().remove(resource);
            resource = null;
        }
    }

    public Point getPosition() {
        return position;
    }

    public Building getBuilding() {
        return building;
    }

    public Resource getResource() {
        return resource;
    }
    public ResourceType getResourceType() {
        return hasResource() ? getResource().getType() : ResourceType.EMPTY;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public BuildingType getBuildingType() {
        return hasBuilding() ? getBuilding().getType() : BuildingType.EMPTY;
    }
}