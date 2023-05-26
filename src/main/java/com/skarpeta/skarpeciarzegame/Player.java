package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.inventory.Inventory;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.image.ImageView;

import java.io.IOException;

/** Gracz posiada pole Field w którym się znajduje
 */
public class Player extends Asset {

    Inventory playerEq;
    WorldMap worldMap;
    public Field playerField;

    Player(WorldMap worldMap, Point point){
        super(new ImageView(ImageManager.getImage("player.png",32,32)));
        this.playerEq = new Inventory();
        this.worldMap = worldMap;
        this.playerField = worldMap.getField(point);
        align(playerField);
    }

    /** ustawianie pozycji gracza na ekranie, uzywajac pozycji pola */
    public void align(Field field) {
        relocate(field.getLayoutX(),field.getLayoutY());
        super.align(this.getWidth() * -0.5,0);
    }
    /** Poruszanie się gracza
     *  gracz porusza się na podane pole Field destination tylko w przypadku gdy ruch jest poprawny (isValidMovePlayer)
     */
    public void movePlayer(Field destination) throws InvalidMoveException, IOException {
        if(isValidMovePlayer(destination)){
            this.playerField = destination;
            Client.sendData();
            align(this.playerField);
        }
    }

    /** Walidacja ruchów gracza:
     *  niepoprawnym ruchem jest próba wejścia do wody bez łodzi
     *  oraz próba wejścia na niesąsiadujące pole
     */
    public boolean isValidMovePlayer(Field destination) throws InvalidMoveException {
        if(destination.terrain.equals(TerrainType.WATER))
            throw new InvalidMoveException("nie posiadasz lodki!!!");
        if(!destination.position.isTouchingHexagonal(this.playerField.position))
            throw new InvalidMoveException("to pole jest poza zasiegiem");
        return true;
    }
}
