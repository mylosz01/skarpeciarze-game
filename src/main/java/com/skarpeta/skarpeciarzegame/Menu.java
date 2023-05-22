package com.skarpeta.skarpeciarzegame;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
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
        layoutMenu.setSpacing(40);

        Group logoPane = new Group();

        ImageView logo = new ImageView(ImageManager.getImage("logoGame"));
        logoPane.getChildren().add(logo);

        MenuButton btnStartGame = new MenuButton("btnStart");

        MenuButton btnJoinGame = new MenuButton("btnJoin");

        MenuButton btnExit = new MenuButton("btnQuit");

        VBox btnLayout = new VBox();
        btnLayout.setSpacing(10);
        btnLayout.setAlignment(Pos.CENTER);
        btnLayout.getChildren().addAll(btnStartGame.getImageView(),btnJoinGame.getImageView(),btnExit.getImageView());

        layoutMenu.getChildren().addAll(logoPane,btnLayout);
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
