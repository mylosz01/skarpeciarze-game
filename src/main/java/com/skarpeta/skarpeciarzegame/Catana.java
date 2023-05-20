package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final int BOARD_SIZE = 60;
    public static final int WINDOW_SIZE = 500;
    public static final double FIELD_WIDTH = 50;
    private double currentScale = 1.0;
    double initialPositionX=0;
    double initialPositionY=0;
    private static final double ZOOM_FACTOR = 1.1;

    static final ColorPalette palette= new ColorPalette(Color.valueOf("4BC87F"),Color.valueOf("6EAFF7"));

    static Map map = new Map(BOARD_SIZE);
    static StackPane root;
    @Override
    public void start(Stage stage) {

        root = new StackPane(map);
        Scene scene = new Scene(root);
        scene.setFill(palette.blue);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);
        stage.setTitle("catana");
        stage.setScene(scene);
        stage.setWidth(WINDOW_SIZE);
        stage.setHeight(WINDOW_SIZE);
        stage.show();

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


    private void handleScroll(ScrollEvent scrollEvent) {
        double scroll = scrollEvent.getDeltaY();
        if (scroll > 0)
            currentScale *= ZOOM_FACTOR;
        else if (scroll < 0)
            currentScale /= ZOOM_FACTOR;
        map.setScaleX(currentScale);
        map.setScaleY(currentScale);
    }

    public static void main(String[] args) {
        launch();
    }
}