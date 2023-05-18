package com.skarpeta.skarpeciarzegame;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;
import static com.skarpeta.skarpeciarzegame.Catana.fieldColors;

public class Map {
    public Field[][] board;
    private final int BOARD_SIZE;
    Map(int size) {
        board = new Field[size][size];
        BOARD_SIZE = size;
        for(int y = 0; y< BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point point = new Point(x,y);
                Field field = new Field(point, FIELD_WIDTH, fieldColors[point.isEven()]);
                board[x][y] = field;
            }
        }
    }
    public Field getField(Point p) {
        return board[p.x][p.y];
    }
}
