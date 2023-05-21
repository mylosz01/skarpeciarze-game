package com.skarpeta.skarpeciarzegame;

import java.util.HashMap;

public class Inventory {
    HashMap<String,Item> equipment;
    Inventory(){
        this.equipment = new HashMap<>();
        this.equipment.put("Gold",new GoldItem(0));
        this.equipment.put("Stone",new StoneItem(0));
        this.equipment.put("Wood",new WoodItem(0));
        this.equipment.put("Boat",new BoatItem());
    }
    private CraftingStatus checkAmount(int goldAmount, int stoneAmount, int woodAmount){
        if(equipment.get("Gold").lesserThan(goldAmount)){
            return CraftingStatus.NOT_ENOUGH_GOLD;
        }
        if(equipment.get("Stone").lesserThan(stoneAmount)){
            return CraftingStatus.NOT_ENOUGH_STONE;
        }
        if(equipment.get("Wood").lesserThan(woodAmount)){
            return CraftingStatus.NOT_ENOUGH_WOOD;
        }
        equipment.get("Gold").decreaseAmount(goldAmount);
        equipment.get("Stone").decreaseAmount(stoneAmount);
        equipment.get("Wood").decreaseAmount(woodAmount);
        return CraftingStatus.DONE;
    }
    public CraftingStatus craftBoat(int goldAmount, int stoneAmount, int woodAmount){
        CraftingStatus craftingStatus = checkAmount(goldAmount,stoneAmount,woodAmount);
        if(craftingStatus!=CraftingStatus.DONE){
            return craftingStatus;
        }
        equipment.get("Boat").craftBoat();
        return CraftingStatus.DONE;
    }
    public int getAmount(String itemName){
        return equipment.get(itemName).getAmount();
    }
    public CraftingStatus build(int goldAmount, int stoneAmount, int woodAmount){
        return checkAmount(goldAmount,stoneAmount,woodAmount);
    }
}
