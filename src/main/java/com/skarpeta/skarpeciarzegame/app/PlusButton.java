package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.buildings.Craftable;
import com.skarpeta.skarpeciarzegame.inventory.Item;
import com.skarpeta.skarpeciarzegame.tools.FontManager;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;



public class PlusButton extends Group {
    private MenuButton button;
    private final Label tooltipLabel;
    private final Label nameLabel;
    private final ArrayList<Item> tooltipList;
    Label costText = new Label("\nPRICE:");
    public PlusButton(String texturePath, Craftable type) {
        this(texturePath, type.getDescription(), type.getCost(), type.toString());
    }
    public PlusButton(String texturePath, String description, ArrayList<Item> tooltipList, String name){
        this.tooltipList = tooltipList;
        this.tooltipLabel = new Label(description);
        this.nameLabel = new Label(name);
        tooltipLabel.setFont(FontManager.getSmallFont());
        costText.setFont(FontManager.getBigFont());
        nameLabel.setFont(FontManager.getBigFont());
        HBox box = createBuildingButton(texturePath);
        getChildren().add(box);
    }
    private HBox createBuildingButton(String texturePath) {
        button = new MenuButton("plusButton", "plusButtonHover", 56, 56);
        HBox buildingBox = new HBox();
        buildingBox.getChildren().addAll(button, new ImageView(ImageManager.getImage(texturePath, 64, 64)));
        buildingBox.setAlignment(Pos.CENTER);

        Tooltip.install(buildingBox, createTooltip());

        return buildingBox;
    }

    private Tooltip createTooltip() {
        Tooltip costTooltip = new Tooltip();
        HBox tooltipItems = new HBox();
        VBox tooltipContent = new VBox();
        tooltipList.forEach(e -> {
            Label label = new Label(Integer.toString(e.getAmount()));
            label.setFont(FontManager.getBigFont());
            tooltipItems.getChildren().addAll(e, label);
        });
        tooltipContent.getChildren().addAll(nameLabel,tooltipLabel,costText,tooltipItems);
        costTooltip.setGraphic(tooltipContent);
        costTooltip.setShowDelay(new Duration(250));
        return costTooltip;
    }

    public void setEnabled(boolean state) {
        button.setEnabled(state);
    }
}
