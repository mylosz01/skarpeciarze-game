package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.Asset;
import javafx.scene.image.ImageView;
/** Losowo generowane materiały do zebrania przez gracza */
public abstract class Resource extends Asset {
    public Resource(ImageView texture) {
        super(texture);
        align();
    }
    /** align() służy materiałom do przyjęcia pozycji w lewym dolnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-16.2,16);
    }
}
