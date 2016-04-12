package KabasujiControllers;

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

    int levelNumber;



    public StartScreenController() {
    }

    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;


        if (event.getSource() == startButton) {
            //get reference to the button's stage
            stage = (Stage) startButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("/views/puzzleLevel.fxml"));

            // Create new scene with root and set stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == leftArrow) {
            levelNumber--;

        } else if (event.getSource() == rightArrow) {
            //decrementLevelController(levelNumber);
        } else if (event.getSource() == aboutButton) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/views/about.fxml")); // Get other FXML document
            stage.setScene(new Scene(root));
            stage.setTitle("About Kabasuji");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(aboutButton.getScene().getWindow());
            stage.showAndWait();
        } else {

        }
        //create a new scene with root and set the stage

    }
}

