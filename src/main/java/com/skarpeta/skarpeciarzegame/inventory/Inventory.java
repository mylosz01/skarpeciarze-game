package com.skarpeta.skarpeciarzegame.inventory;

import com.skarpeta.skarpeciarzegame.buildings.Mineshaft;
import com.skarpeta.skarpeciarzegame.buildings.Quarry;
import com.skarpeta.skarpeciarzegame.buildings.Sawmill;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;

import java.util.HashMap;

/** Klasa reprezentująca ekwipunek gracza
 *
 */

public class Inventory {

    public HashMap<String, Item> equipment;

    public Inventory(){
        this.equipment = new HashMap<>();
        this.equipment.put("Gold",new GoldItem(0));
        this.equipment.put("Stone",new StoneItem(0));
        this.equipment.put("Wood",new WoodItem(0));
        this.equipment.put("Boat",new BoatItem());
    }

    public boolean checkBuildStatus(BuildingType buildingType){

        switch (buildingType){
            case MINESHAFT -> {
                if (Mineshaft.itemCost.getAmount() <= equipment.get(Mineshaft.itemCost.getName()).getAmount()) {
                    decreaseItemAmount(Mineshaft.itemCost.getName(), Mineshaft.itemCost.getAmount());
                    return true;
                }
            }
            case QUARRY -> {
                if (Quarry.itemCost.getAmount() <= equipment.get(Quarry.itemCost.getName()).getAmount()) {
                    decreaseItemAmount(Quarry.itemCost.getName(), Quarry.itemCost.getAmount());
                    return true;
                }
            }
            case SAWMILL -> {
                if (Sawmill.itemCost.getAmount() <= equipment.get(Sawmill.itemCost.getName()).getAmount()) {
                    decreaseItemAmount(Sawmill.itemCost.getName(), Sawmill.itemCost.getAmount());
                    return true;
                }
            }
        }

        return false;
    }

    public CraftingStatus checkAmount(int goldAmount, int stoneAmount, int woodAmount){
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

    public HashMap<String, Item> getEquipment() {
        return equipment;
    }

    public void increaseItemAmount(String itemName, int amount){
        equipment.get(itemName).increaseAmount(amount);
    }

    public void decreaseItemAmount(String itemName, int amount){
        equipment.get(itemName).decreaseAmount(amount);
    }
}
