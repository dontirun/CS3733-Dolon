package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by slafo on 4/10/2016.
 */
public class LevelBuilderController implements Initializable{
    @FXML
    public Button homeButton;
    @FXML
    public GridPane boardView;

    int rows = 12;
    int columns = 12;

    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) homeButton.getScene().getWindow(); // Gets button stage reference
        root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml")); // Get other FXML document

        // Create new scene with root and set stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardView.getStyleClass().add("board");

        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(45.8333333);
            boardView.getColumnConstraints().add(column);
        }

        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(45.8333333);
            boardView.getRowConstraints().add(row);
        }

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Pane pane = new Pane();
                pane.setMinSize(0, 0);
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, i, j);
            }
        }
    }
}
