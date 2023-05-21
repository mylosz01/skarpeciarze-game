package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

public class ForestResource extends Resource {
    public ForestResource() {
        super(new ImageView(ImageManager.getImagePNG("forest")));
    }
}
