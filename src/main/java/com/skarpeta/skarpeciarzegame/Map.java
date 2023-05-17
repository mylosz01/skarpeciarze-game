package com.skarpeta.skarpeciarzegame;

import static com.skarpeta.skarpeciarzegame.Catana.TILE_SIZE;
import static com.skarpeta.skarpeciarzegame.Catana.tileColors;

public class Map {
    public Field[][] board;
    private final int BOARD_SIZE;
    private final int ROWS_COUNT;
    Map(int size) {
        board = new Field[size][size];
        BOARD_SIZE = size;
        ROWS_COUNT = (int) Math.ceil((double) BOARD_SIZE/3);
        for(int y = 0; y< BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point point = new Point(x,y);
                Field field = new Field(point,TILE_SIZE,tileColors[(x+y)%2]);
                board[x][y] = field;
            }
        }
    }
    public Field getField(Point p) {
        return board[p.x][p.y];
    }
}
