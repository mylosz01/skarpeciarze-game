package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.network.Client;
import com.skarpeta.skarpeciarzegame.network.Player;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import com.skarpeta.skarpeciarzegame.worldmap.WorldMap;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Catana extends Application {
    public static final double WINDOW_SIZE = 1000;
    public static final double FIELD_WIDTH = 50;
    public static final double UI_WIDTH = 300;
    private double currentScale = 1.0;
    double initialPositionX = 0;
    double initialPositionY = 0;
    private static final double ZOOM_FACTOR = 1.1;

    public static String ipAddress = "127.0.0.1";
    public static int portNumber = 5555;

    static WorldMap worldMap;
    public static Pane gameMap;
    public static Group playersGroup = new Group();
    public static Stage katana;

    public static PlayerUI playerUI;
    private static Client clientThread;
    Timeline timeline;

    public Catana(String ipAddress, int portNumber) {
        Catana.ipAddress = ipAddress;
        Catana.portNumber = portNumber;
    }

    public static void renderPlayer(Player player) {
        playersGroup.getChildren().add(player);
        player.scaleXProperty().bind(
                Bindings.when(Catana.gameMap.scaleXProperty().greaterThan(2))
                        .then(1)
                        .otherwise(Bindings.divide(2, Catana.gameMap.scaleXProperty()))
        );

        player.scaleYProperty().bind(
                Bindings.when(Catana.gameMap.scaleYProperty().greaterThan(2))
                        .then(1)
                        .otherwise(Bindings.divide(2, Catana.gameMap.scaleYProperty()))
        );
    }

    @Override
    public void start(Stage katana) throws IOException {
        this.katana = katana;
        clientThread = new Client();
        Thread playerSend = new Thread(clientThread);
        playerSend.start();
        worldMap = clientThread.getWorldMap();
        gameMap = new Pane(worldMap,playersGroup);
        playersGroup.setMouseTransparent(true);
        setupStage();
        panTo(clientThread.getPlayer(),0);
    }

    private void setupStage() {
        playerUI = new PlayerUI(); //okienko z ui itp po prawej
        Pane gamePane = createGamePane();//okienko gry po lewej
        MenuButton recenterButton = new MenuButton("center","centerHover",64,64);
        recenterButton.setOnMouseClicked(e -> panTo(clientThread.getPlayer(),1));

        AnchorPane.setBottomAnchor(recenterButton,0.0);
        AnchorPane.setRightAnchor(recenterButton,UI_WIDTH*1.1);

        AnchorPane gameLayout = new AnchorPane();
        gameLayout.getChildren().addAll(gamePane,playerUI,recenterButton);

        Scene scene = new Scene(gameLayout);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);

        katana.setScene(scene);
        katana.setWidth(WINDOW_SIZE);
        katana.setHeight(700);
        katana.show();
    }
    /** Porusza kamera aby na srodku ekranu byl dany obiekt*/
    private void panTo(Node node, double durationInSeconds) {
        if(timeline != null)
            timeline.stop();
        if(durationInSeconds<=0)
            durationInSeconds = 0.0001;
        currentScale = 1;
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(durationInSeconds),
                new KeyValue(gameMap.layoutXProperty(), -node.getLayoutX() + katana.getWidth() * 0.5 - UI_WIDTH * 0.5,Interpolator.EASE_BOTH),
                new KeyValue(gameMap.layoutYProperty(), -node.getLayoutY() + katana.getHeight() * 0.5,Interpolator.EASE_BOTH),
                new KeyValue(gameMap.scaleXProperty(), currentScale,Interpolator.EASE_BOTH),
                new KeyValue(gameMap.scaleYProperty(), currentScale,Interpolator.EASE_BOTH)
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(e->timeline = null);
        timeline.play();
    }

    private Pane createGamePane() { //lewy panel okna (gra)
        Pane gamePane = new Pane();
        gamePane.setBackground(new Background(new BackgroundFill(TerrainType.WATER.getColor().primary,CornerRadii.EMPTY, Insets.EMPTY)));

        AnchorPane.setTopAnchor(gamePane,0.0);
        AnchorPane.setLeftAnchor(gamePane,0.0);
        AnchorPane.setBottomAnchor(gamePane,0.0);
        AnchorPane.setRightAnchor(gamePane,UI_WIDTH);

        gamePane.getChildren().add(gameMap);
        return gamePane;
    }

    private void handleRightClick(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            initialPositionX = event.getX();
            initialPositionY = event.getY();
        }
    }

    private void handleDrag(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            if(timeline != null)
                timeline.stop();

            double distanceX = event.getX() - initialPositionX;
            double distanceY = event.getY() - initialPositionY;

            gameMap.setLayoutX(gameMap.getLayoutX() + distanceX);
            gameMap.setLayoutY(gameMap.getLayoutY() + distanceY);

            initialPositionX = event.getX();
            initialPositionY = event.getY();
        }
    }

    private void handleScroll(ScrollEvent event) {
        double zoomFactor = (event.getDeltaY() > 0) ? ZOOM_FACTOR : (1 / ZOOM_FACTOR);
        double offsetX = (event.getX() - gameMap.getWidth() / 2) * (1 - zoomFactor);
        double offsetY = (event.getY() - gameMap.getHeight() / 2) * (1 - zoomFactor);

        gameMap.setScaleX(currentScale*zoomFactor);
        gameMap.setScaleY(currentScale*zoomFactor);
        gameMap.setLayoutX((gameMap.getLayoutX() + offsetX) * zoomFactor);
        gameMap.setLayoutY((gameMap.getLayoutY() + offsetY) * zoomFactor);
        currentScale *= zoomFactor;
    }

    public static Client getClientThread() {
        return clientThread;
    }

    public static void main(String[] args) {
        Platform.runLater(()-> {
            try {
                new Catana(ipAddress,portNumber).start(new Stage());
            } catch (IOException e) {
                System.out.println("Could not connect to "+ipAddress+":"+portNumber);
            }
        });
    }
}