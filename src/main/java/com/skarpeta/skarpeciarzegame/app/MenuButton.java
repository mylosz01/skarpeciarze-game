package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.*;


/** Przycisk z wybraną grafiką */
public class MenuButton extends Asset {
    ImageView hover_texture = new ImageView(ImageManager.getImage("button/hover.png",128,128));
    /** Tworzenie przycisku z grafiką o nazwie pliku textureName (wraz z rozszerzeniem)*/
    public MenuButton(String textureName){
        super(new ImageView(ImageManager.getImage("button/"+textureName+".png",128,128)));
        setOnMouseEntered(e -> getChildren().add(hover_texture));
        setOnMouseExited(e -> getChildren().remove(hover_texture));
    }
}
