package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;

/** Item służący do pływania po wodzie.
 *  Craftowany przez gracza.
 */
public class CatanaItem extends Item{
    private static final ArrayList<Item> buildingCost = new ArrayList<>();
    static{ buildingCost.add(new GoldItem(500)); }

    CatanaItem(int amount){
        super(amount,ItemType.CATANA);
    }

    public static ArrayList<Item> getCost() {
        return buildingCost;
    }
}
