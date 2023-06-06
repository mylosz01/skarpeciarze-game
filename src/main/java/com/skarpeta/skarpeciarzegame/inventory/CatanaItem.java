package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;

/** Item służący do pływania po wodzie.
 *  Craftowany przez gracza.
 */
public class CatanaItem extends Item{
    public static ArrayList<Item> buildingCost = new ArrayList<>();
    static{
        buildingCost.add(new GoldItem(500));
    }
    CatanaItem(int amount){
        super(amount,"catanaItem","Catana");
        this.type = ItemType.CATANA;
    }

    public static ArrayList<Item> getCost() {
        return buildingCost;
    }
}
