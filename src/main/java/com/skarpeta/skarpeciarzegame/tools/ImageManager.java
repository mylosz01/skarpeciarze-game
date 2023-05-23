package com.skarpeta.skarpeciarzegame.tools;

import javafx.scene.image.Image;

import java.util.HashMap;
/** Klasa zarządzająca użyciem obrazów w projekcie */
public class ImageManager {
    private static final String path = "file:src/main/resources/images/";

    static HashMap<String,Image> images = new HashMap<>();

    /** Zwraca obraz o podanej nazwie i podanym rozmiarze.
     *  Gdy podany obraz nie został wcześniej wczytany, przed zwróceniem otworzy go i zapisze w mapie
     */
    public static Image getImage(String imageName,double sizeX,double sizeY) {
        if(!images.containsKey(imageName))
            images.put(imageName, new Image(path + imageName,sizeX,sizeY,true,false));
        return images.get(imageName);
    }
}
