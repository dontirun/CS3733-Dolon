package Controllers;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.Button;

/**
 * Created by Arthur on 4/11/2016.
 */
public class menuToLevelController {
    public menuToLevelController(Label levelNumber){
        String ourLevel = levelNumber.getText();
        int levelInt = Integer.parseInt(ourLevel);
        switch (levelInt % 3) {
            case 1: //Puzzle Level
                //Load Puzzle Level
                break;
            case 2: //Lightning Level
                //Load Lightning Level
                break;
            case 0: //Release Level
                //Load Release Level
                break;
        }
    }
}
