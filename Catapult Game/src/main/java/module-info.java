module com.example.catapult_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.catapult_game to javafx.fxml;
    exports com.example.catapult_game;
}