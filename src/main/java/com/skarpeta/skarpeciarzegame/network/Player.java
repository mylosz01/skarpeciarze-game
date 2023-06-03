package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.worldmap.Field;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import com.skarpeta.skarpeciarzegame.inventory.Inventory;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import javafx.scene.image.ImageView;

import java.io.IOException;

/** Gracz posiada pole Field w którym się znajduje
 */
public class Player extends Asset {

    private final Inventory inventory;
    int playerID;
    public Field playerField;

    Player(Field field, int playerID){
        super(new ImageView(ImageManager.getImage("player"+playerID % 5+".png",32,32)));
        this.playerID = playerID;
        this.inventory = new Inventory();
        this.playerField = field;
        align(playerField);
    }

    /** ustawianie pozycji gracza na ekranie, uzywajac pozycji pola */
    public void align(Field field) {
        relocate(field.getLayoutX(),field.getLayoutY());
        super.align(this.getTexture().getFitWidth() * -0.5,0);
    }
    /** Poruszanie się gracza
     *  gracz porusza się na podane pole Field destination tylko w przypadku gdy ruch jest poprawny (isValidMovePlayer)
     */
    public void sendMove(Field destination) throws InvalidMoveException, IOException {
        if(isValidMovePlayer(destination)){
            Client.makeMove(playerID,destination.position);
        }
    }

    public void collectResource(){

        if(playerField.hasResource()){
           int itemAmount =  playerField.resource.getItem().getAmount();
           System.out.println("Zebrano: " + playerField.resource.item.getName() + " " + itemAmount);

           inventory.increaseItemAmount(playerField.resource.item.getName(),itemAmount);
           this.playerField.destroyResource();
        }
    }

    public void moveTo(Field destination){
        this.playerField = destination;
        align(this.playerField);
    }
    /** Walidacja ruchów gracza:
     *  niepoprawnym ruchem jest próba wejścia do wody bez łodzi
     *  oraz próba wejścia na niesąsiadujące pole
     */
    public boolean isValidMovePlayer(Field destination) throws InvalidMoveException {
        if(destination.terrain.equals(TerrainType.WATER) && inventory.getAmount("Boat") == 0)
            throw new InvalidMoveException("nie posiadasz lodki!!!");
        if(!destination.position.isTouchingHexagonal(this.playerField.position))
            throw new InvalidMoveException("to pole jest poza zasiegiem");
        return true;
    }

    public Inventory getInventory() {
        return inventory;
    }
}