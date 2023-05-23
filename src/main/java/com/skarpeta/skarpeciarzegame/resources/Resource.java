package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.Asset;
import javafx.scene.image.ImageView;

public abstract class Resource extends Asset {
    public Resource(ImageView texture) {
        super(texture);
        align();
    }
    private void align() {
        super.allignTo(-16.2,16);
    }
}
