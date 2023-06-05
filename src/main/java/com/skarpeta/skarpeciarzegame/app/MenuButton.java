package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.Asset;
import com.skarpeta.skarpeciarzegame.tools.ImageManager;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.*;


/**
 * Przycisk z wybraną grafiką
 */
public class MenuButton extends Asset {
    ImageView hoverTexture;

    /**
     * Tworzenie przycisku z grafiką o nazwie pliku textureName (wraz z rozszerzeniem)
     */
    public MenuButton(String textureName) {
        this(textureName, "hover", 128, 128);
    }

    public MenuButton(String textureName, String hoverTextureName, int sizeX, int sizeY) {
        super(new ImageView(ImageManager.getImage("button/" + textureName + ".png", sizeX, sizeY)));
        setHoverImage(hoverTextureName);
        setOnMouseEntered(e -> getChildren().add(hoverTexture));
        setOnMouseExited(e -> getChildren().remove(hoverTexture));
        setCursor(new ImageCursor(ImageManager.getImage("hand.png", 16, 16), 6, 0));
    }

    public void setHoverImage(String hoverTextureName) {
        this.hoverTexture = new ImageView(ImageManager.getImage("button/" + hoverTextureName + ".png",
                this.getTexture().getImage().getWidth(), this.getTexture().getImage().getHeight()));
    }
}
