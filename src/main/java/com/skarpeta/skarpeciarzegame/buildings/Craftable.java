package com.skarpeta.skarpeciarzegame.buildings;

import com.skarpeta.skarpeciarzegame.inventory.Item;

import java.util.ArrayList;

public interface Craftable {
    default ArrayList<Item> getCost(){
        return null;
    }
    default String getDescription(){
        return null;
    }
}
