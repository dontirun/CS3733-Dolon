package Boundaries;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.IOException;

import Game.*;


/**
 * Created by Arthur on 4/10/2016.
 */
public class MenuView {
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
    Button about;
    @FXML
    public GameMenu menu;

    public MenuView(){
        //this.menu = menu;
       //levelNumber.setText(Integer.toString(5));

    }

    public void initialize() {
        levelNumber.setText(Integer.toString(1));
    }

    public void handleAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource() == startButton){

            //menuToLevelController(levelNumber);
            //get reference to the button's stage
            stage=(Stage) startButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("../puzzleLevel.fxml"));
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
        else  if (event.getSource() == about){

        }
        else{

        }
        //create a new scene with root and set the stage

    }
}


