package com.skarpeta.skarpeciarzegame;

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Menu extends Application {

    @Override
    public void start(Stage menu) throws Exception {

        menu.setTitle("Sockware game Catan");
        menu.show();
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}
