package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.worldmap.Field;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import com.skarpeta.skarpeciarzegame.inventory.Inventory;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import static com.skarpeta.skarpeciarzegame.app.Catana.FIELD_WIDTH;

/** Gracz posiada pole Field w którym się znajduje
 */
public class Player extends Asset {

    private final Inventory inventory;
    int playerID;
    public Field playerField;

    Player(Field field, int playerID){
        super(new ImageView(ImageManager.getImage("player/player" +
                playerID % new File("src/main/resources/images/player").list().length+".png",128,128)));
        this.playerID = playerID;
        this.inventory = new Inventory();
        this.playerField = field;
        getTexture().setFitWidth(FIELD_WIDTH * 0.5);
        getTexture().setFitHeight(FIELD_WIDTH * 0.5);
        relocate(field.getLayoutX(),field.getLayoutY());
        moveTo(field);
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
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2),
                new KeyValue(layoutXProperty(), destination.getLayoutX() - getTexture().getFitWidth() * 0.5, Interpolator.EASE_OUT),
                new KeyValue(layoutYProperty(), destination.getLayoutY(), Interpolator.EASE_OUT)
        );
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
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
