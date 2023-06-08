package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.app.Catana;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Consumer;

/** Mapa gry, definiowana przez tablicę pól Field */
public class WorldMap extends Group {
    /** Zbiór pól mapy */
    private final Field[][] board;
    private final int mapSize;
    private final int seed;

    /** Tworzenie mapy o wymiarach size * size, generowana poprzez losowo wybrany plik noise */
    public WorldMap(int size, int seed) {
        this.seed = seed;
        WorldGenerator worldGenerator = new WorldGenerator(seed);
        board = new Field[size][size];
        mapSize = size;
        for(int y = 0; y< mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                Point point = new Point(x,y);
                Field newField = worldGenerator.generateField(this,point);
                newField.setOnMouseClicked((e)->click(e,newField));
                board[x][y] = newField;
            }
        }
    }


    void click(MouseEvent mouseEvent, Field field) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            selectField(field);
        }
    }

    /** wyswietla jeden z noise odpowiedzialnych za generacje drzew */
    public void debugModeTerrain()
    {
        this.forEach((e)->e.setColor(Color.BLACK.interpolate(Color.WHITE,e.height)));//debug);
    }

    /** Wykonywanie akcji dla każdego z pola */
    public void forEach(Consumer<Field> action) {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                action.accept(board[i][j]);
            }
        }
    }

    public Field getField(Point p) {
        if(p.isNegative())
            throw new NoSuchElementException("pola nie moga byc na pozycji ujemnej");
        return board[p.x][p.y];
    }

    /** Wybieranie pola przez gracza,
     *  poruszanie się na poprawnie wybrane pole
     */
    public void selectField(Field field) {
        try {
            Catana.getClientThread().getPlayer().sendMove(field);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(" "+e.getMessage());
        }
        //field.addBuilding(new Sawmill());
    }

    /** wylosowanie pola dla gracza z gwarancja drewna na wyspie*/
    public Point placePlayer() {
        List<Island> islands = Island.findIslands(this);
        Island randomIsland;

        if(!hasTrees())
            return getRandomPosition();

        do {
            randomIsland = islands.get(new Random().nextInt(islands.size()));
        } while (randomIsland.treesCount == 0);

        return randomIsland.fields.get(new Random().nextInt(randomIsland.fields.size())).position;
    }

    private boolean hasTrees() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                Field field = getField(new Point(j,i));
                if(field.hasResource() && field.resource.type == ResourceType.FOREST)
                    return true;
            }
        }
        return false;
    }

    private Point getRandomPosition() {
        Point point;
        do {
            point = new Point(new Random().nextInt(mapSize),new Random().nextInt(mapSize));
        } while(board[point.x][point.y].terrain == TerrainType.WATER);
        return point;
    }

    public int getSeed(){
        return seed;
    }

    public void generateResources() {
        WorldGenerator worldGenerator = new WorldGenerator(seed);
        forEach(worldGenerator::generateResource);
    }

    public int getMapSize() {
        return mapSize;
    }
}
