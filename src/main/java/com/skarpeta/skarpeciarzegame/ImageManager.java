package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.Image;

public class ImageManager {
    private static final String path = "src/main/resources/images";
    Image domekpng;
    Image test;
    public Image getDomekPng() {
        if(domekpng == null)
            domekpng = new Image(path+"domek.png");
        return domekpng;
    }
    public Image getTest() {
        if(test == null)
            test = new Image(path+"test.png");
        return test;
    }
}
