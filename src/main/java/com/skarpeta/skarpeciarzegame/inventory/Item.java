package com.skarpeta.skarpeciarzegame.inventory;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.image.ImageView;

/** Przedmioty przechowywane przez gracza w ekwipunku
 *  (wraz z jego ilością)
 * */
public abstract class Item extends Asset {
    private Integer amount;
    Item(int amount, String textureName){
        super(new ImageView(ImageManager.getImage(textureName+".png",32,32)));
        this.amount = amount;
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

    public void craftBoat(){
        return;
    }

    public boolean lesserThan(int amount){
        return this.amount<amount;
    }

    public String toString(){
        return Integer.toString(amount);
    }

    public boolean boatExists(){
        return false;
    }
}
