package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.resources.ForestResource;
import com.skarpeta.skarpeciarzegame.resources.StoneResource;
import com.skarpeta.skarpeciarzegame.tools.ChannelSplitter;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.skarpeta.skarpeciarzegame.app.Catana.FIELD_WIDTH;

import javax.imageio.ImageIO;
/** Generowanie świata, czytanie pliku z perlin noise */
public class WorldGenerator {

    /** Poszczególne wartości definiują stopień wysokości mapy dla kolejnych typów terenu TerrainType */
    Double[] threshold = new Double[]{0.5, 0.55, 0.65, 1.0};
    BufferedImage noise;
    List<Double[][]> channels;
    Random random;
    int seed;

    /** Konstruktor tworzy nowy plik noise*/
    public WorldGenerator(int seed) {
        try {
            random = new Random(seed);
            this.seed = seed;
            int filesAmount = new File("src/main/resources/images/noise").list().length;
            noise = ImageIO.read(new File("src/main/resources/images/noise/noiseTexture"+random.nextInt(filesAmount)+".png"));
            channels = ChannelSplitter.splitImage(noise);
            Collections.shuffle(channels,random);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Generuje pole na podstawie danych z obrazka noise.
     *  Ustawia teren oraz generuje losowe materialy do zbierania przez graczy
     */
    public Field generateField(WorldMap worldMap, Point point) {
        double heightChannel = channels.get(0)[point.x][point.y];
        double forestChannel = channels.get(1)[point.x][point.y];

        TerrainType terrain = thresholdedTerrain(heightChannel);
        Field field = new Field(point, FIELD_WIDTH,terrain,heightChannel);

        if(terrain == TerrainType.GRASS_LAND)
            field.darken(1-(forestChannel)-threshold[terrain.getIndex()-1]);

        worldMap.getChildren().add(field);
        return field;
    }

    public void generateResource(Field field) {

        double forestChannel = channels.get(1)[field.getPosition().x][field.getPosition().y];

        switch (field.getTerrain()) {
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
    public void setBiomes(WorldMap worldMap){
        List<Island> islands = Island.findIslands(worldMap);
        islands.forEach(island->{
            Integer[] rgb = new Integer[]{random.nextInt(255),random.nextInt(255),random.nextInt(255)};
            island.forEach(field -> field.setColor(Color.rgb(rgb[0],rgb[1],rgb[2])));
        });
    }
}
