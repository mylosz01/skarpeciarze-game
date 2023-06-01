package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;
/** Building definiuje budynki budowane przez graczy */
public abstract class Building extends Asset {
    public Building(String textureName) {
        super(new ImageView(ImageManager.getImage(textureName+".png",128,128)));
        align();
    }
    /** align() służy budynkom do przyjęcia pozycji w prawym górnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-10,0.5);
    }
}
