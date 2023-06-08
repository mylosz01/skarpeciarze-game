package com.skarpeta.skarpeciarzegame.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class ServerMenu extends Stage {
    private final Button launchButton;
    private final TextField mapSizeField;
    private final TextField portField;
    private final TextField seedField;
    private final TextField nicknameField;

    private final Label exceptionLabel;
    private final CheckBox randomSeedCheckbox;

    public Label getExceptionLabel() {return exceptionLabel;}
    public TextField getSeedField() {return seedField;}
    public Button getLaunchButton() {return launchButton;}
    public TextField getMapSizeField() {return mapSizeField;}
    public TextField getPortField() {return portField;}
    public TextField getNicknameField() {return nicknameField;}

    public ServerMenu() {
        setTitle("Server config");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label mapSizeLabel = new Label("Map size:");
        mapSizeField = new TextField();
        mapSizeField.setText("50");
        mapSizeField.setMaxWidth(80);
        gridPane.add(mapSizeLabel, 0, 0);
        gridPane.add(mapSizeField, 1, 0);

        Label seedLabel = new Label("Map seed:");
        seedField = new TextField();
        seedField.setMaxWidth(80);
        gridPane.add(seedLabel, 0, 1);
        gridPane.add(seedField, 1, 1);

        randomSeedCheckbox = new CheckBox("random");
        randomSeedCheckbox.setOnAction(e -> clickCheckBox());
        setRandomSeed(true);
        gridPane.add(randomSeedCheckbox, 2, 1);

        Label portLabel = new Label("Port Number:");
        portField = new TextField();
        portField.setText("5555");
        portField.setMaxWidth(80);
        gridPane.add(portLabel, 0, 2);
        gridPane.add(portField, 1, 2);

        Label nicknameLabel = new Label("Nickname:");
        nicknameField = new TextField();
        nicknameField.setText("Host");
        nicknameField.setMaxWidth(80);
        gridPane.add(nicknameLabel, 0, 3);
        gridPane.add(nicknameField, 1, 3);

        launchButton = new Button("Launch server");
        gridPane.add(launchButton, 2,3);

        exceptionLabel = new Label();
        exceptionLabel.setTextFill(Color.RED);
        gridPane.add(exceptionLabel,0,4,3,4);

        Scene scene = new Scene(gridPane, 300, 150);
        setScene(scene);
        show();
    }

    private void clickCheckBox() {
        setRandomSeed(randomSeedCheckbox.isSelected());
    }

    private void setRandomSeed(boolean b) {
        seedField.setText(b ? Integer.toString(new Random().nextInt(99999)) : "");
        seedField.setDisable(b);
        randomSeedCheckbox.setSelected(b);
    }

    public boolean isSeedRandom(){
        return randomSeedCheckbox.isSelected();
    }
}
