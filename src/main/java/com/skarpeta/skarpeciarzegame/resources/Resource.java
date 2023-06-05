package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.image.ImageView;
/** Losowo generowane materiały do zebrania przez gracza */
public abstract class Resource extends Asset {

    public Item item;
    protected int maxResourceRange = 100;
    public ResourceType type;

    public Resource(String textureName) {
        super(new ImageView(ImageManager.getImage("mapasset/"+textureName+".png",128,128)));
        align();
    }

    public Item getItem(){
       return this.item;
    }

    /** align() służy materiałom do przyjęcia pozycji w lewym dolnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-16.2,16);
    }
}
