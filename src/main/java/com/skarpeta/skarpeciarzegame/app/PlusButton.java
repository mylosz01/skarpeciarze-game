package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import com.skarpeta.skarpeciarzegame.worldmap.BuildingType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import static com.skarpeta.skarpeciarzegame.app.PlayerUI.customFont;

public class PlusButton extends Group {
    private MenuButton button;
    public PlusButton(String textureName,BuildingType type) {
        HBox box = createBuildingButton(type, "button/"+textureName+".png");
        getChildren().add(box);
    }
    private HBox createBuildingButton(BuildingType type, String buttonImage) {
        button = new MenuButton("plusButton", "plusButtonHover", 56, 56);
        HBox buildingBox = new HBox();
        button.setOnMouseClicked(e -> Catana.getClientThread().sendBuildBuilding(Catana.getClientThread().getPlayer().playerField.position, type));
        buildingBox.getChildren().addAll(button, new ImageView(ImageManager.getImage(buttonImage, 64, 64)));
        buildingBox.setAlignment(Pos.CENTER);

        Tooltip costTooltip = createTooltip(type);
        Tooltip.install(buildingBox, costTooltip);

        return buildingBox;
    }

    private Tooltip createTooltip(BuildingType type) {
        Tooltip costTooltip = new Tooltip();
        HBox buildingCost = new HBox();
        type.getCost().forEach(e -> {
            Label label = new Label(Integer.toString(e.getAmount()));
            label.setFont(customFont);
            buildingCost.getChildren().addAll(e, label);
        });
        costTooltip.setGraphic(buildingCost);
        costTooltip.setShowDelay(new Duration(250));
        return costTooltip;
    }

    public void setEnabled(boolean state) {
        button.setEnabled(state);
    }
}
