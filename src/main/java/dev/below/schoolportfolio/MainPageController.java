package dev.below.schoolportfolio;

import dev.below.schoolportfolio.entities.Pupil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static dev.below.schoolportfolio.App.IMAGES;
import static dev.below.schoolportfolio.App.DOCS;

public class MainPageController {
    public ImageView photoBox;
    public TableColumn documentsColumn;
    private int currentPupilIndex;
    private List<Pupil> pupils = new ArrayList<>();

    @FXML
    private TableView<Document> filesTable;
    @FXML
    public VBox pupilsList;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ComboBox<String> gradePicker;
    @FXML
    private ComboBox<String> subjectPicker;
    @FXML
    private GridPane pupilInfo;

    @FXML
    public void initialize() {
        // Обработчик клика на строку
        filesTable.setOnMouseClicked(this::handleRowClick);
    }

    private void handleRowClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { // Обработка двойного клика
            Document selectedDocument = filesTable.getSelectionModel().getSelectedItem();
            if (selectedDocument != null) {
                openFile(new File(selectedDocument.getDocument()));
            }
        }
    }

    public void deleteDocument(ActionEvent actionEvent) {
        Document selectedDocument = filesTable.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            Pupil currentPupil = getCurrentPupil();
            currentPupil.removeDocument(selectedDocument.getDocument());
            updateDocumentsList();
        }
    }

    private Pupil getCurrentPupil() {
        return pupils.get(currentPupilIndex);
    }

    @FXML
    public void createPupilButtonClicked() {
        Pupil pupil = new Pupil("Новый", "Ученик");
        pupils.add(pupil);

        updatePupilsList();
        setPupilInfo(pupil);
        showPupilInfo();
    }

    private Button getPupilButton(Pupil pupil) {
        Button pupilButton = new Button();
        pupilButton.setPrefWidth(320);
        pupilButton.setPrefHeight(28);
        pupilButton.setFont(javafx.scene.text.Font.font("Arial", 14));
        pupilButton.toFront();

        pupilButton.setText(pupil.getName() + " " + pupil.getSurname());

        pupilButton.setOnAction(event -> {
            setPupilInfo(pupil);
            currentPupilIndex = pupils.indexOf(pupil);
        });
        return pupilButton;
    }

    private void updatePupilsList() {
        pupilsList.getChildren().clear();
        for (Pupil pupil : pupils) {
            Button pupilButton = getPupilButton(pupil);
            pupilsList.getChildren().add(pupilButton);
        }
    }

    private void setPupilInfo(Pupil pupil) {
        nameTextField.setText(pupil.getName());
        surnameTextField.setText(pupil.getSurname());

        if (pupil.getBirthDate() != null) {
            birthDatePicker.setValue(pupil.getBirthDate());
        }

        if (pupil.getGrade() != null) {
            gradePicker.setValue(pupil.getGrade());
        }

        if (pupil.getFavouriteSubject() != null) {
            subjectPicker.setValue(pupil.getFavouriteSubject());
        }

        if (pupil.getPathToPhoto() != null) {
            photoBox.setImage(new javafx.scene.image.Image(pupil.getPathToPhoto()));
        } else {
            photoBox.setImage(null);
        }
    }

    private void showPupilInfo() {
        pupilInfo.setVisible(true);
    }

    private void hidePupilInfo() {
        pupilInfo.setVisible(false);
    }

    public void changePupilPhoto(ActionEvent actionEvent) throws IOException {
        String photoPath = openFileChooser();
        if (photoPath != null) {
            Path copiedFilePath = Files.copy(
                    Path.of(photoPath),
                    Path.of(IMAGES + File.separator + new File(photoPath).getName()),
                    StandardCopyOption.REPLACE_EXISTING);
            File copiedImage = copiedFilePath.toFile();
            photoBox.setImage(new javafx.scene.image.Image(copiedImage.toURI().toString()));
        }
    }

    private String openFileChooser() {
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

    public void deletePupil(ActionEvent actionEvent) {
        Pupil currentPupil = getCurrentPupil();
        pupils.remove(currentPupil);

        updatePupilsList();

        if (pupils.isEmpty()) {
            hidePupilInfo();
        } else {
            setPupilInfo(pupils.getLast());
            currentPupilIndex = pupils.size() - 1;
        }
    }

    public void savePupilInfo(ActionEvent actionEvent) {
        Pupil currentPupil = getCurrentPupil();
        currentPupil.setName(nameTextField.getText());
        currentPupil.setSurname(surnameTextField.getText());

        currentPupil.setBirthDate(birthDatePicker.getValue());

        String grade = gradePicker.getValue();
        if (grade != null && !grade.isEmpty()) {
            currentPupil.setGrade(grade);
            gradePicker.getItems().add(grade);
        }

        String subject = subjectPicker.getValue();
        if (subject != null && !subject.isEmpty()) {
            currentPupil.setFavouriteSubject(subject);
            subjectPicker.getItems().add(subject);
        }

        if (photoBox.getImage() != null) {
            currentPupil.setPathToPhoto(photoBox.getImage().getUrl());
        }

        updatePupilsList();
    }

    public void openFile(File file) {
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

    public void addDocument(ActionEvent actionEvent) throws IOException {
        Pupil currentPupil = getCurrentPupil();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите документ");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.docx", "*.doc"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) return;

        Path copiedFilePath = Files.copy(
                Path.of(selectedFile.getAbsolutePath()),
                Path.of(DOCS + File.separator + selectedFile.getName()),
                StandardCopyOption.REPLACE_EXISTING);
        File copiedFile = copiedFilePath.toFile();

        currentPupil.addDocument(copiedFile.getPath());

        updateDocumentsList();
    }

    private void updateDocumentsList() {
        Pupil currentPupil = getCurrentPupil();

        ObservableList<Document> data = FXCollections.observableArrayList();
        for (String document : currentPupil.getDocuments()) {
            data.add(new Document(document));
        }

        documentsColumn.setCellValueFactory(new PropertyValueFactory<>("document"));
        filesTable.setItems(data);
    }

    public static class Document {
        private final SimpleStringProperty document;

        public Document(String document) {
            this.document = new SimpleStringProperty(document);
        }

        public String getDocument() {
            return document.get();
        }

        public void setDocument(String document) {
            this.document.set(document);
        }
    }
}
