package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by slafo on 4/10/2016.
 */
public class StartScreenController {
    @FXML
    public Button startButton;

    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) startButton.getScene().getWindow(); // Gets button stage reference
        root = FXMLLoader.load(getClass().getResource("/views/levelbuilder.fxml")); // Get other FXML document

        // Create new scene with root and set stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
