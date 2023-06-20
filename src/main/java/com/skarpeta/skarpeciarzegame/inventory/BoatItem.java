package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;

/** Item służący do pływania po wodzie.
 *  Craftowany przez gracza.
 */
public class BoatItem extends Item{
    private static final ArrayList<Item> buildingCost = new ArrayList<>();
    static{ buildingCost.add(new WoodItem(5)); }

    BoatItem(int amount){
        super(amount,ItemType.BOAT);
    }

    public static ArrayList<Item> getCost() {
        return buildingCost;
    }
}
