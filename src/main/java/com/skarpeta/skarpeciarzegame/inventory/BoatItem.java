package com.skarpeta.skarpeciarzegame.inventory;

/** Item służący do pływania po wodzie.
 *  Craftowany przez gracza.
 */
public class BoatItem extends Item {
    BoatItem(){
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
