package com.skarpeta.skarpeciarzegame;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Menu extends Application {

    Scene menuScene;
    Scene gameScene;
    Stage window;

    @Override
    public void start(Stage menu) throws Exception {
        window = menu;

        VBox layoutMenu = new VBox();
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.setSpacing(20);

        Button btnStartGame = new Button("Start Game");
        Button btnJoinGame = new Button("Join Game");
        Button btnExit = new Button("Exit");

        layoutMenu.getChildren().addAll(btnStartGame,btnJoinGame,btnExit);

        menuScene = new Scene(layoutMenu);

        menu.setScene(menuScene);
        menu.setMinWidth(600);
        menu.setMinHeight(400);
        menu.setTitle("Sockware game Catan");
        menu.show();
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}
