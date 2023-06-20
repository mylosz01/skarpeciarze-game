package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/** Building definiuje budynki budowane przez graczy */
public abstract class Building extends Asset {
    private final BuildingType type;
    public Item producedItem;
    public static ArrayList<Item> buildingCost;
    public Building(BuildingType type) {
        super(new ImageView(ImageManager.getImage("mapasset/"+type.name().toLowerCase()+".png",128,128)));
        this.type = type;
        align();
    }
    /** align() służy budynkom do przyjęcia pozycji w prawym górnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-10,0.5);
    }

    @Override
    public String toString() {
        return type.name();
    }

    public BuildingType getType() {
        return type;
    }
}
