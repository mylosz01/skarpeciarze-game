package com.skarpeta.skarpeciarzegame.inventory;

public class StoneItem extends Item {
    public StoneItem(int amount){
        super(amount,"stoneItem","Stone");
        this.type = ItemType.STONE;
    }
}
