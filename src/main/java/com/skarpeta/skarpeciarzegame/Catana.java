package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final int BOARD_SIZE = 50;
    public static final int WINDOW_SIZE = 500;
    public static final double FIELD_WIDTH = 50;
    private double currentScale = 1.0;
    double initialPositionX=0;
    double initialPositionY=0;
    private static final double ZOOM_FACTOR = 1.1;

    static WorldMap worldMap = new WorldMap(BOARD_SIZE);
    static StackPane gameMap;

    @Override
    public void start(Stage game) {

        gameMap = new StackPane(worldMap);
        gameMap.setOnScroll(this::handleScroll);
        gameMap.setOnMouseDragged(this::handleDrag);
        gameMap.setOnMousePressed(this::handleRightClick);

        VBox playerUIMain = new VBox();

        playerUIMain.setAlignment(Pos.CENTER);

        playerUIMain.setBorder(new Border(new BorderStroke(Color.DARKGOLDENROD,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        Rectangle ruchyGracza = new Rectangle(410,200,Color.LIGHTCORAL);
        Rectangle eqGracza = new Rectangle(200,200,Color.CHOCOLATE);
        Rectangle statyGracza = new Rectangle(200,200,Color.CHARTREUSE);

        HBox playerUIDown = new HBox();
        playerUIDown.getChildren().addAll(eqGracza,statyGracza);
        playerUIDown.setBorder(new Border(new BorderStroke(Color.GREENYELLOW,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        playerUIMain.getChildren().addAll(ruchyGracza,playerUIDown);

        AnchorPane gameLayout = new AnchorPane();
        gameLayout.getChildren().addAll(gameMap,playerUIMain);
        AnchorPane.setLeftAnchor(gameMap,0.0);
        AnchorPane.setTopAnchor(playerUIMain,0.0);
        AnchorPane.setRightAnchor(playerUIMain,0.0);
        AnchorPane.setBottomAnchor(playerUIMain,0.0);

        Scene scene = new Scene(gameLayout);
        scene.setFill(TerrainType.WATER.getColor().primary);
        game.setTitle("catana");
        game.setScene(scene);
        game.setWidth(1200);
        game.setHeight(800);
        game.show();
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
        currentScale *= zoomFactor;
        gameMap.setScaleX(currentScale);
        gameMap.setScaleY(currentScale);

        double offsetX = (event.getX() - gameMap.getWidth() / 2) * (1 - zoomFactor);
        double offsetY = (event.getY() - gameMap.getHeight() / 2) * (1 - zoomFactor);

        gameMap.setTranslateX((gameMap.getTranslateX() + offsetX) * zoomFactor);
        gameMap.setTranslateY((gameMap.getTranslateY() + offsetY) * zoomFactor);
    }

    public static void main(String[] args) {
        launch();
    }
}