module dev.below.schoolportfolio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dev.below.schoolportfolio to javafx.fxml;
    exports dev.below.schoolportfolio;
    exports dev.below.schoolportfolio.controller;
    opens dev.below.schoolportfolio.controller to javafx.fxml;
}