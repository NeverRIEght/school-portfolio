package dev.below.schoolportfolio.controller;

import dev.below.schoolportfolio.entities.Document;
import dev.below.schoolportfolio.entities.Pupil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import static dev.below.schoolportfolio.utils.FileUtil.openFile;
import static dev.below.schoolportfolio.utils.FileUtil.openFileChooser;

public class MainPageController {
    private final List<Pupil> pupils = new ArrayList<>();
    private int currentPupilIndex;

    @FXML
    public VBox pupilsList;

    @FXML
    private GridPane pupilInfo;

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
    public ImageView photoBox;

    @FXML
    private TableView<Document> filesTable;
    @FXML
    public TableColumn documentsColumn;

    @FXML
    public void initialize() {
        filesTable.setOnMouseClicked(this::filesTableRowClick);
    }

    @FXML
    public void createPupilButtonClicked() {
        Pupil pupil = new Pupil("Новый", "Ученик");
        pupils.add(pupil);

        updatePupilsList();
        setPupilInfo(pupil);
        currentPupilIndex = pupils.size() - 1;
        showPupilInfo();
    }

    private Pupil getCurrentPupil() {
        return pupils.get(currentPupilIndex);
    }

    private void updatePupilsList() {
        pupilsList.getChildren().clear();
        for (Pupil pupil : pupils) {
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

    public void changePupilPhoto() throws IOException {
        File currentImage = new File(photoBox.getImage().getUrl());
        if (currentImage.exists()) {
            currentImage.delete();
        }

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

    public void addDocument() throws IOException {
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

    private void filesTableRowClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Document selectedDocument = filesTable.getSelectionModel().getSelectedItem();
            if (selectedDocument != null) {
                openFile(new File(selectedDocument.getDocument()));
            }
        }
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

    public void deleteDocument() {
        Document selectedDocument = filesTable.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            Pupil currentPupil = getCurrentPupil();
            currentPupil.removeDocument(selectedDocument.getDocument());
            if (new File(selectedDocument.getDocument()).exists()) {
                new File(selectedDocument.getDocument()).delete();
            }
            updateDocumentsList();
        }
    }

    public void savePupilInfo() {
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

    public void deletePupil() {
        Pupil currentPupil = getCurrentPupil();
        pupils.remove(currentPupil);

        updatePupilsList();

        if (pupils.isEmpty()) {
            hidePupilInfo();
        } else {
            setPupilInfo(pupils.getLast());
            currentPupilIndex = pupils.size() - 1;
        }

        if (new File(currentPupil.getPathToPhoto()).exists()) {
            new File(currentPupil.getPathToPhoto()).delete();
        }

        for (String document : currentPupil.getDocuments()) {
            if (new File(document).exists()) {
                new File(document).delete();
            }
        }
    }
}
