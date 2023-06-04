package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.network.Player;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;
import com.skarpeta.skarpeciarzegame.worldmap.Field;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.Map;

import static com.skarpeta.skarpeciarzegame.app.Catana.UI_WIDTH;

public class PlayerUI extends VBox {

    HBox buttonCategoriesPane;
    VBox fieldActionPane;
    MenuButton destroyButton;
    MenuButton collectButton;

    MenuButton quarryBtn;
    MenuButton mineshaftBtn;
    MenuButton sawmillBtn;

    VBox playerItemsTable;
    VBox buildActionPane;

    Pane inventoryPane;
    Border insideBorder = new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent,BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5)));

    public PlayerUI() {
        super();
        setBackground(new Background(new BackgroundFill(TerrainType.MOUNTAINS.getColor().primary,CornerRadii.EMPTY, Insets.EMPTY)));
        setAlignment(Pos.TOP_CENTER);
        setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().darker,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        setMinWidth(UI_WIDTH);

        //ruchy gracza (gorny panel)
        HBox btnLayout = createInteractionMenu();

        //lista graczy (prawy panel)
        Pane listPlayer = new Pane();
        listPlayer.setBorder(insideBorder);
        listPlayer.setPadding(new Insets(3));

        //eqPlayer (lewy panel)
        inventoryPane = createEqPlayerPane();

        //dolna czesc UI
        HBox playerUIDown = new HBox();
        playerUIDown.setPrefHeight(1000);
        playerUIDown.getChildren().addAll(inventoryPane,listPlayer);

        AnchorPane.setTopAnchor(this,0.0);
        AnchorPane.setRightAnchor(this,0.0);
        AnchorPane.setBottomAnchor(this,0.0);

        getChildren().addAll(btnLayout,playerUIDown);
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

        destroyButton = new MenuButton("break");
        destroyButton.setOnMouseClicked(e -> Catana.getClientThread().sendRemoveBuilding(Catana.getClientThread().getPlayer().playerField.position));
        collectButton = new MenuButton("get");
        collectButton.setOnMouseClicked(e -> Catana.getClientThread().sendRemoveResource(Catana.getClientThread().getPlayer().playerField.position));

        buildActionPane = new VBox();
        buildActionPane.setAlignment(Pos.CENTER);
        buildActionPane.setSpacing(14);
        buildActionPane.setMinWidth(150);

        quarryBtn = new MenuButton("buildQuarry");
        quarryBtn.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position, BuildingType.QUARRY));
        mineshaftBtn = new MenuButton("buildMineshaft");
        mineshaftBtn.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position,BuildingType.MINESHAFT));
        sawmillBtn = new MenuButton("buildSawmill");
        sawmillBtn.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position,BuildingType.SAWMILL));

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

    public void updateButtonUI() {
        if(Catana.getClientThread()== null || Catana.getClientThread().getPlayer() == null)
            return;
        Field field = Catana.getClientThread().getPlayer().playerField;
        Platform.runLater(()->{
            fieldActionPane.getChildren().clear();
            buildActionPane.getChildren().clear();
            if(field.hasResource())
                fieldActionPane.getChildren().add(collectButton);
            if(field.hasBuilding())
                fieldActionPane.getChildren().add(destroyButton);
            else {
                switch (field.terrain) {
                    case MOUNTAINS -> buildActionPane.getChildren().addAll(quarryBtn, mineshaftBtn);
                    case GRASS_LAND -> buildActionPane.getChildren().add(sawmillBtn);
                }
            }
        });
    }


    public void renderInventory(Player player) {

        playerItemsTable.getChildren().clear();

        for (Map.Entry<String, Item> entry : player.getInventory().equipment.entrySet()) {
            Item item = entry.getValue();

            Label idLabel = new Label(item.getName());
            idLabel.setFont(new Font("Arial",20));

            Label amountLabel = new Label(String.valueOf(item.getAmount()));
            amountLabel.setFont(new Font("Arial",20));

            HBox rowItem = new HBox(item, idLabel, amountLabel);
            rowItem.setAlignment(Pos.CENTER);
            rowItem.setSpacing(20);
            playerItemsTable.getChildren().add(rowItem);
        }
    }

}
