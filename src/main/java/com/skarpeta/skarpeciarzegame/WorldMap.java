package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.Group;
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

    /** Tworzenie mapy o wymiarach size * size, generowana poprzez losowo wybrany plik noise */
    WorldMap(int size,int seed) {
        this.seed = seed;
        WorldGeneration worldGeneration = new WorldGeneration(seed);
        board = new Field[size][size];
        BOARD_SIZE = size;
        for(int y = 0; y< BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point point = new Point(x,y);
                board[x][y] = worldGeneration.generateField(this,point);
            }
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
            PlayerManager.getPlayer().movePlayer(field);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(" "+e.getMessage());
        }
        //field.addBuilding(new Sawmill());
    }

    public Point placePlayer(){
        int newX;
        int newY;
        while(true){
            newX = new Random().nextInt(BOARD_SIZE);
            newY = new Random().nextInt(BOARD_SIZE);

            if(board[newX][newY].terrain != TerrainType.WATER){
                break;
            }
        }

        return new Point(newX,newY);
    }

    public int getSeed(){
        return seed;
    }
}
