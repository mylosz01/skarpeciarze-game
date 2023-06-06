package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;

public interface Craftable {
    default ArrayList<Item> getCost(){
        return null;
    }
    default String getDescription(){
        return null;
    }
}
