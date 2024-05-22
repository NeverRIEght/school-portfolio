package dev.below.schoolportfolio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class App extends Application {
    public static final String ROOT = Paths.get("").toAbsolutePath() + "";
    public static final String DOCS = ROOT + File.separator + "docs";
    public static final String IMAGES = ROOT + File.separator + "images";

    @Override
    public void start(Stage stage) throws IOException {
        createDirectories();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("School Portfolio");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void createDirectories() {
        new File(DOCS).mkdirs();
        new File(IMAGES).mkdirs();
    }
}