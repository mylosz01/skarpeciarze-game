package com.skarpeta.skarpeciarzegame.tools;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageManager {
    private static final String path = "file:src/main/resources/images/";

    static HashMap<String,Image> images = new HashMap<>();
    public static Image getImage(String imageName,double sizeX,double sizeY) {
        if(!images.containsKey(imageName))
            images.put(imageName, new Image(path + imageName,sizeX,sizeY,true,false));
        return images.get(imageName);
    }
}
