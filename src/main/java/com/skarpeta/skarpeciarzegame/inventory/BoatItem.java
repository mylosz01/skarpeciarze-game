package com.skarpeta.skarpeciarzegame.inventory;

/** Item służący do pływania po wodzie.
 *  Craftowany przez gracza.
 */
public class BoatItem extends Item {
    BoatItem(){
        super(0);
    }
    @Override
    public boolean boatExists(){
        return getAmount() == 1;
    }
    @Override
    public void craftBoat(){
        if(boatExists()){
            return;
        }
        setAmount(1);
    }
    @Override
    public void decreaseAmount(int number){
        return;
    }

}
