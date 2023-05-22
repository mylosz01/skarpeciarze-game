package com.skarpeta.skarpeciarzegame.inventory;

public abstract class Item {
    private Integer amount;
    Item(int amount){
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

    public void craftBoat() {
        return;
    }
    public boolean lesserThan(int amount){
        return this.amount<amount;
    }
    public String toString()
    {
        return Integer.toString(amount);
    }
}
