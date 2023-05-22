package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.inventory.Inventory;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.DoubleBuffer;
import java.util.HashMap;
import java.util.Map;

public class Catana extends Application {
    public static final int BOARD_SIZE = 50;
    public static final int WINDOW_SIZE = 500;
    public static final double FIELD_WIDTH = 50;
    private double currentScale = 1.0;
    double initialPositionX=0;
    double initialPositionY=0;
    private static final double ZOOM_FACTOR = 1.1;

    static WorldMap worldMap = new WorldMap(BOARD_SIZE);
    static StackPane gameMap;

    @Override
    public void start(Stage game) {

        gameMap = new StackPane(worldMap);

        VBox playerUIMain = new VBox();

        playerUIMain.setBackground(new Background(new BackgroundFill(TerrainType.MOUNTAINS.getColor().primary,CornerRadii.EMPTY, Insets.EMPTY)));
        playerUIMain.setAlignment(Pos.TOP_CENTER);
        playerUIMain.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));


        //ruchy gracza (gora)
        VBox btnLayout = new VBox();
        btnLayout.setAlignment(Pos.CENTER);
        btnLayout.setSpacing(20);

        Button buildBtn = new Button("Zbuduj");
        Button destroyBtn = new Button("Zniszcz");
        Button collectBtn = new Button("Zbierz");

        btnLayout.getChildren().addAll(buildBtn,destroyBtn,collectBtn);
        btnLayout.setBorder(new Border(new BorderStroke(Color.DARKGOLDENROD,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));


        //eqPlayer (lewo)
        Pane eqPlayer = new Pane();
        eqPlayer.setBorder(new Border(new BorderStroke(Color.RED,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        //lista graczy (prawo)
        Pane listPlayer = new Pane();
        listPlayer.setBorder(new Border(new BorderStroke(Color.SKYBLUE,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        //
        // sample data

        Inventory inv = new Inventory();
        Map<String, Item> map = inv.getEquipment();

        TableColumn<Map.Entry<String, Item>, String> column1 = new TableColumn<>("Nazwa");
        column1.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));

        TableColumn<Map.Entry<String, Item>, String> column2 = new TableColumn<>("Ilość");
        column2.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getAmount().toString()));

        ObservableList<Map.Entry<String, Item>> items = FXCollections.observableArrayList(map.entrySet());
        final TableView<Map.Entry<String,Item>> table = new TableView<>(items);

        table.getColumns().setAll(column1, column2);
        table.setMaxWidth(100);

        eqPlayer.getChildren().add(table);
        //

        //dolna czesc UI
        HBox playerUIDown = new HBox();
        playerUIDown.setPrefHeight(1000);
        playerUIDown.getChildren().addAll(eqPlayer,listPlayer);
        playerUIDown.setBorder(new Border(new BorderStroke(Color.GREENYELLOW,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        playerUIMain.getChildren().addAll(btnLayout,playerUIDown);

        AnchorPane gameLayout = new AnchorPane();
        gameLayout.getChildren().addAll(gameMap,playerUIMain);
        AnchorPane.setTopAnchor(playerUIMain,0.0);
        AnchorPane.setRightAnchor(playerUIMain,0.0);
        AnchorPane.setBottomAnchor(playerUIMain,0.0);

        Scene scene = new Scene(gameLayout);
        scene.setFill(TerrainType.WATER.getColor().primary);
        scene.setOnScroll(this::handleScroll);
        scene.setOnMouseDragged(this::handleDrag);
        scene.setOnMousePressed(this::handleRightClick);
        game.setTitle("catana");
        game.setScene(scene);
        game.setWidth(1200);
        game.setHeight(800);
        game.show();
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
        currentScale *= zoomFactor;
        gameMap.setScaleX(currentScale);
        gameMap.setScaleY(currentScale);

        double offsetX = (event.getX() - gameMap.getWidth() / 2) * (1 - zoomFactor);
        double offsetY = (event.getY() - gameMap.getHeight() / 2) * (1 - zoomFactor);

        gameMap.setTranslateX((gameMap.getTranslateX() + offsetX) * zoomFactor);
        gameMap.setTranslateY((gameMap.getTranslateY() + offsetY) * zoomFactor);
    }

    public static void main(String[] args) {
        launch();
    }
}