package com.skarpeta.skarpeciarzegame.inventory;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

/** Przedmioty przechowywane przez gracza w ekwipunku
 *  (wraz z jego ilością)
 * */
public abstract class Item extends Asset {
    private Integer amount;
    ItemType type;

    Item(int amount, ItemType type){
        super(new ImageView(ImageManager.getImage("item/"+type.toString()+".png",64,64)));
        this.amount = amount;
        this.type = type;
    }

    public String getName(){
        return this.type.toString();
    }

    public void setAmount(int newAmount){
        if(newAmount<0){
            newAmount = 0;
        }
        this.amount = newAmount;
    }

    public Integer getAmount(){
        return this.amount;
    }

    public void decreaseAmount(int number){
        setAmount(getAmount()-number);
    }

    public void increaseAmount(int number){
        decreaseAmount(-number);
    }

    public String toString(){
        return getName() + " - " + amount;
    }

    public ItemType getType() {
        return type;
    }
}
