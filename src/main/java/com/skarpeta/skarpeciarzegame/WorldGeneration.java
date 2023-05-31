package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;

/** Generowanie świata, czytanie pliku z perlin noise */
public class WorldGeneration {

    /** Poszczególne wartości definiują stopień wysokości mapy dla kolejnych typów terenu TerrainType */
    Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};
    ArrayList<String > noiseChannels = new ArrayList<>();
    PixelReader pixels;

    int seed;

    /** Konstruktor tworzy nowy plik noise*/
    WorldGeneration(int seed) {
        this.seed = seed;
        int random = new Random(seed).nextInt(new File("src/main/resources/images/noise").list().length);
        Image noise = ImageManager.getImage("noise/noiseTexture" + random + ".png", 128, 128);
        this.pixels = noise.getPixelReader();
        noiseChannels.add("r");
        noiseChannels.add("g");
        noiseChannels.add("b");
        //Collections.shuffle(noiseChannels);
    }

    /** Generuje pole na podstawie danych z obrazka noise.
     *  Ustawia teren oraz generuje losowe materialy do zbierania przez graczy
     */
    public Field generateField(WorldMap worldMap, Point point) {
        double heightChannel = getRandomChannel(0, point);
        double forestChannel = getRandomChannel(1, point);
        //double thirdChannel = getRandomChannel(2, point); //reserved

        TerrainType terrain = thresholdedTerrain(heightChannel);
        Field field = new Field(worldMap,point, FIELD_WIDTH,terrain,heightChannel);

        if(terrain == TerrainType.GRASS_LAND) {
            field.darken(1-(forestChannel)-threshold[terrain.getIndex()-1]);
            if(randomBoolean(forestChannel))
                field.addResource(new ForestResource());
        }
        if(terrain == TerrainType.MOUNTAINS) {
            if(new Random(seed).nextInt(7) == 0)
                field.addResource(new StoneResource());
        }
        worldMap.getChildren().add(field);
        return field;
    }
    /** zwraca wartość piksela w punkcie Point z losowego kanału RGB*/
    private double getRandomChannel(int i, Point point) {
        return switch (noiseChannels.get(i)) {
            case "r" -> pixels.getColor(point.x, point.y).getRed();
            case "g" -> pixels.getColor(point.x, point.y).getGreen();
            case "b" -> pixels.getColor(point.x, point.y).getBlue();
            default -> 0;
        };
    }
    /** Zwraca losową wartość boolean z prawdopodobieństwem zależnym od value */
    private boolean randomBoolean(double value) {
        double calc = (value-0.3)*60;
        if(calc<1)
            calc=1;
        int random = new Random(seed).nextInt((int) Math.floor(calc));
        return random == 0;
    }

    /** Zwraca teren przypisany danej wysokości (wysokości definiowane poprzez threshold[]) */
    private TerrainType thresholdedTerrain(double value) {
        for (int index = 0; index < threshold.length; index++) {
            if (value <= threshold[index])
                return TerrainType.fromIndex(index);
        }
        throw new RuntimeException("niepoprawny plik");
    }
}
