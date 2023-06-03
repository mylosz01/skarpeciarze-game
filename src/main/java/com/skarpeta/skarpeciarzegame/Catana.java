package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.network.Client;
import com.skarpeta.skarpeciarzegame.network.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
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

    static WorldMap worldMap;
    static StackPane gameMap;
    public static Stage katana;
    static Pane eqPane;
    static VBox playerItemsTable;

    //static MenuButton buildBtn;
    static MenuButton destroyBtn;
    static MenuButton collectBtn;

    HBox buttonCategoriesPane;
    static VBox fieldActionPane;
    static VBox buildActionPane;

    static MenuButton quarryBtn;
    static MenuButton mineshaftBtn;
    static MenuButton sawmillBtn;

    static Client clientThread;

    @Override
    public void start(Stage katana) throws IOException, ClassNotFoundException {
        this.katana = katana;
        System.out.println("CLIENT START");
        clientThread = new Client();
        Thread playerSend = new Thread(clientThread);
        playerSend.start();
        worldMap = clientThread.getWorldMap();
        gameMap = new StackPane(worldMap);
        setupUI();
    }

    public static void renderInventory(Player player) {

        playerItemsTable.getChildren().clear();

        for (Map.Entry<String, Item> entry : player.getInventory().equipment.entrySet()) {
            String id = entry.getKey();
            Item item = entry.getValue();

            Label idLabel = new Label(id);
            idLabel.setFont(new Font("Arial",20));

            Label amountLabel = new Label(String.valueOf(item.getAmount()));
            amountLabel.setFont(new Font("Arial",20));

            HBox rowItem = new HBox(item, idLabel, amountLabel);
            rowItem.setAlignment(Pos.CENTER);
            rowItem.setSpacing(20);
            playerItemsTable.getChildren().add(rowItem);
        }
    }

    private void setupUI() {
        VBox playerUIMain = createplayerUIMain(); //okienko z ui itp po prawej
        Pane gamePane = createGamePane();//okienko gry po lewej

        AnchorPane gameLayout = new AnchorPane();
        gameLayout.getChildren().addAll(gamePane,playerUIMain);

        Scene scene = new Scene(gameLayout);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);

        katana.setTitle("Katana");
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
        HBox btnLayout = createInteractionMenu();

        //lista graczy (prawy panel)
        Pane listPlayer = new Pane();
        listPlayer.setBorder(insideBorder);
        listPlayer.setPadding(new Insets(3));

        //eqPlayer (lewy panel)
        eqPane = createEqPlayerPane();

        //dolna czesc UI
        HBox playerUIDown = new HBox();
        playerUIDown.setPrefHeight(1000);
        playerUIDown.getChildren().addAll(eqPane,listPlayer);

        AnchorPane.setTopAnchor(playerUIMain,0.0);
        AnchorPane.setRightAnchor(playerUIMain,0.0);
        AnchorPane.setBottomAnchor(playerUIMain,0.0);

        playerUIMain.getChildren().addAll(btnLayout,playerUIDown);
        return playerUIMain;
    }
    public static void updateButtonUI() {
        if(clientThread== null || clientThread.getPlayer() == null)
            return;
        Field field = clientThread.getPlayer().playerField;
        Platform.runLater(()->{
            fieldActionPane.getChildren().clear();
            buildActionPane.getChildren().clear();
            if(field.hasResource())
                fieldActionPane.getChildren().add(collectBtn);
            if(field.hasBuilding())
                fieldActionPane.getChildren().add(destroyBtn);
            else {
                switch (field.terrain) {
                    case MOUNTAINS -> buildActionPane.getChildren().addAll(quarryBtn, mineshaftBtn);
                    case GRASS_LAND -> buildActionPane.getChildren().add(sawmillBtn);
                }
            }
        });
    }

    private HBox createInteractionMenu(){ //gorny panel z przyciskami
        buttonCategoriesPane = new HBox();
        buttonCategoriesPane.setBorder(insideBorder);
        buttonCategoriesPane.setAlignment(Pos.CENTER);
        buttonCategoriesPane.setMinHeight(200);

        fieldActionPane = new VBox();
        fieldActionPane.setAlignment(Pos.CENTER);
        fieldActionPane.setSpacing(14);
        fieldActionPane.setMinWidth(150);

        destroyBtn = new MenuButton("break.png");
        destroyBtn.setOnMouseClicked(e -> clientThread.sendRemoveBuilding(clientThread.getPlayer().playerField.position));
        collectBtn = new MenuButton("get.png");
        collectBtn.setOnMouseClicked(e -> clientThread.sendRemoveResource(clientThread.getPlayer().playerField.position));

        buildActionPane = new VBox();
        buildActionPane.setAlignment(Pos.CENTER);
        buildActionPane.setSpacing(14);
        buildActionPane.setMinWidth(150);

        quarryBtn = new MenuButton("buildQuarry.png");
        quarryBtn.setOnMouseClicked(e -> clientThread.sendBuildBuilding(clientThread.getPlayer().playerField.position,BuildingType.QUARRY));
        mineshaftBtn = new MenuButton("buildMineshaft.png");
        mineshaftBtn.setOnMouseClicked(e -> clientThread.sendBuildBuilding(clientThread.getPlayer().playerField.position,BuildingType.MINESHAFT));
        sawmillBtn = new MenuButton("buildSawmill.png");
        sawmillBtn.setOnMouseClicked(e -> clientThread.sendBuildBuilding(clientThread.getPlayer().playerField.position,BuildingType.SAWMILL));

        buttonCategoriesPane.getChildren().addAll(fieldActionPane, buildActionPane);
        return buttonCategoriesPane;
    }

    private Pane createEqPlayerPane() {
        Pane eqPlayer = new Pane();
        playerItemsTable = new VBox();
        playerItemsTable.setSpacing(10);
        playerItemsTable.setAlignment(Pos.CENTER);
        eqPlayer.getChildren().add(playerItemsTable);
        eqPlayer.setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));

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