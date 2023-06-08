package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** Klasa reprezentująca ekwipunek gracza
 *
 */

public class Inventory {

    public static final int MAX_BOAT_HOLD = 1;
    public LinkedHashMap<ItemType, Item> equipment;

    public Inventory(){
        this.equipment = new LinkedHashMap <>();
        this.equipment.put(ItemType.GOLD,new GoldItem(0));
        this.equipment.put(ItemType.STONE,new StoneItem(0));
        this.equipment.put(ItemType.WOOD,new WoodItem(0));
        this.equipment.put(ItemType.BOAT,new BoatItem(0));
        this.equipment.put(ItemType.CATANA,new CatanaItem(0));
    }

    public boolean hasEnoughMaterials(ArrayList<Item> cost) {
        for (Item item : cost) {
            if (item.getAmount() > equipment.get(item.type).getAmount())
                return false;
        }
        return true;
    }

    public void craft(ItemType type){
        if(hasEnoughMaterials(type.getCost())){
            if(type == ItemType.BOAT  && getAmount(type) < MAX_BOAT_HOLD)
                return;
            equipment.get(type).increaseAmount(1);
            decrease(type.getCost());
        }
    }

    public int getAmount(ItemType type){
        return equipment.get(type).getAmount();
    }


    public HashMap<ItemType, Item> getEquipment() {
        return equipment;
    }

    public void increaseItemAmount(ItemType type, int amount){
        equipment.get(type).increaseAmount(amount);
    }

    public void decreaseItemAmount(ItemType type, int amount){
        equipment.get(type).decreaseAmount(amount);
    }

    public void decrease(ArrayList<Item> cost) {
        for (Item item : cost) {
            decreaseItemAmount(item.type,item.getAmount());
        }
    }


}
