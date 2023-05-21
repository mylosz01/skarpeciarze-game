package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final int BOARD_SIZE = 60;
    public static final int WINDOW_SIZE = 500;
    public static final double FIELD_WIDTH = 50;
    private double currentScale = 1.0;
    double initialPositionX=0;
    double initialPositionY=0;
    private static final double ZOOM_FACTOR = 1.1;

    static Map map = new Map(BOARD_SIZE);
    static StackPane root;

    @Override
    public void start(Stage game) {
        
        root = new StackPane(map);
        Scene scene = new Scene(root);
        scene.setFill(TerrainType.WATER.getColor().primary);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);
        game.setTitle("catana");
        game.setScene(scene);
        game.setWidth(WINDOW_SIZE);
        game.setHeight(WINDOW_SIZE);
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

            root.setTranslateX(root.getTranslateX() + distanceX);
            root.setTranslateY(root.getTranslateY() + distanceY);

            initialPositionX = event.getX();
            initialPositionY = event.getY();
        }
    }


    private void handleScroll(ScrollEvent event) {
        double zoomFactor = (event.getDeltaY() > 0) ? ZOOM_FACTOR : (1 / ZOOM_FACTOR);
        currentScale *= zoomFactor;
        root.setScaleX(currentScale);
        root.setScaleY(currentScale);

        double offsetX = (event.getX() - root.getWidth() / 2) * (1 - zoomFactor);
        double offsetY = (event.getY() - root.getHeight() / 2) * (1 - zoomFactor);

        root.setTranslateX((root.getTranslateX() + offsetX) * zoomFactor);
        root.setTranslateY((root.getTranslateY() + offsetY) * zoomFactor);
    }

    public static void main(String[] args) {
        launch();
    }
}