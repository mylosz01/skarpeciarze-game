package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.*;


/** Przycisk z wybraną grafiką */
public class MenuButton extends Asset{
    ImageView hover_texture = new ImageView(ImageManager.getImage("hover.png",128,128));
    /** Tworzenie przycisku z grafiką o nazwie pliku imageName (wraz z rozszerzeniem)*/
    MenuButton(String imageName){
        super(new ImageView(ImageManager.getImage(imageName,128,128)));
        setOnMouseEntered(e -> getChildren().add(hover_texture));
        setOnMouseExited(e -> getChildren().remove(hover_texture));
    }

    /** Zwracanie grafiki przycisku jako ImageView */

}
