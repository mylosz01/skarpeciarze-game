package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.Random;
import java.util.function.Consumer;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;

public class WorldMap extends Group {
    private Field[][] board;
    private final int BOARD_SIZE;
    Image noise = ImageManager.getImage("noise/noiseTexture" + new Random().nextInt(10));
    WorldMap(int size) {
        PixelReader pixels = noise.getPixelReader();
        board = new Field[size][size];
        BOARD_SIZE = size;
        for(int y = 0; y< BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point point = new Point(x,y);
                board[x][y] = generateField(point,pixels);
            }
        }
    }

    private Field generateField(Point point, PixelReader pixels) {
        double value = pixels.getColor(point.x,point.y).grayscale().getBlue();
        TerrainType terrain = perlinThreshold(value);
        Field field = new Field(this,point, FIELD_WIDTH,terrain);

        if(terrain == TerrainType.GRASS_LAND && new Random().nextInt(4)==0)
            field.addResource(new ForestResource());

        getChildren().add(field);
        return field;
    }

    private TerrainType perlinThreshold(double value) {


        Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};
        for (int index = 0; index < threshold.length; index++) {
            if (value <= threshold[index])
                return TerrainType.fromIndex(index);
        }
        throw new RuntimeException("niepoprawny plik");
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
        field.addBuilding(new Quarry());
    }
}
