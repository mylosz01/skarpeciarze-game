package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.network.Client;
import com.skarpeta.skarpeciarzegame.network.Player;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import com.skarpeta.skarpeciarzegame.worldmap.WorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        setupStage();
    }

    private void setupStage() {
        playerUI = new PlayerUI(); //okienko z ui itp po prawej
        Pane gamePane = createGamePane();//okienko gry po lewej

        AnchorPane gameLayout = new AnchorPane();
        gameLayout.getChildren().addAll(gamePane,playerUI);

        Scene scene = new Scene(gameLayout);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);

        katana.setScene(scene);
        katana.setWidth(WINDOW_SIZE);
        katana.setHeight(700);
        katana.show();
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
            double distanceX = event.getX() - initialPositionX;
            double distanceY = event.getY() - initialPositionY;

            gameMap.setTranslateX(gameMap.getTranslateX() + distanceX);
            gameMap.setTranslateY(gameMap.getTranslateY() + distanceY);

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
        gameMap.setTranslateX((gameMap.getTranslateX() + offsetX) * zoomFactor);
        gameMap.setTranslateY((gameMap.getTranslateY() + offsetY) * zoomFactor);
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