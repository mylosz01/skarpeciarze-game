package com.skarpeta.skarpeciarzegame.network;

import com.skarpeta.skarpeciarzegame.inventory.ItemType;
import com.skarpeta.skarpeciarzegame.resources.Resource;
import com.skarpeta.skarpeciarzegame.tools.FontManager;
import com.skarpeta.skarpeciarzegame.worldmap.Field;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import com.skarpeta.skarpeciarzegame.inventory.Inventory;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import static com.skarpeta.skarpeciarzegame.app.Catana.FIELD_WIDTH;

/** Gracz posiada pole Field w którym się znajduje
 */
public class Player extends VBox {

    private static double ANIMATION_SPEED = 0.2;

    private final StackPane playerSprite = new StackPane();
    private final ImageView texture;
    private final ImageView boat;
    private final Inventory inventory;
    int playerID;
    public Field playerField;
    private boolean isOnBoat = false;
    private String nickname = "";

    Player(Field field, int playerID, String nickname){
        super();
        this.texture = new ImageView(ImageManager.getImage("player/player" +
                playerID % new File("src/main/resources/images/player").list().length+".png",128,128));
        boat = new ImageView(ImageManager.getImage("boatEntity.png",128,128));
        boat.setFitWidth(FIELD_WIDTH * 0.5);
        boat.setFitHeight(FIELD_WIDTH * 0.5);
        this.playerID = playerID;
        this.inventory = new Inventory();
        this.playerField = field;
        texture.setFitWidth(FIELD_WIDTH * 0.5);
        texture.setFitHeight(FIELD_WIDTH * 0.5);
        playerSprite.getChildren().add(texture);
        getChildren().add(playerSprite);
        setNickname(nickname);
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
           Resource resource = playerField.resource;
           inventory.increaseItemAmount(resource.getItem().getType(),resource.getItem().getAmount());
           this.playerField.destroyResource();
        }
    }

    public void moveTo(Field destination){
        this.playerField = destination;
        double width = nickname.length() * 5.8; //getWidth() replacement
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(ANIMATION_SPEED),
                new KeyValue(translateXProperty(), destination.getLayoutX() - width, Interpolator.EASE_OUT),
                new KeyValue(translateYProperty(), destination.getLayoutY(), Interpolator.EASE_OUT)
        );
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(e-> System.out.println("koniec"));
        timeline.play();
    }
    /** Walidacja ruchów gracza:
     *  niepoprawnym ruchem jest próba wejścia do wody bez łodzi
     *  oraz próba wejścia na niesąsiadujące pole
     */
    public boolean isValidMovePlayer(Field destination) throws InvalidMoveException {
        if(destination.terrain.equals(TerrainType.WATER) && inventory.getAmount(ItemType.BOAT) == 0)
            throw new InvalidMoveException("nie posiadasz lodki!!!");
        if(!destination.position.isTouchingHexagonal(this.playerField.position))
            throw new InvalidMoveException("to pole jest poza zasiegiem");
        return true;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isOnBoat() {
        return isOnBoat;
    }

    public void getOnBoat() {
        if(!isOnBoat()){
            isOnBoat = true;
            Platform.runLater(()->playerSprite.getChildren().add(boat));
        }
    }

    public void getOffBoat() {
        if(isOnBoat()) {
            isOnBoat = false;
            Platform.runLater(() -> playerSprite.getChildren().remove(boat));
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        Platform.runLater(() -> {
            Label nickLabel = new Label(nickname);
            nickLabel.setFont(FontManager.getSmallFont());

            nickLabel.setTextFill(Color.WHITE);
            nickLabel.setStyle("-fx-background-color: rgba(64, 64, 64, 0.3);");
            nickLabel.setMaxWidth(Double.MAX_VALUE);
            setAlignment(Pos.CENTER);
            getChildren().add(nickLabel);
            nickLabel.setLayoutY(FIELD_WIDTH * 0.45);
        });
    }

    public ImageView getTexture() {
        return  texture;
    }
}
