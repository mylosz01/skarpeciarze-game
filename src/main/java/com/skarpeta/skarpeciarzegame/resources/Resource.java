package com.skarpeta.skarpeciarzegame.resources;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.image.ImageView;

import java.util.Random;

/** Losowo generowane materiały do zebrania przez gracza */
public abstract class Resource extends Asset {

    protected Item item;
    protected Integer[] rangeAmount = new Integer[]{10,20};
    ResourceType type;

    public Resource(ResourceType type, String textureName) {
        super(new ImageView(ImageManager.getImage("mapasset/"+textureName+".png",128,128)));
        this.type = type;
        align();
    }

    public Item getItem(){
       return this.item;
    }

    /** align() służy materiałom do przyjęcia pozycji w lewym dolnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-16.2,16);
    }


    public int generateAmount() {
        return new Random().nextInt(rangeAmount[1] - rangeAmount[0]) + rangeAmount[0];
    }

    public ResourceType getType() {
        return type;
    }
}
