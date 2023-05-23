package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.inventory.Inventory;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.scene.image.ImageView;

public class Player extends Asset {

    Inventory playerEq;
    WorldMap worldMap;
    Field playerField;

    Player(WorldMap worldMap, Point point){
        super(new ImageView(ImageManager.getImage("player.png",32,32)));
        this.playerEq = new Inventory();
        this.worldMap = worldMap;
        this.playerField = worldMap.getField(point);
    }

    public void allignTo(Field field) {
        double x = field.position.x * field.hexagon.width * 0.75;
        double y = field.position.y * field.hexagon.height;
        if(field.position.x%2 == 1)
            y += field.hexagon.height * 0.5;
        super.allignTo(field,x- field.hexagon.width * 0.25,y);
    }

    public void movePlayer(Field destination) throws InvalidMoveException {
        if(isValidMovePlayer(destination)){
            this.playerField = destination;
            allignTo(this.playerField);
        }
    }

    public boolean isValidMovePlayer(Field destination) throws InvalidMoveException {
        if(destination.terrain.equals(TerrainType.WATER))
            throw new InvalidMoveException("nie posiadasz lodki!!!");
        if(!destination.position.isTouchingHexagonal(this.playerField.position))
            throw new InvalidMoveException("to pole jest poza zasiegiem");
        return true;
    }
}
