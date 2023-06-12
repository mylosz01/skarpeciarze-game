package com.skarpeta.skarpeciarzegame.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JoinMenu extends Stage {
    private Label exceptionLabel;
    private Button connectButton;
    private TextField ipField;
    private TextField nicknameField;
    private TextField portField;

    public Label getExceptionLabel() {return exceptionLabel;}
    public Button getConnectButton() {return connectButton;}
    public TextField getIpField() {return ipField;}
    public TextField getPortField() {return portField;}
    public TextField getNicknameField() {return nicknameField;}

    public JoinMenu() {
        setTitle("Join server");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label ipLabel = new Label("IP:");
        ipField = new TextField();
        ipField.setText("127.0.0.1");
        gridPane.add(ipLabel, 0, 0);
        gridPane.add(ipField, 1, 0);

        Label portLabel = new Label("Port:");
        portField = new TextField();
        portField.setText("5555");
        gridPane.add(portLabel, 0, 1);
        gridPane.add(portField, 1, 1);

        Label nicknameLabel = new Label("Nickname:");
        nicknameField = new TextField();
        nicknameField.setText("Guest");
        gridPane.add(nicknameLabel, 0, 2);
        gridPane.add(nicknameField, 1, 2);

        connectButton = new Button("Connect");
        gridPane.add(connectButton, 0, 3, 3, 1);

        exceptionLabel = new Label();
        exceptionLabel.setTextFill(Color.RED);
        gridPane.add(exceptionLabel,0,4,2,4);

        Scene scene = new Scene(gridPane, 300, 150);
        setScene(scene);
        show();
    }

}
