module com.skarpeta.skarpeciarzegame {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;

    opens com.skarpeta.skarpeciarzegame to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame;
    exports com.skarpeta.skarpeciarzegame.buildings;
    opens com.skarpeta.skarpeciarzegame.buildings to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame.resources;
    opens com.skarpeta.skarpeciarzegame.resources to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame.inventory;
    opens com.skarpeta.skarpeciarzegame.inventory to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame.tools;
    opens com.skarpeta.skarpeciarzegame.tools to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame.network;
    opens com.skarpeta.skarpeciarzegame.network to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame.app;
    opens com.skarpeta.skarpeciarzegame.app to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame.worldmap;
    opens com.skarpeta.skarpeciarzegame.worldmap to javafx.fxml;
}