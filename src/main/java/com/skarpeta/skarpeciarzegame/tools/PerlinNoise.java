package com.skarpeta.skarpeciarzegame.tools;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class PerlinNoise {
    /** Zwraca liste trzech tablic wypełnionych wartościami double w zakresie 0-1, elementy listy odpowiadające kolejno kanałom RGB */

    private final List<Double[][]> channels;
    private final BufferedImage image;
    public PerlinNoise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        this.image = image;

        Double[][] redChannel = new Double[width][height];
        Double[][] greenChannel = new Double[width][height];
        Double[][] blueChannel = new Double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);

                double normalizedRed = ((rgb >> 16) & 255) / 255.0;
                double normalizedGreen = ((rgb >> 8) & 255) / 255.0;
                double normalizedBlue = ((rgb >> 0) & 255) / 255.0;

                redChannel[i][j] = normalizedRed;
                greenChannel[i][j] = normalizedGreen;
                blueChannel[i][j] = normalizedBlue;
            }
        }
        channels = new ArrayList<>();
        channels.add(redChannel);
        channels.add(greenChannel);
        channels.add(blueChannel);
    }


    public BufferedImage getImage() {
        return image;
    }
    public Double getRed(Point point){
        return channels.get(0)[point.x][point.y];
    }
    public Double getGreen(Point point){
        return channels.get(1)[point.x][point.y];
    }

    public void shuffleChannels(Random random) {
        Collections.shuffle(channels,random);
    }
}
