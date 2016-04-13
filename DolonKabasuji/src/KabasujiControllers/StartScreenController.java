package KabasujiControllers;

import KabasujiModel.GameMenu;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.IOException;


/**
 * Created by Arthur on 4/10/2016.
 */
public class StartScreenController{
    @FXML
    Button startKButton;
    @FXML
    Label name;
    @FXML
    Label levelNumberLabel;
    @FXML
    Button decrementLevel;
    @FXML
    Button incrementLevel;
    @FXML
    Button aboutKButton;
    @FXML
    ImageView leftArrow;
    @FXML
    ImageView rightArrow;
    @FXML
    ImageView lockIcon;


    // this might need to change
    static GameMenu menu;




    // Constructor
    public StartScreenController() {
    }

    // run every time this view loads
    @FXML
    public void initialize() {
        if (this.menu == null) {
            this.menu = new GameMenu();
        }

        // Initializes level accessibility on load
        if(menu.getLevelNumber() > menu.getUnlocked()){
           lockIcon.setVisible(true);
        }
        else{
           lockIcon.setVisible(false);
        }

        levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));
    }

    // checks the event sent out by a certain button
    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        // Start selected level
        if (event.getSource() == startKButton) {
            //get reference to the button's stage
            stage = (Stage) startKButton.getScene().getWindow();
            //load up OTHER FXML document
            if(lockIcon.isVisible() == false) {
                root = null;
                switch (menu.getLevelNumber() % 3) {
                    case 1:
                        root = FXMLLoader.load(getClass().getResource("/views/puzzleLevel.fxml"));
                        break;
                    case 2:
                        // change to lightning later
                        root = FXMLLoader.load(getClass().getResource("/views/puzzleLevel.fxml"));
                        break;
                    case 0:
                        // change to release later
                        root = FXMLLoader.load(getClass().getResource("/views/puzzleLevel.fxml"));
                        break;

                }
                // Create new scene with root and set stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        // Select previous level (or loop to max level)
        else if (event.getSource() == decrementLevel) {
            menu.decrementLevelNumber();
            //stage = (Stage) decrementLevel.getScene().getWindow();
            levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));

            if(menu.getLevelNumber() > menu.getUnlocked()){
                lockIcon.setVisible(true);
            }
            else{
                lockIcon.setVisible(false);
            }

        }
        // Select next level (or loop to first level)
        else if (event.getSource() == incrementLevel) {
            menu.incrementLevelNumber();
            //stage = (Stage) incrementLevel.getScene().getWindow();
            levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));

            if(menu.getLevelNumber() > menu.getUnlocked()){
                lockIcon.setVisible(true);
            }
            else{
                lockIcon.setVisible(false);
            }
        }
        // Open about window
        else if (event.getSource() == aboutKButton) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/views/about.fxml")); // Get other FXML document
            stage.setScene(new Scene(root));
            stage.setTitle("About Kabasuji");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(aboutKButton.getScene().getWindow());
            stage.showAndWait();
        }
        else {
            //
        }
        //create a new scene with root and set the stage

    }
}

