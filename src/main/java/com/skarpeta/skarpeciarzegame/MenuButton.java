package com.skarpeta.skarpeciarzegame;

import com.skarpeta.skarpeciarzegame.tools.ImageManager;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class MenuButton {

    private final ImageView btnImage;

    MenuButton(String imageName){

        btnImage = new ImageView(ImageManager.getImage(imageName,128,128));

        btnImage.setOnMouseEntered(e -> {btnImage.setScaleX(1.3); btnImage.setScaleY(1.3);});
        btnImage.setOnMouseExited(e -> {btnImage.setScaleX(1); btnImage.setScaleY(1);});
        btnImage.setOnMouseClicked(e -> System.out.println("CLikcked!!"));
    }

    public ImageView getImageView(){
        return btnImage;
    }

}
