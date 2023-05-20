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

    static final ColorPalette palette = new ColorPalette(
            new ColorShades("#28c074", "#10908E", "#48e094"), //green
            new ColorShades("#488BD4", "#3C67D3", "#68ABF4"), //blue
            new ColorShades("#928FB8", "#5B537D", "#B2AFD8"), //gray
            new ColorShades("#FFCF8E", "#E7B36F", "#FFE2A3")); //yellow

    static Map map = new Map(BOARD_SIZE);
    static StackPane root;
    private Stage game;

    @Override
    public void start(Stage game) {

        this.game = game;
        root = new StackPane(map);
        Scene scene = new Scene(root);
        scene.setFill(palette.blue.primary);
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
        double scroll = event.getDeltaY();
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