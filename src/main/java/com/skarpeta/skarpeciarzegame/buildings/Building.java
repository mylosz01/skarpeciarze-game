package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.Asset;
import javafx.scene.image.ImageView;
/** Building definiuje budynki budowane przez graczy */
public abstract class Building extends Asset {
    public Building(ImageView texture) {
        super(texture);
        align();
    }
    /** align() służy budynkom do przyjęcia pozycji w prawym górnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-10,0.5);
    }
}
