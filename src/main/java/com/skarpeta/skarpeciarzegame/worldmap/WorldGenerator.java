package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;
import com.skarpeta.skarpeciarzegame.tools.ChannelSplitter;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static com.skarpeta.skarpeciarzegame.app.Catana.FIELD_WIDTH;

import javax.imageio.ImageIO;
/** Generowanie świata, czytanie pliku z perlin noise */
public class WorldGenerator {

    /** Poszczególne wartości definiują stopień wysokości mapy dla kolejnych typów terenu TerrainType */
    Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};
    BufferedImage noise;
    Double[][] noiseChannelHeight;
    Double[][] noiseChannelForest;
    Random random;
    int seed;

    /** Konstruktor tworzy nowy plik noise*/
    public WorldGenerator(int seed) {
        try {
            this.seed = seed;
            this.random = new Random(seed);

            int filesAmount = new File("src/main/resources/images/noise").list().length;
            noise = ImageIO.read(new File("src/main/resources/images/noise/noiseTexture"+random.nextInt(filesAmount)+".png"));

            List<Double[][]> channels = ChannelSplitter.splitImage(noise);
            Collections.shuffle(channels,random);
            noiseChannelHeight = channels.get(0);
            noiseChannelForest = channels.get(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Generuje pole na podstawie danych z obrazka noise.
     *  Ustawia teren oraz generuje losowe materialy do zbierania przez graczy
     */
    public Field generateField(WorldMap worldMap, Point point) {
        TerrainType terrain = thresholdedTerrain(noiseChannelHeight[point.x][point.y]);
        Field field = new Field(point, FIELD_WIDTH,terrain, noiseChannelHeight[point.x][point.y]);

        if(terrain == TerrainType.GRASS_LAND)
            field.darken(1-(noiseChannelForest[point.x][point.y])-threshold[terrain.getIndex()-1]);

        worldMap.getChildren().add(field);
        return field;
    }

    public void generateResource(Field field) {

        switch (field.getTerrain()) {
            case GRASS_LAND -> {
                if (randomBoolean(noiseChannelForest[field.getPosition().x][field.getPosition().y]))
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
        return TerrainType.fromIndex(
                IntStream.range(0, threshold.length)
                .filter(index -> (value <= threshold[index]))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid terrain")));
    }

    public void setBiomes(WorldMap worldMap){
        List<Island> islands = Island.findIslands(worldMap);
        islands.forEach(island->{
            Integer[] rgb = new Integer[]{random.nextInt(255),random.nextInt(255),random.nextInt(255)};
            island.forEach(field -> field.setColor(Color.rgb(rgb[0],rgb[1],rgb[2])));
        });
    }
}
