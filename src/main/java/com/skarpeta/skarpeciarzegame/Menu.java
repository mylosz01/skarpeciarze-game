package com.skarpeta.skarpeciarzegame;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
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

        ImageView logo = new ImageView(ImageManager.getImage("logoGame"));

        ImageView btnStartGame = new ImageView(ImageManager.getImage("btnStart"));

        btnStartGame.setOnMouseEntered(e -> {btnStartGame.setScaleX(1.3); btnStartGame.setScaleY(1.3);});
        btnStartGame.setOnMouseExited(e -> {btnStartGame.setScaleX(1); btnStartGame.setScaleY(1);});
        btnStartGame.setOnMouseClicked(e -> System.out.println("CLikcked!!"));

        ImageView btnJoinGame = new ImageView(ImageManager.getImage("btnJoin"));

        btnJoinGame.setOnMouseEntered(e -> {btnJoinGame.setScaleX(1.3); btnJoinGame.setScaleY(1.3);});
        btnJoinGame.setOnMouseExited(e -> {btnJoinGame.setScaleX(1); btnJoinGame.setScaleY(1);});
        btnJoinGame.setOnMouseClicked(e -> System.out.println("CLikcked!!"));

        ImageView btnExit = new ImageView(ImageManager.getImage("btnQuit"));

        btnExit.setOnMouseEntered(e -> {btnExit.setScaleX(1.3); btnExit.setScaleY(1.3);});
        btnExit.setOnMouseExited(e -> {btnExit.setScaleX(1); btnExit.setScaleY(1);});
        btnExit.setOnMouseClicked(e -> System.out.println("CLikcked!!"));

        layoutMenu.getChildren().addAll(logo,btnStartGame,btnJoinGame,btnExit);
        layoutMenu.setBackground(new Background(new BackgroundImage(new Image("file:src/main/resources/images/background1.jpg"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));

        menuScene = new Scene(layoutMenu);

        menuScene.setCursor(new ImageCursor(ImageManager.getImage("kursor")));

        menu.setScene(menuScene);
        menu.setMinWidth(700);
        menu.setMinHeight(500);

        menu.setMaxWidth(1000);
        menu.setMaxHeight(700);

        menu.setTitle("Sockware game Catan");
        menu.show();
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}
