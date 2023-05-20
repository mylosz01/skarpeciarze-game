package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final int BOARD_SIZE = 60;
    public static final double FIELD_WIDTH = 1000.0/60;

    static final ColorPalette palette= new ColorPalette(Color.valueOf("4BC87F"),Color.valueOf("6EAFF7"));

    static Map map = new Map(BOARD_SIZE);
    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane(map);
        Scene scene = new Scene(root);
        scene.setFill(palette.blue);
        stage.setTitle("catana");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}