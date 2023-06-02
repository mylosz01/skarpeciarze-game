package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.BuildingType;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

public class Mineshaft extends Building {
    public Mineshaft() {
        super("mineshaft");
        this.type= BuildingType.MINESHAFT;
    }
}
