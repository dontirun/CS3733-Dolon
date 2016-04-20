package BuilderControllers;

import BuilderModel.LevelModel;
import UndoActionManager.IAction;
import UndoActionManager.ResizeAction;
import UndoActionManager.TileAction;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * Created by slafo on 4/10/2016.
 */
public class LevelBuilderController implements Initializable {
    LevelModel level;
    @FXML
    public Button greenButton; // Return to menu
    @FXML
    public Button redButton; // Return to menu
    @FXML
    public Button yellowButton; // Return to menu
    @FXML
    public Button homeButton; // Return to menu
    @FXML
    public Button loadButton; // Return to menu
    @FXML
    public GridPane boardView; // Pane for board
    @FXML
    public TextField rowsTextField;
    @FXML
    public TextField colsTextField;
    @FXML
    public TextField levelTextField;
    @FXML
    public TextField timerField;
    @FXML
    public TextField movesRemainField;
    @FXML
    public Label levelNumber;
    @FXML
    public Label timerandmovesLabel;
    @FXML
    public ImageView typeImage;
    //contains references to all the panes added to boardView
    Pane[][] tilePanes;
    // Max row and column size
    int rows = 12;
    int columns = 12;
    Stack<IAction> undoHistory;
    Stack<IAction> redoHistory;


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

    /**
     * called when load button is clicked, loads the correct elements on the levelbuilder based on type of level and if
     * there is already a saved levels loads that information
     *
     * @param event
     */

    // need to add actually loading functionality
    public void handleLoadButtonAction(ActionEvent event) throws IOException{
        // only do loading things if the textfield has a valid value
        if(handleLevelChanged()){
            // change the elements to match the correct level type
            levelNumber.setText(levelTextField.getText());

        }

        int num = Integer.parseInt(levelTextField.getText());
        // initialize elements for correct level type
        //Need to add actual loading stuff here as well
        switch (num % 3) {
            case 1:
                // puzzle
                // setting buttons we don't want to not visible and not managed
                timerField.setVisible(false);
                timerField.setManaged(false);
                redButton.setVisible(false);
                redButton.setManaged(false);
                greenButton.setVisible(false);
                greenButton.setManaged(false);
                yellowButton.setVisible(false);
                yellowButton.setManaged(false);

                movesRemainField.setVisible(true);
                movesRemainField.setManaged(true);
                movesRemainField.clear();
                Image puz = new Image("/images/PuzzleIcon.png");
                typeImage.setImage(puz);
                timerandmovesLabel.setText("Moves");
                timerandmovesLabel.setVisible(true);
                typeImage.setVisible(true);

                break;
            case 2:
                //lightning
                //setting buttons we don't want to not visible and not managed
                movesRemainField.setVisible(false);
                movesRemainField.setManaged(false);
                redButton.setVisible(false);
                redButton.setManaged(false);
                greenButton.setVisible(false);
                greenButton.setManaged(false);
                yellowButton.setVisible(false);
                yellowButton.setManaged(false);
                typeImage.setVisible(false);


                timerField.setVisible(true);
                timerField.setManaged(true);
                timerField.clear();
                timerandmovesLabel.setText("Timer");
                timerandmovesLabel.setVisible(true);
                Image light = new Image("/images/lightning.png");
                typeImage.setImage(light);
                typeImage.setVisible(true);


                break;
            case 0:
                //release
                timerField.setVisible(false);
                timerField.setManaged(false);
                movesRemainField.setVisible(false);
                movesRemainField.setManaged(false);
                timerandmovesLabel.setVisible(false);
                redButton.setVisible(true);
                redButton.setManaged(true);
                greenButton.setVisible(true);
                greenButton.setManaged(true);
                yellowButton.setVisible(true);
                yellowButton.setManaged(true);
                typeImage.setVisible(false);

                Image rel = new Image("/images/ReleaseIcon.png");
                typeImage.setImage(rel);
                typeImage.setVisible(true);

                break;

        }
    }

    /**
     * called when mouse clicks on board, inverts the validity of the tile clicked and updates view
     *
     * @param event
     */
    public void handleBoardClicked(MouseEvent event) {
        //get x and y mouse coordinates
        double x = event.getX();
        double y = event.getY();
        //find column and row of tile clicked
        int col = (int) (x / 45.8333333);
        int row = (int) (y / 45.8333333);

        TileAction ta = new TileAction(level.getTile(col, row), tilePanes[col][row]);
        if (ta.doAction()) {
            System.out.println("board click action performed");
            undoHistory.push(ta);
            redoHistory.clear();
        }
    }

    /**
     * Checks if rows input is valid and changes border color to reflect it
     *
     * @return true if rows has integer input between 1 and 12, false otherwise
     */
    public boolean handleRowsChanged() {
        try {
            int inputRows = Integer.parseInt(rowsTextField.getText().trim());
            rowsTextField.setStyle("-fx-border-color: black");
            if (inputRows < 0 || inputRows > 12) {
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

    /**
     * Checks if cols input is valid and changes border color to reflect it
     *
     * @return true if cols has integer input between 1 and 12, false otherwise
     */
    public boolean handleColsChanged() {
        try {
            int inputCols = Integer.parseInt(colsTextField.getText().trim());
            colsTextField.setStyle("-fx-border-color: black");
            if (inputCols < 0 || inputCols > 12) {
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

    /**
     * Checks if level input is valid and changes border color to reflect it
     *
     * @return true if cols has integer input between 1 and 12, false otherwise
     */
    public boolean handleLevelChanged() {
        try {
            int inputCols = Integer.parseInt(levelTextField.getText().trim());
            levelTextField.setStyle("-fx-border-color: black");
            if (inputCols < 1 || inputCols > 15) {
                levelTextField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (Exception e) {
            levelTextField.setStyle("-fx-border-color: red");
            return false;
        }
        System.out.println("level changed to new value");
        return true;
    }

    /**
     * Checks if level input is valid and changes border color to reflect it
     *
     * @return true if cols has integer input between 1 and 12, false otherwise
     */
    public boolean handleTimerChanged() {
        try {
            int inputCols = Integer.parseInt(timerField.getText().trim());
            timerField.setStyle("-fx-border-color: black");
            if (inputCols < 0) {
                timerField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (Exception e) {
            timerField.setStyle("-fx-border-color: red");
            return false;
        }
        System.out.println("level changed to new value");
        return true;
    }

    /**
     * Checks if moves input is valid and changes border color to reflect it
     *
     * @return true if cols has integer input between 1 and 12, false otherwise
     */
    public boolean handleMovesChanged() {
        try {
            int inputCols = Integer.parseInt(movesRemainField.getText().trim());
            movesRemainField.setStyle("-fx-border-color: black");
            if (inputCols < 0) {
                movesRemainField.setStyle("-fx-border-color: red");
                return false;
            }
        } catch (Exception e) {
            movesRemainField.setStyle("-fx-border-color: red");
            return false;
        }
        System.out.println("level changed to new value");
        return true;
    }

    /**
     * makes a rectangular area of tiles valid according to user input into rows and cols textFields
     *
     * @param event
     */
    public void handleResizeButton(ActionEvent event) {
        ResizeAction ra = new ResizeAction(level.getBoardTiles(),tilePanes, colsTextField, rowsTextField);
        if (ra.doAction()) {
            System.out.println("resize action performed");
            undoHistory.push(ra);
            redoHistory.clear();
        }

    }

    public void handleUndo(){
        System.out.println("undo button clicked");
        IAction i = undoHistory.pop();
        i.undoAction();
        redoHistory.push(i);
    }
    public void handleRedo(){
        System.out.println("redo button clicked");
        IAction i = redoHistory.pop();
        i.redoAction();
        undoHistory.push(i);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        level = new LevelModel();
        boardView.getStyleClass().add("board");
        undoHistory = new Stack<IAction>();
        redoHistory = new Stack<IAction>();

        timerandmovesLabel.setVisible(false);
        timerField.setVisible(false);
        timerField.setManaged(false);
        movesRemainField.setVisible(false);
        movesRemainField.setManaged(false);
        redButton.setVisible(false);
        redButton.setManaged(false);
        greenButton.setVisible(false);
        greenButton.setManaged(false);
        yellowButton.setVisible(false);
        yellowButton.setManaged(false);
        typeImage.setVisible(false);

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
        levelTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleLevelChanged();
            }
        });
        timerField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleTimerChanged();
            }
        });
        movesRemainField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleMovesChanged();
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
