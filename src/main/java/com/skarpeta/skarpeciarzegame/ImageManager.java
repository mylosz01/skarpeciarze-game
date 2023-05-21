package com.skarpeta.skarpeciarzegame;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageManager {
    private static final String path = "file:src/main/resources/images/";

    static HashMap<String,Image> images = new HashMap<>();
    public static Image getImage(String imageName) {
        if(!images.containsKey(imageName))
            images.put(imageName, new Image(path + imageName + ".png",128.0,128.0,true,false));
        return images.get(imageName);
    }
}
