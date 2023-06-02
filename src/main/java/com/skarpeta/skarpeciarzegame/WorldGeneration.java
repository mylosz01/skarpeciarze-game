package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.image.PixelReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.skarpeta.skarpeciarzegame.Catana.FIELD_WIDTH;

import javax.imageio.ImageIO;
/** Generowanie świata, czytanie pliku z perlin noise */
public class WorldGeneration {

    /** Poszczególne wartości definiują stopień wysokości mapy dla kolejnych typów terenu TerrainType */
    Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};
    BufferedImage noise;

    int seed;

    /** Konstruktor tworzy nowy plik noise*/
    WorldGeneration(int seed) {
        try {
            this.seed = seed;
            noise = ImageIO.read(new File("src/main/resources/images/noise/noiseTexture1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Generuje pole na podstawie danych z obrazka noise.
     *  Ustawia teren oraz generuje losowe materialy do zbierania przez graczy
     */
    public Field generateField(WorldMap worldMap, Point point) {

        int rgb = noise.getRGB(point.x, point.y);
        double heightChannel = ((rgb >> 16) & 0xFF)/255.0;
        double forestChannel = ((rgb >> 8) & 0xFF)/255.0;

        TerrainType terrain = thresholdedTerrain(heightChannel);
        Field field = new Field(worldMap,point, FIELD_WIDTH,terrain,heightChannel);

        if(terrain == TerrainType.GRASS_LAND)
            field.darken(1-(forestChannel)-threshold[terrain.getIndex()-1]);

        worldMap.getChildren().add(field);
        return field;
    }

    public void generateResource(Field field) {

        int rgb = noise.getRGB(field.position.x, field.position.y);

        double forestChannel = ((rgb >> 8) & 0xFF)/255.0;

        switch (field.terrain) {
            case GRASS_LAND -> {
                if (randomBoolean(forestChannel))
                    field.addResource(new ForestResource());
            }
            case MOUNTAINS -> {
                if (new Random(seed + field.position.x + field.position.y).nextInt(7) == 0)
                    field.addResource(new StoneResource());
            }
        }
    }
  
    /** Zwraca losową wartość boolean z prawdopodobieństwem zależnym od value */
    private boolean randomBoolean(double value) {
        double calc = (value-0.3)*60;
        if(calc<1)
            calc=1;
        int random = new Random(seed + (long)value).nextInt((int) Math.floor(calc));

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
