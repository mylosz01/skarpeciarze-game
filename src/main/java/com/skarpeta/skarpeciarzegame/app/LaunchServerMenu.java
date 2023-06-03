package com.skarpeta.skarpeciarzegame.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LaunchServerMenu extends Stage {
    private Button launchButton;
    private TextField mapSizeField;
    private TextField portField;
    private TextField seedField;
    private CheckBox localhostCheckBox;

    public TextField getSeedField() {
        return seedField;
    }

    public Button getLaunchButton() {
        return launchButton;
    }

    public TextField getMapSizeField() {
        return mapSizeField;
    }

    public TextField getPortField() {
        return portField;
    }

    public LaunchServerMenu() {
        setTitle("Server config");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label mapSizeLabel = new Label("Map size:");
        mapSizeField = new TextField();
        mapSizeField.setText("50");
        gridPane.add(mapSizeLabel, 0, 0);
        gridPane.add(mapSizeField, 1, 0);

        Label seedLabel = new Label("Map seed:");
        seedField = new TextField();
        gridPane.add(seedLabel, 0, 1);
        gridPane.add(seedField, 1, 1);

        localhostCheckBox = new CheckBox("random seed");
        localhostCheckBox.setOnAction(e -> clickCheckBox());
        setRandomSeed(true);
        gridPane.add(localhostCheckBox, 2, 1);

        Label portLabel = new Label("Port Number:");
        portField = new TextField();
        portField.setText("5555");
        gridPane.add(portLabel, 0, 2);
        gridPane.add(portField, 1, 2);

        launchButton = new Button("Launch server");

        gridPane.add(launchButton, 2,2);

        Scene scene = new Scene(gridPane, 300, 150);
        setScene(scene);
        show();
    }

    private void clickCheckBox() {
        setRandomSeed(localhostCheckBox.isSelected());
    }

    private void setRandomSeed(boolean b) {
        String text = b ? "randomized" : "";
        seedField.setText(text);
        seedField.setDisable(b);
        localhostCheckBox.setSelected(b);
    }
}
