package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.Field;
import javafx.scene.image.ImageView;

public abstract class Resource extends Asset {
    public Resource(ImageView texture) {
        super(texture);
    }
    public void allignTo(Field field) {
        super.allignTo(field,-16.2,16);
    }
}
