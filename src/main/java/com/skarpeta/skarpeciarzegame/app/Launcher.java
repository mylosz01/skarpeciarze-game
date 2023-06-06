package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.network.Server;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;
import java.util.Random;

public class Launcher extends Application {

    Scene menuScene;
    Stage window;

    public static Cursor arrow = new ImageCursor(ImageManager.getImage("cursor.png", 16, 16), 6, 0);

    @Override
    public void start(Stage menu) {
        window = menu;
        VBox layoutMenu = new VBox();
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.setSpacing(40);

        Group logoPane = new Group();

        ImageView logo = new ImageView(ImageManager.getImage("logoGame.png", 128, 128));
        logoPane.getChildren().add(logo);

        MenuButton btnStartGame = new MenuButton("start");
        btnStartGame.setOnMouseClicked(e -> launchServer());

        MenuButton btnJoinGame = new MenuButton("join");
        btnJoinGame.setOnMouseClicked(e -> joinServer());

        MenuButton btnExit = new MenuButton("exit");
        btnExit.setOnMouseClicked(e -> quitGame());

        VBox btnLayout = new VBox();
        btnLayout.setSpacing(10);
        btnLayout.setAlignment(Pos.CENTER);
        btnLayout.getChildren().addAll(btnStartGame, btnJoinGame, btnExit);

        layoutMenu.getChildren().addAll(logoPane, btnLayout);
        layoutMenu.setBackground(new Background(new BackgroundImage(ImageManager.getImage("background_catana1.png", 1280, 1280), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, true))));

        menuScene = new Scene(layoutMenu);

        menuScene.setCursor(arrow);

        menu.setScene(menuScene);
        menu.setMinWidth(700);
        menu.setMinHeight(500);

        menu.setMaxWidth(1000);
        menu.setMaxHeight(700);

        menu.getIcons().add(ImageManager.getImage("logoGame.png", 128, 128));
        menu.setTitle("Sockware game Catan");
        menu.show();
    }

    private void launchServer() {
        ServerMenu serverMenu = new ServerMenu();
        serverMenu.show();
        serverMenu.getLaunchButton().setOnAction(event -> {
            try {
                int mapSize = Integer.parseInt(serverMenu.getMapSizeField().getText());
                int portNumber = Integer.parseInt(serverMenu.getPortField().getText());
                int seed = serverMenu.isSeedRandom() ? new Random().nextInt() : Integer.parseInt(serverMenu.getSeedField().getText());

                if(mapSize <= 0)
                    throw new IllegalArgumentException("Invalid map size.");

                System.out.println("Booting server on port " + portNumber+"...");
                Server server = new Server(portNumber,mapSize,seed);
                new Thread(server).start();
                Catana catana = new Catana("127.0.0.1", portNumber);
                catana.start(new Stage());
                catana.katana.setOnCloseRequest(e -> new ClosingGamePopup(server,Catana.clientThread).start(new Stage()));
                serverMenu.close();
                window.close();
            } catch (IllegalArgumentException e) {
                serverMenu.getExceptionLabel().textProperty().set("Incorrect input. " + e.getMessage());
            } catch (IOException e) {
                serverMenu.getExceptionLabel().textProperty().set("Could not start the server. " + e.getMessage());
            }
        });
    }

    private void quitGame() {
        window.close();
        Platform.exit();
    }

    private void joinServer() {
        JoinMenu joinMenu = new JoinMenu();
        joinMenu.getConnectButton().setOnAction(event -> {
            try {
                String ipAddress = joinMenu.getIpField().getText();
                int portNumber = Integer.parseInt(joinMenu.getPortField().getText());
                System.out.println("Connecting to " + ipAddress + " on port " + portNumber);
                new Catana(ipAddress, portNumber).start(new Stage());
                joinMenu.close();
                window.close();
            } catch (IllegalArgumentException e) {
                joinMenu.getExceptionLabel().textProperty().set("Incorrect input. "+e.getMessage());
            } catch (IOException e) {
                joinMenu.getExceptionLabel().textProperty().set("Could not connect to server. "+e.getMessage());
            }
        });
        joinMenu.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
