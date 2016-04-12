package Controllers;

import Model.GameMenu;
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
public class KabasujiStartScreenController {
    @FXML
    Button startButton;
    @FXML
    Label name;
    @FXML
    Label levelNumber;
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
    public GameMenu menu;


    public KabasujiStartScreenController(){
        //this.menu = menu;
       //levelNumber.setText(Integer.toString(5));

    }

    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource() == startButton){

            //menuToLevelController(levelNumber);
            //get reference to the button's stage
            stage=(Stage) startButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("/views/puzzleLevel.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else if (event.getSource() == incrementLevel){
            //incrementLevelController(levelNumber);

        }
        else  if (event.getSource() == decrementLevel){
            //decrementLevelController(levelNumber);

        }
        else  if (event.getSource() == aboutButton){
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/views/about.fxml")); // Get other FXML document
            stage.setScene(new Scene(root));
            stage.setTitle("About Kabasuji Builder");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(aboutButton.getScene().getWindow());
            stage.showAndWait();
        }
        else{

        }
        //create a new scene with root and set the stage

    }
}


