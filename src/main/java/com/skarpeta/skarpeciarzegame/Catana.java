package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final double WINDOW_SIZE = 600;
    public static final int BOARD_SIZE = 6;
    public static final double FIELD_WIDTH = WINDOW_SIZE/BOARD_SIZE;

    static final ColorPalette palette= new ColorPalette(Color.OLIVEDRAB,Color.DARKCYAN);

    static Map map = new Map(BOARD_SIZE);
    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane(map);
        Scene scene = new Scene(root);
        stage.setTitle("catana");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}