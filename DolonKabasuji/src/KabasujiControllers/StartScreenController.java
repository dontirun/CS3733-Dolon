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
public class StartScreenController {
    @FXML
    Button startButton;
    @FXML
    Label name;
    @FXML
    Label levelNumberLabel;
    @FXML
    Button decrementLevel;
    @FXML
    Button incrementLevel;
    @FXML
    Button aboutButton;
    @FXML
    ImageView leftArrow;
    @FXML
    ImageView rightArrow;
    @FXML
    ImageView lockIcon;


    // this might need to change
    static GameMenu menu;





    public StartScreenController() {
    }

    @FXML
    public void initialize(){
        if (this.menu == null) {
            this.menu = new GameMenu();
        }

        if(menu.getLevelNumber() > menu.getUnlocked()){
            lockIcon.setVisible(true);
        }
        else{
            lockIcon.setVisible(false);
        }

        levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));
    }

    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;


        if (event.getSource() == startButton) {
            //get reference to the button's stage
            stage = (Stage) startButton.getScene().getWindow();
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
        else if (event.getSource() == decrementLevel) {
            //System.out.println(menu.getLevelNumber());
            menu.decrementLevelNumber();
            stage = (Stage) decrementLevel.getScene().getWindow();
            //System.out.println(menu.getLevelNumber());
            levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));

            if(menu.getLevelNumber() > menu.getUnlocked()){
                lockIcon.setVisible(true);
            }
            else{
                lockIcon.setVisible(false);
            }

        }
        else if (event.getSource() == incrementLevel) {
         //   System.out.println(menu.getLevelNumber());
            menu.incrementLevelNumber();
            stage = (Stage) incrementLevel.getScene().getWindow();
          //  System.out.println(menu.getLevelNumber());
            levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));

            if(menu.getLevelNumber() > menu.getUnlocked()){
                lockIcon.setVisible(true);
            }
            else{
                lockIcon.setVisible(false);
            }
        }
        else if (event.getSource() == aboutButton) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/views/about.fxml")); // Get other FXML document
            stage.setScene(new Scene(root));
            stage.setTitle("About Kabasuji");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(aboutButton.getScene().getWindow());
            stage.showAndWait();
        }
        else {

        }
        //create a new scene with root and set the stage

    }
}

