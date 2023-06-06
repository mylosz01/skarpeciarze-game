package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;
import com.skarpeta.skarpeciarzegame.tools.Point;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static com.skarpeta.skarpeciarzegame.app.Catana.FIELD_WIDTH;

import javax.imageio.ImageIO;
/** Generowanie świata, czytanie pliku z perlin noise */
public class WorldGenerator {

    /** Poszczególne wartości definiują stopień wysokości mapy dla kolejnych typów terenu TerrainType */
    Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};
    BufferedImage noise;
    Random random;

    int seed;

    /** Konstruktor tworzy nowy plik noise*/
    WorldGenerator(int seed) {
        try {
            random = new Random(seed);
            this.seed = seed;
            int randomNoiseFile = random.nextInt(new File("src/main/resources/images/noise").list().length);
            noise = ImageIO.read(new File("src/main/resources/images/noise/noiseTexture"+randomNoiseFile+".png"));
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
        Field field = new Field(point, FIELD_WIDTH,terrain,heightChannel);

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
                if (random.nextInt(7) == 0)
                    field.addResource(new StoneResource());
            }
        }
    }
  
    /** Zwraca losową wartość boolean z prawdopodobieństwem zależnym od value */
    private boolean randomBoolean(double value) {
        double calc = (value-0.3)*60;
        if(calc<1)
            calc=1;
        return random.nextInt((int) Math.floor(calc)) == 0;
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
