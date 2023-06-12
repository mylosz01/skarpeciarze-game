package com.skarpeta.skarpeciarzegame.app;

import com.skarpeta.skarpeciarzegame.network.Client;
import com.skarpeta.skarpeciarzegame.network.Server;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClosingGamePopup extends Application {
    Server server;
    Client clientThread;

    public ClosingGamePopup(Server server, Client clientThread) {
        this.server = server;
        this.clientThread = clientThread;
    }

    @Override
    public void start(Stage ignore) {

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closing Game");
        alert.setHeaderText("Do you want to close the server as well?");
        alert.setContentText("Choose your option.");

        ButtonType closeClientButton = new ButtonType("Close Client Only");
        ButtonType closeBothButton = new ButtonType("Close Both");

        alert.getButtonTypes().setAll(closeClientButton, closeBothButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == closeClientButton) {
                closeClientOnly();
            } else if (buttonType == closeBothButton) {
                closeBoth();
            }
        });
    }

    private void closeClientOnly() {
        System.out.println("Closing Client Only");

        Catana.clientThread.leaveGame();
    }

    private void closeBoth() {
        System.out.println("Closing Both Client and Server");

        server.stopServer();
        Catana.clientThread.leaveGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
