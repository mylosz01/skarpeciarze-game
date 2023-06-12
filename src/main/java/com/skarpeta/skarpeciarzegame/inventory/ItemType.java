package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;

public enum ItemType implements Craftable {
    EMPTY(null,""),
    WOOD(null, " "),
    STONE(null, ""),
    GOLD(null, ""),
    BOAT(BoatItem.getCost(), "Used to swim through water once. Can hold only "+Inventory.MAX_BOAT_HOLD+"."),
    CATANA(CatanaItem.getCost(), "Victory!");
    private final ArrayList<Item> cost;
    final String description;

    ItemType(ArrayList<Item> cost, String description) {
        this.cost = cost;
        this.description = description;
    }
    @Override
    public ArrayList<Item> getCost(){
        return cost;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public String getDescription() {
        return description;
    }
}
