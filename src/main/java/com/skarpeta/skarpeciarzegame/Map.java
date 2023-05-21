package com.skarpeta.skarpeciarzegame;

import javafx.scene.Group;
import javafx.scene.image.PixelReader;

import java.util.function.Consumer;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;

public class Map extends Group {
    private Field[][] board;
    private final int BOARD_SIZE;
    Map(int size) {
        board = new Field[size][size];
        BOARD_SIZE = size;
        for(int y = 0; y< BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point point = new Point(x,y);
                Field field = calculateField(point);
                getChildren().add(field);
                board[x][y] = field;
            }
        }
    }

    private Field calculateField(Point point) {
        TerrainType terrain = perlinThreshold(point);
        return new Field(this,point, FIELD_WIDTH,terrain);
    }

    private TerrainType perlinThreshold(Point p) {
        PixelReader px = ImageManager.getNoise().getPixelReader();

        double pixel = px.getColor(p.x,p.y).grayscale().getBlue();
        TerrainType terrain;

        Double[] threshold = new Double[]{0.5, 0.55, 0.65};

        if(pixel < threshold[0])
            terrain = TerrainType.WATER;
        else if(pixel < threshold[1])
            terrain = TerrainType.DESERT;
        else if(pixel < threshold[2])
            terrain = TerrainType.GRASS_LAND;
        else
            terrain = TerrainType.MOUNTAINS;
        return terrain;
    }

    public void forEach(Consumer<Field> action) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                action.accept(board[i][j]);
            }
        }
    }

    public Field getField(Point p) {
        return board[p.x][p.y];
    }

    public void selectField(Field field) {
        //przykladowe uzycie addAsset()
        field.addAsset(new Sawmill());
    }
}
