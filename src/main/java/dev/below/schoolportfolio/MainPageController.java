package dev.below.schoolportfolio;

import dev.below.schoolportfolio.entities.Pupil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainPageController {
    public VBox pupilsList;
    @FXML
    private Button createPupilButton;

    @FXML
    public void createPupilButtonClicked() {
        Pupil newPupil = new Pupil("New", "Pupil");
        Button pupilButton = getPupilButton(newPupil);

        pupilsList.getChildren().add(pupilButton);
    }

    private Button getPupilButton(Pupil pupil) {
        Button pupilButton = new Button();
        pupilButton.setPrefWidth(320);
        pupilButton.setPrefHeight(28);
        pupilButton.setFont(javafx.scene.text.Font.font("Arial", 14));
        pupilButton.toFront();

        pupilButton.setText(pupil.getName() + " " + pupil.getSurname());
        return pupilButton;
    }

//    @FXML
//    private Button btnRepeatable;
//    @FXML
//    private Button btnAddImage1;
//    @FXML
//    private Button btnAddImage2;
//    @FXML
//    private ImageView imageViewAddImage1;
//    @FXML
//    private ImageView imageViewAddImage2;
//    @FXML
//    private VBox questsVBox;
//    private String imagePath1 = "";
//    private String imagePath2 = "";
//
//    @FXML
//    protected void onMouseClicked() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(new File("D:\\Java\\apex-legends-battle-pass-helper\\src\\main\\java\\org\\apexlegendsbphelper\\View\\add-quests-page.fxml").toURI().toURL());
//        Scene scene = new Scene(fxmlLoader.load(), 850, 478);
//
//        Stage window = (Stage) btnRepeatable.getScene().getWindow();
//        window.setScene(scene);
//        btnRepeatable.setText("Clicked!");
//    }
//    @FXML
//    protected void btnClickLoadImage(ActionEvent event) {
//        Button clickedButton = (Button) event.getSource();
//        String buttonId = clickedButton.getId();
//
//        String pathToWeekImage = pathFixer(openFileChooser());
//        if(pathToWeekImage != null) {
//            Image weekImage = new Image(new File(pathToWeekImage).toURI().toString());
//            switch (buttonId) {
//                case "btnAddImage1" -> {
//                    imagePath1 = pathToWeekImage;
//                    btnAddImage1.setVisible(false);
//                    imageViewAddImage1.setImage(weekImage);
//                    imageViewAddImage1.setVisible(true);
//                }
//                case "btnAddImage2" -> {
//                    imagePath2 = pathToWeekImage;
//                    btnAddImage2.setVisible(false);
//                    imageViewAddImage2.setImage(weekImage);
//                    imageViewAddImage2.setVisible(true);
//                }
//            }
//        }
//    }
//
//    @FXML
//    protected void btnClickResetImages() {
//        btnAddImage1.setVisible(true);
//        imageViewAddImage1.setVisible(false);
//        btnAddImage2.setVisible(true);
//        imageViewAddImage2.setVisible(false);
//    }
//
//    @FXML
//    protected void btnClickScanImages() throws TesseractException, IOException {
//        Quest[] quests = processWeekImages(imagePath1, imagePath2);
//        if(quests != null) {
//            for(Quest quest : quests) {
//                Button questButton = new Button();
//                questButton.setText(quest.getQuestNameBR());
//                questButton.setWrapText(true);
//                questButton.setStyle("-fx-background-color: #0E0E0E; -fx-text-fill: white");
//                questButton.setMinHeight(24);
//                questButton.toFront();
//                questsVBox.getChildren().add(questButton);
//            }
//        }
//    }
}
