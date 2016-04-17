package BuilderControllers;

import BuilderModel.LevelModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
public class LevelBuilderController implements Initializable {
    LevelModel level;
    @FXML
    public Button homeButton; // Return to menu
    @FXML
    public GridPane boardView; // Pane for board
    @FXML
    public TextField rowsTextField;
    @FXML
    public TextField colsTextField;

    Pane[][] tilePanes;
    // Max row and column size
    int rows = 12;
    int columns = 12;

    public void handleHomeButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) homeButton.getScene().getWindow(); // Gets button stage reference
        root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml")); // Get other FXML document

        // Create new scene with root and set stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleBoardClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        System.out.println(x + ", " + y);
        //find column and row of tile clicked
        int col = (int) (x / 45.8333333);
        int row = (int) (y / 45.8333333);
        level.flipValid(col, row);
        if (level.getValid(col, row) == true) {
            tilePanes[col][row].setStyle("-fx-background-color: white");
            tilePanes[col][row].setStyle("-fx-border-color: black");
        } else {
            tilePanes[col][row].setStyle("-fx-background-color: black");

        }
    }

    public boolean handleRowsChanged() {
        try {
            int inputRows = Integer.parseInt(rowsTextField.getText().trim());
            rowsTextField.setStyle("-fx-border-color: black");
            if (inputRows < 1 || inputRows > 12) {
                rowsTextField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (Exception e) {
            rowsTextField.setStyle("-fx-border-color: red");
            return false;
        }
        System.out.println("size changed to new value");
        return true;
    }

    public boolean handleColsChanged() {
        try {
            int inputCols = Integer.parseInt(colsTextField.getText().trim());
            colsTextField.setStyle("-fx-border-color: black");
            if (inputCols < 1 || inputCols > 12) {
                colsTextField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (Exception e) {
            colsTextField.setStyle("-fx-border-color: red");
            return false;
        }
        System.out.println("size changed to new value");
        return true;
    }

    public void handleResizeButton(ActionEvent event) {
        //check for valid entries just incase
        if (handleColsChanged() && handleRowsChanged()) {
            int inputRows = Integer.parseInt(rowsTextField.getText().trim());
            int inputCols = Integer.parseInt(colsTextField.getText().trim());
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    if (i < inputCols && j < inputRows) {
                        level.makeValid(i, j);
                        tilePanes[i][j].setStyle("-fx-background-color: white");
                        tilePanes[i][j].setStyle("-fx-border-color: black");
                    } else {
                        level.makeInvalid(i, j);
                        tilePanes[i][j].setStyle("-fx-background-color: black");
                    }
                }
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        level = new LevelModel();
        boardView.getStyleClass().add("board");

        rowsTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleRowsChanged();
            }
        });
        colsTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleColsChanged();
            }
        });
        for (int i = 0; i < columns; i++) {
            // Set constraints
            ColumnConstraints column = new ColumnConstraints(45.8333333);
            boardView.getColumnConstraints().add(column);
        }

        for (int i = 0; i < rows; i++) {
            // Set constraints
            RowConstraints row = new RowConstraints(45.8333333);
            boardView.getRowConstraints().add(row);
        }

        // Draw grid on board
        tilePanes = new Pane[columns][rows];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Pane pane = new Pane();

                pane.setMinSize(0, 0);
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, i, j);
                tilePanes[i][j] = pane;
            }
        }
    }
}
