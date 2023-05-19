package com.skarpeta.skarpeciarzegame;

import java.util.HashMap;

public class Inventory {
    HashMap<String,Item> equipment;
    Inventory(){
        this.equipment = new HashMap<>();
        this.equipment.put("Gold",new Gold(0));
        this.equipment.put("Stone",new Stone(0));
        this.equipment.put("Wood",new Wood(0));
        this.equipment.put("Boat",new Boat());
    }
    public boolean craftBoat(int goldAmount, int stoneAmount, int woodAmount){
        if(equipment.get("Gold").getAmount()<goldAmount || equipment.get("Stone").getAmount()<stoneAmount || equipment.get("Wood").getAmount()<woodAmount){
            return false;
        }
        equipment.get("Gold").decreaseAmount(goldAmount);
        equipment.get("Stone").decreaseAmount(stoneAmount);
        equipment.get("Wood").decreaseAmount(woodAmount);

        equipment.get("Boat").craftBoat();
        return true;
    }
    public int getAmount(String itemName){
        return equipment.get(itemName).getAmount();
    }
}
