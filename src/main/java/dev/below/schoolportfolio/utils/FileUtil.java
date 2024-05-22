package dev.below.schoolportfolio.utils;

import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

public class FileUtil {
    public static void openFile(File file) {
        try {
            if (!Desktop.isDesktopSupported()) return;

            Desktop desktop = Desktop.getDesktop();

            if (file.exists()) {
                desktop.open(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите фото ученика");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {
            return selectedFile.getPath();
        } else {
            return null;
        }
    }
}
