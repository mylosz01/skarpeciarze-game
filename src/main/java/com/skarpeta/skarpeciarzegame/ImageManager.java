package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.Image;

public class ImageManager {
    private static final String path = "file:src/main/resources/images/";
    private static Image house;
    private static Image tree;
    private static Image getNoise;
    public static Image getHouse() {
        if(house == null)
            house = new Image(path + "house.png");
        return house;
    }
    public static Image getTree() {
        if(tree == null)
            tree = new Image(path + "tree.png");
        return tree;
    }
    public static Image getNoise() {
        if(getNoise == null)
            getNoise = new Image(path + "noiseTexture2.png");
        return getNoise;
    }
}
