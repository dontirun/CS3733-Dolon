package Model;

/**
 * Created by Arthur on 4/10/2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class LevelView {
    @FXML
    Button backLevel;
    @FXML
    Button forwardLevel;
    @FXML
    Button menu;
    @FXML
    Label levelNumber;
    @FXML
    Pane bv;
    @FXML
    FlowPane bp;
    @FXML
    Level l;
    @FXML
    ImageView levelIcon;
    @FXML
    ImageView backArrow;
    @FXML
    ImageView forwardArrow;
    @FXML
    ImageView firstStar;
    @FXML
    ImageView secondStar;
    @FXML
    ImageView thirdStar;
    @FXML
    ImageView homeIcon;

    public LevelView(){
        //this.l = l;
    }

    public void initialize() {
        levelNumber.setText(Integer.toString(1));
    }

    public void handleAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource() == menu){

            //menuToLevelController(levelNumber);
            //get reference to the button's stage
            stage=(Stage) menu.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else{
            //
        }
        //create a new scene with root and set the stage

    }

}
