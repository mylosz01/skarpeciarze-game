package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.inventory.Inventory;
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
    public static Font customFont = Font.loadFont(PlayerUI.class.getResourceAsStream("/ARCADECLASSIC.TTF"), 28);

    HBox buttonCategoriesPane;
    VBox fieldActionPane;
    MenuButton destroyButton;
    MenuButton collectButton;

    PlusButton quarryBtn;
    PlusButton mineshaftBtn;
    PlusButton sawmillBtn;

    VBox playerItemsTable;
    VBox buildActionPane;

    private final double spacing = 10;

    Pane inventoryPane;
    Border insideBorder = new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent,BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(4)));

    public PlayerUI() {
        super();

        setBackground(new Background(new BackgroundFill(TerrainType.MOUNTAINS.getColor().primary,CornerRadii.EMPTY, Insets.EMPTY)));
        setSpacing(spacing);

        setAlignment(Pos.CENTER);
        setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().darker,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        setMinWidth(UI_WIDTH);

        //ruchy gracza (gorny panel)
        HBox btnLayout = createInteractionMenu();
        btnLayout.setSpacing(spacing);

        //lista graczy (prawy panel)
        Pane listPlayer = new Pane();
        listPlayer.setBorder(insideBorder);
        //listPlayer.setPadding(new Insets(spacing));

        //eqPlayer (lewy panel)
        inventoryPane = createEqPlayerPane();
        //inventoryPane.setPadding(new Insets(spacing));

        //dolna czesc UI
        HBox playerUIDown = new HBox();
        playerUIDown.setSpacing(spacing);
        playerUIDown.setPrefHeight(1000);
        playerUIDown.getChildren().addAll(inventoryPane,listPlayer);

        AnchorPane.setTopAnchor(this,0.0);
        AnchorPane.setRightAnchor(this,0.0);
        AnchorPane.setBottomAnchor(this,0.0);

        getChildren().addAll(btnLayout,playerUIDown);
        updateButtonUI();
    }


    private HBox createInteractionMenu(){ //gorny panel z przyciskami
        buttonCategoriesPane = new HBox();
        buttonCategoriesPane.setBorder(insideBorder);
        buttonCategoriesPane.setAlignment(Pos.CENTER);
        buttonCategoriesPane.setMinHeight(200);

        destroyButton = new MenuButton("break");
        destroyButton.setOnMouseClicked(e -> Catana.getClientThread().sendRemoveBuilding(Catana.getClientThread().getPlayer().playerField.position));
        collectButton = new MenuButton("get");
        collectButton.setOnMouseClicked(e -> Catana.getClientThread().sendRemoveResource(Catana.getClientThread().getPlayer().playerField.position));

        sawmillBtn = new PlusButton("sawmillButton",BuildingType.SAWMILL);
        quarryBtn = new PlusButton("quarryButton",BuildingType.QUARRY);
        mineshaftBtn = new PlusButton("mineshaftButton",BuildingType.MINESHAFT);

        fieldActionPane = new VBox();
        fieldActionPane.setAlignment(Pos.CENTER);
        fieldActionPane.setSpacing(0);
        fieldActionPane.setMinWidth(150);
        fieldActionPane.getChildren().addAll(collectButton, destroyButton);

        buildActionPane = new VBox();
        buildActionPane.setAlignment(Pos.CENTER);
        buildActionPane.setSpacing(0);
        buildActionPane.setMinWidth(150);
        buildActionPane.getChildren().addAll(sawmillBtn,quarryBtn,mineshaftBtn);

        buttonCategoriesPane.getChildren().addAll(fieldActionPane, buildActionPane);
        return buttonCategoriesPane;
    }

    private Pane createEqPlayerPane() {
        Pane eqPlayer = new Pane();
        playerItemsTable = new VBox();
        playerItemsTable.setSpacing(spacing*0.5);
        playerItemsTable.setPadding(new Insets(spacing));
        playerItemsTable.setAlignment(Pos.CENTER);
        eqPlayer.getChildren().add(playerItemsTable);
        eqPlayer.setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));

        return eqPlayer;
    }

    public void updateButtonUI() {
        if(Catana.getClientThread()== null || Catana.getClientThread().getPlayer() == null)
            return;
        Field field = Catana.getClientThread().getPlayer().playerField;
        Inventory inventory = Catana.getClientThread().getPlayer().getInventory();
        Platform.runLater(()->{
            collectButton.setEnabled(field.hasResource());
            destroyButton.setEnabled(field.hasBuilding());
            sawmillBtn.setEnabled(field.terrain == TerrainType.GRASS_LAND && !field.hasBuilding() && inventory.hasEnoughMaterials(BuildingType.SAWMILL));
            quarryBtn.setEnabled(field.terrain == TerrainType.MOUNTAINS && !field.hasBuilding() && inventory.hasEnoughMaterials(BuildingType.QUARRY));
            mineshaftBtn.setEnabled(field.terrain == TerrainType.MOUNTAINS && !field.hasBuilding() && inventory.hasEnoughMaterials(BuildingType.MINESHAFT));
        });
    }


    public void renderInventory(Player player) {

        playerItemsTable.getChildren().clear();

        for (Map.Entry<String, Item> entry : player.getInventory().equipment.entrySet()) {
            Item item = entry.getValue();

            Label idLabel = new Label(item.getName());
            idLabel.setFont(customFont);
            idLabel.setMinWidth(70);

            Label amountLabel = new Label(String.valueOf(item.getAmount()));
            amountLabel.setFont(customFont);
            amountLabel.setMinWidth(40);

            HBox rowItem = new HBox(item, idLabel, amountLabel);
            rowItem.setAlignment(Pos.CENTER);
            rowItem.setSpacing(15);
            playerItemsTable.getChildren().add(rowItem);
        }
        updateButtonUI();
    }
}
