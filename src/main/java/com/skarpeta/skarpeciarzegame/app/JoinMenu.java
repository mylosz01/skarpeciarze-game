package com.skarpeta.skarpeciarzegame.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JoinMenu extends Stage {
    private Button connectButton;
    private TextField ipField;
    private TextField portField;

    public Button getConnectButton() {
        return connectButton;
    }

    public TextField getIpField() {
        return ipField;
    }

    public TextField getPortField() {
        return portField;
    }

    public JoinMenu() {
        setTitle("IP and Port Menu");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label ipLabel = new Label("IP Address:");
        ipField = new TextField();
        ipField.setText("127.0.0.1");
        gridPane.add(ipLabel, 0, 0);
        gridPane.add(ipField, 1, 0);

        Label portLabel = new Label("Port Number:");
        portField = new TextField();
        portField.setText("5555");
        gridPane.add(portLabel, 0, 1);
        gridPane.add(portField, 1, 1);

        connectButton = new Button("Connect");

        gridPane.add(connectButton, 0, 2, 2, 1);

        Scene scene = new Scene(gridPane, 300, 150);
        setScene(scene);
        show();
    }
}
