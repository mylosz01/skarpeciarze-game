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

public class Launcher extends Application {

    Scene menuScene;
    Stage window;

    @Override
    public void start(Stage menu) {
        window = menu;
        VBox layoutMenu = new VBox();
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.setSpacing(40);

        Group logoPane = new Group();

        ImageView logo = new ImageView(ImageManager.getImage("logoGame.png", 128, 128));
        logoPane.getChildren().add(logo);

        MenuButton btnStartGame = new MenuButton("start.png");
        btnStartGame.setOnMouseClicked(e -> launchServer());

        MenuButton btnJoinGame = new MenuButton("join.png");
        btnJoinGame.setOnMouseClicked(e -> joinServer());

        MenuButton btnExit = new MenuButton("exit.png");
        btnExit.setOnMouseClicked(e -> quitGame());

        VBox btnLayout = new VBox();
        btnLayout.setSpacing(10);
        btnLayout.setAlignment(Pos.CENTER);
        btnLayout.getChildren().addAll(btnStartGame, btnJoinGame, btnExit);

        layoutMenu.getChildren().addAll(logoPane, btnLayout);
        layoutMenu.setBackground(new Background(new BackgroundImage(ImageManager.getImage("background_catana1.png", 1280, 1280), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, true))));

        menuScene = new Scene(layoutMenu);

        menuScene.setCursor(new ImageCursor(ImageManager.getImage("cursor.png", 16, 16), 6, 0));

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
        /*int port = 5555;
        Server server = new Server(port);
        new Thread(server).start();
        Catana catana = new Catana("127.0.0.1",port);
        catana.start(new Stage());
        catana.katana.setOnCloseRequest(e->server.stop());
        window.close();
*/

        ServerMenu serverMenu = new ServerMenu();
        serverMenu.getLaunchButton().setOnAction(e -> {
            try {
                int mapSize = Integer.parseInt(serverMenu.getMapSizeField().getText());
                int portNumber = Integer.parseInt(serverMenu.getPortField().getText());
                System.out.println("Booting server on port " + portNumber+"...");
                new Thread(new Server(portNumber,mapSize,500)).start();
                new Catana("127.0.0.1", portNumber).start(new Stage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            serverMenu.close();
            window.close();
        });
        serverMenu.show();
    }

    private void quitGame() {
        window.close();
        Platform.exit();
    }

    private void joinServer() {
        JoinMenu joinMenu = new JoinMenu();
        joinMenu.getConnectButton().setOnAction(e -> {
            String ipAddress = joinMenu.getIpField().getText();
            int portNumber = Integer.parseInt(joinMenu.getPortField().getText());
            System.out.println("Connecting to " + ipAddress + " on port " + portNumber);
            new Catana(ipAddress, portNumber).start(new Stage());
            joinMenu.close();
            window.close();
        });
        joinMenu.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
