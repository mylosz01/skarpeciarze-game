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
    
    opens com.skarpeta.skarpeciarzegame to javafx.fxml;
    exports com.skarpeta.skarpeciarzegame;
}