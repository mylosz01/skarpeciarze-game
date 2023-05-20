package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final int BOARD_SIZE = 60;
    public static final double FIELD_WIDTH = 50;
    private double currentScale = 1.0;
    private static final double ZOOM_FACTOR = 1.1;

    static final ColorPalette palette= new ColorPalette(Color.valueOf("4BC87F"),Color.valueOf("6EAFF7"));

    static Map map = new Map(BOARD_SIZE);
    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane(map);
        Scene scene = new Scene(root);
        scene.setFill(palette.blue);
        scene.setOnScroll(this::handleScroll);
        stage.setTitle("catana");
        stage.setScene(scene);
        stage.show();

    }

    private void handleScroll(ScrollEvent scrollEvent) {
        double delta = scrollEvent.getDeltaY();

        if (delta > 0) {
            // Zoom in
            map.setScaleX(currentScale * ZOOM_FACTOR);
            map.setScaleY(currentScale * ZOOM_FACTOR);
            currentScale *= ZOOM_FACTOR;
        } else if (delta < 0) {
            // Zoom out
            map.setScaleX(currentScale / ZOOM_FACTOR);
            map.setScaleY(currentScale / ZOOM_FACTOR);
            currentScale /= ZOOM_FACTOR;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}