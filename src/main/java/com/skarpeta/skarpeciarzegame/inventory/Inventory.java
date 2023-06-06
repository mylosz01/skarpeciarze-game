package com.skarpeta.skarpeciarzegame.inventory;

import java.util.ArrayList;
import java.util.HashMap;

/** Klasa reprezentująca ekwipunek gracza
 *
 */

public class Inventory {

    public static final int MAX_BOAT_HOLD = 1;
    public HashMap<String, Item> equipment;

    public Inventory(){
        this.equipment = new HashMap<>();
        this.equipment.put("Gold",new GoldItem(0));
        this.equipment.put("Stone",new StoneItem(0));
        this.equipment.put("Wood",new WoodItem(0));
        this.equipment.put("Boat",new BoatItem(0));
        this.equipment.put("Catana",new CatanaItem(0));
    }

    public boolean hasEnoughMaterials(ArrayList<Item> cost) {
        for (Item item : cost) {
            if (item.getAmount() > equipment.get(item.getName()).getAmount())
                return false;
        }
        return true;
    }

    public void craftBoat(){
        if(hasEnoughMaterials(BoatItem.getCost()) && getAmount("Boat") < MAX_BOAT_HOLD){
            equipment.get("Boat").increaseAmount(1);
            decrease(BoatItem.getCost());
        }
    }
    public void craftCatana() {
        if(hasEnoughMaterials(CatanaItem.getCost()) && getAmount("Catana") < MAX_BOAT_HOLD){
            equipment.get("Catana").increaseAmount(1);
            decrease(CatanaItem.getCost());
        }
    }

    public int getAmount(String itemName){
        return equipment.get(itemName).getAmount();
    }


    public HashMap<String, Item> getEquipment() {
        return equipment;
    }

    public void increaseItemAmount(String itemName, int amount){
        equipment.get(itemName).increaseAmount(amount);
    }

    public void decreaseItemAmount(String itemName, int amount){
        equipment.get(itemName).decreaseAmount(amount);
    }

    public void decrease(ArrayList<Item> cost) {
        for (Item item : cost) {
            decreaseItemAmount(item.getName(),item.getAmount());
        }
    }


}
