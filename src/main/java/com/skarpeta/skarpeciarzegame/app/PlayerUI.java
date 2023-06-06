package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.inventory.*;
import com.skarpeta.skarpeciarzegame.network.Player;
import com.skarpeta.skarpeciarzegame.tools.FontManager;
import com.skarpeta.skarpeciarzegame.buildings.BuildingType;
import com.skarpeta.skarpeciarzegame.worldmap.Field;
import com.skarpeta.skarpeciarzegame.worldmap.TerrainType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.Map;

import static com.skarpeta.skarpeciarzegame.app.Catana.UI_WIDTH;

public class PlayerUI extends VBox {

    HBox buttonCategoriesPane;
    VBox fieldActionPane;
    MenuButton destroyButton;
    MenuButton collectButton;

    PlusButton quarryButton;
    PlusButton mineshaftButton;
    PlusButton sawmillButton;
    PlusButton boatButton;
    PlusButton catanaButton;

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
        HBox buttonLayout = createButtonLayout();
        buttonLayout.setSpacing(spacing);

        //lista graczy (prawy panel)
        Pane leaderboardPane = new Pane(); //todo createLeaderBoardPane();
        leaderboardPane.setBorder(insideBorder);

        //inventory (lewy panel)
        inventoryPane = createInventoryPane();

        //dolna czesc UI
        HBox InventoryAndLeaderboard = new HBox();
        InventoryAndLeaderboard.setSpacing(spacing);
        InventoryAndLeaderboard.setPrefHeight(1000);
        InventoryAndLeaderboard.getChildren().addAll(inventoryPane,leaderboardPane);

        AnchorPane.setTopAnchor(this,0.0);
        AnchorPane.setRightAnchor(this,0.0);
        AnchorPane.setBottomAnchor(this,0.0);

        getChildren().addAll(buttonLayout,InventoryAndLeaderboard);
        updateButtons();
    }


    private HBox createButtonLayout(){ //gorny panel z przyciskami
        buttonCategoriesPane = new HBox();
        buttonCategoriesPane.setBorder(insideBorder);
        buttonCategoriesPane.setAlignment(Pos.CENTER);
        buttonCategoriesPane.setMinHeight(200);

        destroyButton = new MenuButton("break");
        destroyButton.setOnMouseClicked(e -> Catana.getClientThread().sendRemoveBuilding(Catana.getClientThread().getPlayer().playerField.position));
        collectButton = new MenuButton("get");
        collectButton.setOnMouseClicked(e -> Catana.getClientThread().sendRemoveResource(Catana.getClientThread().getPlayer().playerField.position));

        sawmillButton = new PlusButton("button/sawmillButton.png",BuildingType.SAWMILL.getCost(),"Can be placed on Grass. Generates Wood.");
        sawmillButton.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position, BuildingType.SAWMILL));

        quarryButton = new PlusButton("button/quarryButton.png",BuildingType.QUARRY.getCost(),"Can be placed on Stone. Generates Stone.");
        quarryButton.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position, BuildingType.QUARRY));

        mineshaftButton = new PlusButton("button/mineshaftButton.png",BuildingType.MINESHAFT.getCost(),"Can be placed on Stone. Generates Gold.");
        mineshaftButton.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position, BuildingType.MINESHAFT));

        fieldActionPane = new VBox();
        fieldActionPane.setAlignment(Pos.CENTER);
        fieldActionPane.setSpacing(0);
        fieldActionPane.setMinWidth(150);
        fieldActionPane.getChildren().addAll(collectButton, destroyButton);

        buildActionPane = new VBox();
        buildActionPane.setAlignment(Pos.CENTER);
        buildActionPane.setSpacing(0);
        buildActionPane.setMinWidth(150);
        buildActionPane.getChildren().addAll(sawmillButton, quarryButton, mineshaftButton);

        buttonCategoriesPane.getChildren().addAll(fieldActionPane, buildActionPane);
        return buttonCategoriesPane;
    }

    private Pane createInventoryPane() {
        Pane eqPlayer = new Pane();
        VBox inventoryLayout = new VBox();
        inventoryLayout.setSpacing(spacing*0.5);
        playerItemsTable = new VBox();
        playerItemsTable.setSpacing(spacing*0.5);
        playerItemsTable.setPadding(new Insets(spacing));
        playerItemsTable.setAlignment(Pos.CENTER);
        eqPlayer.getChildren().add(playerItemsTable);


        boatButton = new PlusButton("item/boatItem.png",BoatItem.getCost(),"Used to swim through water once. Can hold only "+Inventory.MAX_BOAT_HOLD+".");
        boatButton.setOnMouseClicked(e -> {
            Catana.getClientThread().getPlayer().getInventory().craft(ItemType.BOAT);
            renderInventory(Catana.getClientThread().getPlayer());
        });
        catanaButton= new PlusButton("item/catanaItem.png", CatanaItem.getCost(),"Victory!");
        catanaButton.setOnMouseClicked(e -> {
            Catana.getClientThread().getPlayer().getInventory().craft(ItemType.CATANA);
            renderInventory(Catana.getClientThread().getPlayer());
        });
        HBox craftButtons = new HBox(boatButton,catanaButton);

        inventoryLayout.getChildren().addAll(eqPlayer,craftButtons);
        eqPlayer.setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        craftButtons.setBorder(new Border(new BorderStroke(TerrainType.MOUNTAINS.getColor().accent, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(eqPlayer.widthProperty());
        clip.heightProperty().bind(eqPlayer.heightProperty());
        eqPlayer.setClip(clip);
        return inventoryLayout;
    }

    public void updateButtons() {
        if(Catana.getClientThread()== null || Catana.getClientThread().getPlayer() == null)
            return;
        Field field = Catana.getClientThread().getPlayer().playerField;
        Inventory inventory = Catana.getClientThread().getPlayer().getInventory();
        Platform.runLater(()->{
            collectButton.setEnabled(field.hasResource());
            destroyButton.setEnabled(field.hasBuilding());
            sawmillButton.setEnabled(field.terrain == TerrainType.GRASS_LAND && !field.hasBuilding() && inventory.hasEnoughMaterials(BuildingType.SAWMILL.getCost()));
            quarryButton.setEnabled(field.terrain == TerrainType.MOUNTAINS && !field.hasBuilding() && inventory.hasEnoughMaterials(BuildingType.QUARRY.getCost()));
            mineshaftButton.setEnabled(field.terrain == TerrainType.MOUNTAINS && !field.hasBuilding() && inventory.hasEnoughMaterials(BuildingType.MINESHAFT.getCost()));

            boatButton.setEnabled(inventory.hasEnoughMaterials(BoatItem.getCost()) && inventory.getAmount(ItemType.BOAT) < Inventory.MAX_BOAT_HOLD);
            catanaButton.setEnabled(inventory.hasEnoughMaterials(CatanaItem.getCost()));
        });
    }


    public void renderInventory(Player player) {

        playerItemsTable.getChildren().clear();

        for (Map.Entry<ItemType, Item> entry : player.getInventory().equipment.entrySet()) {
            Item item = entry.getValue();

            Label idLabel = new Label(item.getName());
            idLabel.setFont(FontManager.getBigFont());
            idLabel.setMinWidth(70);

            Label amountLabel = new Label(String.valueOf(item.getAmount()));
            amountLabel.setFont(FontManager.getBigFont());
            amountLabel.setMinWidth(40);

            HBox rowItem = new HBox(item, idLabel, amountLabel);
            rowItem.setAlignment(Pos.CENTER);
            rowItem.setSpacing(15);
            playerItemsTable.getChildren().add(rowItem);
        }
        updateButtons();
    }
}
