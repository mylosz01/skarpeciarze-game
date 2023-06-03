package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.network.Player;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Consumer;

/** Mapa gry, definiowana przez tablicę pól Field */
public class WorldMap extends Group {
    /** Zbiór pól mapy */
    private final Field[][] board;
    private final int BOARD_SIZE;
    private final int seed;
    Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    /** Tworzenie mapy o wymiarach size * size, generowana poprzez losowo wybrany plik noise */
    public WorldMap(int size, int seed) {
        this.seed = seed;
        WorldGenerator worldGenerator = new WorldGenerator(seed);
        board = new Field[size][size];
        BOARD_SIZE = size;
        for(int y = 0; y< BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
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
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
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
            player.sendMove(field);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(" "+e.getMessage());
        }
        //field.addBuilding(new Sawmill());
    }

    public Point placePlayer(){
        Point point;
        do {
            point = new Point(new Random().nextInt(BOARD_SIZE),new Random().nextInt(BOARD_SIZE));
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
}