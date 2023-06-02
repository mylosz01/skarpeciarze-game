package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.BuildingType;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

public class Quarry extends Building {
    public Quarry() {
        super("quarry");
        this.type= BuildingType.QUARRY;
    }
}
