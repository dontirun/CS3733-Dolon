package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.Button;


import java.io.IOException;

/**
 * Created by Arthur on 4/10/2016.
 */
public class GeneralController {
    Button menuStartButton;
    Button levelHomeButton;


    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if(event.getSource() == menuStartButton){
            //get reference to the button's stage
            stage=(Stage) menuStartButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("/views/puzzleLevel.fxml"));
        }
        else{
            stage=(Stage) levelHomeButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
