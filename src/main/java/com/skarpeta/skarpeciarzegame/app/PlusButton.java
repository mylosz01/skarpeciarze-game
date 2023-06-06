package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.inventory.Item;
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

import static com.skarpeta.skarpeciarzegame.app.PlayerUI.bigFont;
import static com.skarpeta.skarpeciarzegame.app.PlayerUI.smallFont;

public class PlusButton extends Group {
    private MenuButton button;
    private Label tooltipLabel;
    private ArrayList<Item> tooltipList;
    Label costText = new Label("\nPRICE:");
    public PlusButton(String texturePath, ArrayList<Item> tooltipList, String tooltipMessage) {
        this.tooltipList = tooltipList;
        this.tooltipLabel = new Label(tooltipMessage);
        tooltipLabel.setFont(smallFont);
        costText.setFont(bigFont);
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
            label.setFont(bigFont);
            tooltipItems.getChildren().addAll(e, label);
        });
        tooltipContent.getChildren().addAll(tooltipLabel,costText,tooltipItems);
        costTooltip.setGraphic(tooltipContent);
        costTooltip.setShowDelay(new Duration(250));
        return costTooltip;
    }

    public void setEnabled(boolean state) {
        button.setEnabled(state);
    }
}
