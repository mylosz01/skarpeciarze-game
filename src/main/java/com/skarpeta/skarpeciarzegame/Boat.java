package com.skarpeta.skarpeciarzegame;

public class Boat extends Item{
    Boat(){
        super(0);
    }
    public boolean exists(){
        return getAmount() == 0;
    }
    @Override
    public void craftBoat(){
        if(exists()){
            return;
        }
        setAmount(1);
    }
    @Override
    public void decreaseAmount(int number){
        return;
    }

}
