package com.skarpeta.skarpeciarzegame.inventory;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

/** Przedmioty przechowywane przez gracza w ekwipunku
 *  (wraz z jego ilością)
 * */
public abstract class Item extends Asset {
    private Integer amount;
    private String name;

    Item(int amount, String textureName, String name){
        super(new ImageView(ImageManager.getImage("item/"+textureName+".png",64,64)));
        this.amount = amount;
        this.name = name;
    }

    public String getName(){
        return this.name;
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

    public boolean lesserThan(int amount){
        return this.amount<amount;
    }

    public String toString(){
        return getName() + " - " + amount;
    }
}
