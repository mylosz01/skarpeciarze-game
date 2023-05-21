package com.skarpeta.skarpeciarzegame;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

import java.io.InputStream;

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

        Label logo = new Label("Katan");
        logo.setStyle("-fx-font: 16px \"Serif\";");

        Button btnStartGame = new Button("START GAME");
        btnStartGame.setStyle("-fx-font: 16px \"Serif\";");

        Button btnJoinGame = new Button("JOIN GAME");
        btnJoinGame.setStyle("-fx-font: 16px \"Serif\";");

        Button btnExit = new Button("EXIT");
        btnExit.setStyle("-fx-font: 16px \"Serif\";");

        layoutMenu.getChildren().addAll(logo,btnStartGame,btnJoinGame,btnExit);
        //layoutMenu.setBackground(new Background(new BackgroundImage(new Image("D:\\Java\\skarpeciarze-game\\assets\\background2.jpg"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));

        menuScene = new Scene(layoutMenu);

        InputStream imageSrc = this.getClass().getResourceAsStream("/assets/background1.jpg");
        System.out.println(imageSrc);
        //menuScene.setCursor(new ImageCursor(new Image("D:\\Java\\skarpeciarze-game\\src\\main\\assets\\kursor.png")));

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
