package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.buildings.*;
import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Consumer;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;

public class WorldMap extends Group {
    private Field[][] board;
    private final int BOARD_SIZE;
    Image noise;
    Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};

    WorldMap(int size) {
        int random = new Random().nextInt(10);
        System.out.println(random);
        noise = ImageManager.getImage("noise/noiseTexture" + random + ".png",128,128);
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
        double value = pixels.getColor(point.x,point.y).getRed();
        double value2 = pixels.getColor(point.x,point.y).grayscale().getBrightness();
        double value3 = pixels.getColor(point.x,point.y).getBlue();
        TerrainType terrain = perlinThreshold(value);
        Field field = new Field(this,point, FIELD_WIDTH,terrain);

        if(terrain == TerrainType.GRASS_LAND) {
            field.darken(value2,threshold[terrain.getIndex()-1]);
            if(new Random().nextInt(4) == 0)
                field.addResource(new ForestResource());
        }

        getChildren().add(field);
        return field;
    }

    private TerrainType perlinThreshold(double value) {
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
        if(p.isNegative())
            throw new NoSuchElementException("pola nie moga byc na pozycji ujemnej");
        return board[p.x][p.y];
    }

    public void selectField(Field field) {
        try {
            PlayerManager.getPlayer().movePlayer(field);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        }
        //field.addBuilding(new Sawmill());

    }
}
