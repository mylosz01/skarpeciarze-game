package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;

public enum ItemType {
    EMPTY(null),
    WOOD(null),
    STONE(null),
    GOLD(null),
    BOAT(BoatItem.getCost()),
    CATANA(CatanaItem.getCost());
    private final ArrayList<Item> cost;

    ItemType(ArrayList<Item> cost) {
        this.cost = cost;
    }
    public ArrayList<Item> getCost(){
        return cost;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
