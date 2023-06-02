package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.BuildingType;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

public class Sawmill extends Building {
    public Sawmill() {
        super("sawmill");
        this.type= BuildingType.SAWMILL;
    }
}
