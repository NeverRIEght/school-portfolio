module dev.below.schoolportfolio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dev.below.schoolportfolio to javafx.fxml;
    exports dev.below.schoolportfolio;
}