package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/** Building definiuje budynki budowane przez graczy */
public abstract class Building extends Asset {
    public Point position;
    public BuildingType type;
    public Item producedItem;
    public static ArrayList<Item> buildingCost;
    public Building(String textureName,Point position) {
        super(new ImageView(ImageManager.getImage("mapasset/"+textureName+".png",128,128)));
        align();
        this.position = position;
    }
    /** align() służy budynkom do przyjęcia pozycji w prawym górnym rogu pola Field w którym się znajduje*/
    private void align() {
        super.align(-10,0.5);
    }
}
