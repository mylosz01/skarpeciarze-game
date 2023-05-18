package com.skarpeta.skarpeciarzegame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Catana extends Application {
    public static final double WINDOW_SIZE = 600;
    public static final int BOARD_SIZE = 6;
    public static final double FIELD_WIDTH = WINDOW_SIZE/BOARD_SIZE;

    static Color paletteDefiningColor = Color.DARKOLIVEGREEN;

    static final ColorPalette palette= new ColorPalette(paletteDefiningColor);
    static final Color[] fieldColors = {palette.light,palette.primary};

    private final Group fieldGroup = new Group();

    static Map map = new Map(BOARD_SIZE);
    @Override
    public void start(Stage stage) {

        Pane root = new Pane();
        Pane game = new Pane();

        game.getChildren().addAll(fieldGroup);
        root.getChildren().addAll(game);
        game.setLayoutX(0);
        game.setLayoutY(0);

        renderFields();

        Scene scene = new Scene(root);

        stage.setTitle("catana");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setMaxWidth(stage.getWidth());

    }
    private void renderFields() {
        for(int y=0;y<BOARD_SIZE;y++)
        {
            for(int x=0;x<BOARD_SIZE;x++)
            {
                Field field = map.getField(new Point(x,y));
                fieldGroup.getChildren().add(field);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}