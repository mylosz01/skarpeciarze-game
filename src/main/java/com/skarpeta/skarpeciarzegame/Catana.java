package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.tools.PlayerManager;
import com.skarpeta.skarpeciarzegame.tools.Point;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Map;

public class Catana extends Application {
    public static final int BOARD_SIZE = 50;
    public static final double WINDOW_SIZE = 1000;
    public static final double FIELD_WIDTH = 50;
    public static final double UI_WIDTH = 300;
    private double currentScale = 1.0;
    double initialPositionX = 0;
    double initialPositionY = 0;
    private static final double ZOOM_FACTOR = 1.1;

    Border insideBorder = new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent,BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5)));

    static WorldMap worldMap = new WorldMap(BOARD_SIZE);
    static StackPane gameMap;
    Player player;

    @Override
    public void start(Stage katana) {

        gameMap = new StackPane(worldMap);
        player = new Player(worldMap,new Point(1,1));
        PlayerManager.addPlayer(player);

        worldMap.getChildren().add(player);

        VBox playerUIMain = createplayerUIMain(); //okienko z ui itp po prawej
        Pane gamePane = createGamePane();//okienko gry po lewej


        AnchorPane gameLayout = new AnchorPane();
        gameLayout.getChildren().addAll(gamePane,playerUIMain);

        Scene scene = new Scene(gameLayout);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);

        katana.setTitle("Katana");
        katana.getIcons().add(ImageManager.getImage("logoGame2.png",128,128));
        katana.setScene(scene);
        katana.setWidth(WINDOW_SIZE);
        katana.setHeight(700);
        katana.show();

    }

    private Pane createGamePane() { //lewy panel okna (gra)
        Pane gamePane = new Pane();
        gamePane.setBackground(new Background(new BackgroundFill(TerrainType.WATER.getColor().primary,CornerRadii.EMPTY, Insets.EMPTY)));
        //gamePane.setBorder(new Border(new BorderStroke(Color.GOLDENROD,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10))));

        AnchorPane.setTopAnchor(gamePane,0.0);
        AnchorPane.setLeftAnchor(gamePane,0.0);
        AnchorPane.setBottomAnchor(gamePane,0.0);
        AnchorPane.setRightAnchor(gamePane,UI_WIDTH);

        gamePane.getChildren().add(gameMap);
        return gamePane;
    }

    private VBox createplayerUIMain() { //prawy panel okna (ui)
        VBox playerUIMain = new VBox();
        playerUIMain.setBackground(new Background(new BackgroundFill(TerrainType.MOUNTAINS.getColor().primary,CornerRadii.EMPTY, Insets.EMPTY)));
        playerUIMain.setAlignment(Pos.TOP_CENTER);
        playerUIMain.setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().darker,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        playerUIMain.setMinWidth(UI_WIDTH);

        //ruchy gracza (gorny panel)
        VBox btnLayout = createInteractionMenu();

        //lista graczy (prawy panel)
        Pane listPlayer = new Pane();
        listPlayer.setBorder(insideBorder);
        listPlayer.setPadding(new Insets(3));

        //eqPlayer (lewy panel)
        Pane eqPlayer = createEqPlayerPane();

        //dolna czesc UI
        HBox playerUIDown = new HBox();
        playerUIDown.setPrefHeight(1000);
        playerUIDown.getChildren().addAll(eqPlayer,listPlayer);
        //playerUIDown.setBorder(new Border(new BorderStroke(Color.T,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        AnchorPane.setTopAnchor(playerUIMain,0.0);
        AnchorPane.setRightAnchor(playerUIMain,0.0);
        AnchorPane.setBottomAnchor(playerUIMain,0.0);

        playerUIMain.getChildren().addAll(btnLayout,playerUIDown);
        return playerUIMain;
    }

    private VBox createInteractionMenu() { //gorny panel z przyciskami

        VBox interactionMenu = new VBox();
        interactionMenu.setBorder(insideBorder);
        interactionMenu.setAlignment(Pos.CENTER);
        interactionMenu.setSpacing(14);

        MenuButton buildBtn = new MenuButton("exit.png");
        MenuButton destroyBtn = new MenuButton("exit.png");
        MenuButton collectBtn = new MenuButton("exit.png");

        interactionMenu.getChildren().addAll(buildBtn,destroyBtn,collectBtn);
        return  interactionMenu;
    }

    private Pane createEqPlayerPane() {
        Pane eqPlayer = new Pane();
        VBox playerItemsTable = new VBox();
        playerItemsTable.setSpacing(10);
        playerItemsTable.setAlignment(Pos.CENTER);

        for (Map.Entry<String, Item> entry : player.playerEq.equipment.entrySet()) {
            String id = entry.getKey();
            Item item = entry.getValue();

            HBox rowItem = new HBox(item, new Label(id), new Label(String.valueOf(item.getAmount())));
            rowItem.setAlignment(Pos.CENTER);
            rowItem.setSpacing(20);
            playerItemsTable.getChildren().add(rowItem);
        }

        eqPlayer.getChildren().add(playerItemsTable);
        eqPlayer.setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent,BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));

        return eqPlayer;
    }

    private void handleRightClick(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            initialPositionX = event.getX();
            initialPositionY = event.getY();
        }
    }

    private void handleDrag(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            double distanceX = event.getX() - initialPositionX;
            double distanceY = event.getY() - initialPositionY;

            gameMap.setTranslateX(gameMap.getTranslateX() + distanceX);
            gameMap.setTranslateY(gameMap.getTranslateY() + distanceY);

            initialPositionX = event.getX();
            initialPositionY = event.getY();
        }
    }


    private void handleScroll(ScrollEvent event) {
        double zoomFactor = (event.getDeltaY() > 0) ? ZOOM_FACTOR : (1 / ZOOM_FACTOR);
        double offsetX = (event.getX() - gameMap.getWidth() / 2) * (1 - zoomFactor);
        double offsetY = (event.getY() - gameMap.getHeight() / 2) * (1 - zoomFactor);

        gameMap.setScaleX(currentScale*zoomFactor);
        gameMap.setScaleY(currentScale*zoomFactor);
        gameMap.setTranslateX((gameMap.getTranslateX() + offsetX) * zoomFactor);
        gameMap.setTranslateY((gameMap.getTranslateY() + offsetY) * zoomFactor);
        currentScale *= zoomFactor;
    }

    public static void main(String[] args) {
        launch();
    }
}