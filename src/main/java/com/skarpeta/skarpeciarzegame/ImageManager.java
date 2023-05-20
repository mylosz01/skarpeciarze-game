package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageManager {
    private static final String path = "file:src/main/resources/images/";
    static Image domekpng;
    static Image test;
    public static Image getDomekPng() {
        if(domekpng == null)
            domekpng = new Image(path+"domek.png");
        return domekpng;
    }
}
