package BuilderControllers;

import BuilderModel.LevelModel;
import BuilderModel.ReleaseTile;
import UndoActionManager.IAction;
import UndoActionManager.ResizeAction;
import UndoActionManager.ResizeReleaseAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * Created by slafo on 4/10/2016.
 */
public class LevelBuilderController implements Initializable {
    BoardController boardController;
    LevelModel level;
    @FXML
    public Button greenButton; // Return to menu
    @FXML
    public Button redButton; // Return to menu
    @FXML
    public Button yellowButton; // Return to menu
    @FXML
    public Button blackButton; // Return to menu
    @FXML
    public Button whiteButton; // Return to menu
    @FXML
    public Button homeButton; // Return to menu
    @FXML
    public Button loadButton; // Return to menu
    @FXML
    public Button resetButton; // Return to menu
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

    public Color color;
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
     * called when reset button is clicked, resets all  elements on the levelbuilder based on type of level
     *
     * @param event
     */
    public void handleResetButtonAction(ActionEvent event) throws IOException {
        // try getting the level number
        System.out.println("reseting level");
        int i;
        try {
            i = Integer.parseInt(levelNumber.getText());
        }
        // can't reset if in initial state
        catch (Exception e) {
            return;
        }
        // helper functions
        resetBoard(i);
        resetFields(i);
        resetPieces();

    }

    public void resetBoard(int levelType) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // reset the visual aspect of the board
                tilePanes[j][i].setMinSize(0, 0);
                tilePanes[j][i].setStyle("-fx-background-color: white");
                //tilePanes[j][i].setStyle("-fx-border-color: black");
                tilePanes[j][i].getStyleClass().add("board-cell");

                // reset the underlying tiles for tile actions
                level.getTile(j, i).setExists(true);

                if (levelType == 3) {
                    // clear the release level specifc parameters
                    ((GridSquare)tilePanes[j][i]).getNumLabel().setText("");
                    ((ReleaseTile)level.getTile(j, i)).setColor(Color.WHITE);
                    boardController.redNumTiles.clear();
                    boardController.redNumPanes.clear();
                    boardController.greenNumTiles.clear();
                    boardController.greenNumPanes.clear();
                    boardController.yellowNumTiles.clear();
                    boardController.yellowNumPanes.clear();

                }


            }
        }

        //clear the undo redo stacks
        redoHistory.clear();
        undoHistory.clear();

        //set the text on rows and columns to reflect board
        rowsTextField.setText("12");
        rowsTextField.setText("12");

    }

    public void resetFields(int levelType) {
        //set the text on rows and columns to reflect board
        rowsTextField.setText("12");
        colsTextField.setText("12");

        switch (levelType) {
            case 1:
                movesRemainField.clear();
                break;
            case 2:
                timerField.clear();
                break;
        }
    }

    public void resetPieces() {
        // add this functionality in later

    }

    /**
     * called when load button is clicked, loads the correct elements on the levelbuilder based on type of level and if
     * there is already a saved levels loads that information
     *
     * @param event
     */

    // need to add actually loading functionality
    public void handleLoadButtonAction(ActionEvent event) throws IOException {
        // only do loading things if the textfield has a valid value
        if (handleLevelChanged()) {
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
        switch (num % 3) {
            case 0:
                level = new LevelModel("release");
        }

        loadLevel(num);
    }

    /**
     * called when mouse clicks on board, inverts the validity of the tile clicked and updates view
     *
     * @param event
     */
    public void handleBoardClicked(MouseEvent event) {


        boardController.handleBoardClicked(event);

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
        if(level.getMode()!="release"){
            ResizeAction ra = new ResizeAction(level.getBoardTiles(), tilePanes, colsTextField, rowsTextField);
            if (ra.doAction()) {
                System.out.println("resize action performed");
                undoHistory.push(ra);
                redoHistory.clear();
            }


        }else{
            ResizeReleaseAction rla = new ResizeReleaseAction(level.getBoardTiles(), tilePanes, colsTextField, rowsTextField);
            if (rla.doAction()) {
                System.out.println("resize action performed");
                undoHistory.push(rla);
                redoHistory.clear();
            }

        }



    }

    public void handleUndo() {
        System.out.println("undo button clicked");
        if (!undoHistory.empty()) {
            IAction i = undoHistory.pop();
            i.undoAction();
            redoHistory.push(i);
        }
    }

    public void handleRedo() {
        System.out.println("redo button clicked");
        if (!redoHistory.empty()) {
            IAction i = redoHistory.pop();
            i.redoAction();
            undoHistory.push(i);
        }
    }

    public void changeColor(ActionEvent ae) {

        if (ae.getSource() == redButton) {
            color = Color.RED;
        }
        if (ae.getSource() == greenButton) {
            color = Color.GREEN;
        }
        if (ae.getSource() == yellowButton) {
            color = Color.YELLOW;
        }
        if (ae.getSource() == whiteButton) {
            color = Color.WHITE;
        }
        if (ae.getSource() == blackButton) {
            color = Color.BLACK;
        }
        System.out.println("color changed to " + color.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        color = Color.BLACK;
        boardController = new BoardController(this);
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
                GridSquare pane = new GridSquare();

                pane.setMinSize(0, 0);
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, i, j);

                tilePanes[i][j] = pane;

            }
        }
    }


    // Level parsing function
    public void loadLevel(int levelNum) throws IOException {

        // Variables for level information
        // NOTE: will most likely be moved to more global variables
        int lvNum = 0;
        int metric = 0;
        ArrayList<Integer> pieces = new ArrayList<Integer>();
        ArrayList<String> tiles = new ArrayList<String>();

        // Starts at 0 because file begins with ### and will automatically increment
        int readCount = 0; // Determines what part of the files is being parsed

        try {
            // Parsing objects
            // Get filepath for the right level, and then load it in
            String filepath = "DolonKabasuji/resources/levels/lvl" + levelNum + ".bdsm";
            FileReader input = new FileReader(filepath); // Read in file
            BufferedReader buf = new BufferedReader(input);
            String dataLine;

            // Parse file
            while ((dataLine = buf.readLine()) != null) {
                if (dataLine.contains("###")) { // Go to next section
                    readCount++;
                } else { // Information to parse
                    switch (readCount) {
                        case 1: // Level Number
                            lvNum = Integer.parseInt(dataLine);
                            break;
                        case 2: // Metric
                            metric = Integer.parseInt(dataLine);
                            break;
                        case 3: // Pieces
                            pieces.add(Integer.parseInt(dataLine));
                            break;
                        case 4: // Tiles
                            tiles.add(dataLine);
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Set level label
        levelNumber.setText(Integer.toString(lvNum));

        // Set metric
        switch(lvNum%3){
            case 1:
                movesRemainField.setText(Integer.toString(metric));
                break;
            case 2:
                timerField.setText(Integer.toString(metric));
                break;
        }

        /*
        // Set the pieces given for the board
        ourPieceFactory = new PieceFactory(); // Generate pieceFactory
        for(int i: pieces){
            generateShapeFromPiece(ourPieceFactory.getPiece(i));
        }
        */

        // Set the specific tiles of the board (non-square board shapes)
        int count = 0;
        for (String s : tiles) {
            // Break up lines and convert to int
            String[] tileLines = s.split(" ");
            int[] tileInts = new int[tileLines.length];

            for (int i = 0; i < tileLines.length; i++) {
                tileInts[i] = Integer.parseInt(tileLines[i]);
            }

            // Set values
            for (int i = 0; i < columns; i++) {
                // Determine what type of tile needs to be set
                if (tileInts[i] == 0) { // No-Tile
                    level.getBoardTiles()[i][count].setExists(false);
                    tilePanes[i][count].setStyle("-fx-background-color: black");
                    //tilePanes[i][count].setStyle("-fx-border-color: black");
                } else if (tileInts[i] == 1) { // Valid blank tile
                    level.getBoardTiles()[i][count].setExists(true);
                    tilePanes[i][count].setStyle("-fx-background-color: white");
                    //tilePanes[i][count].setStyle("-fx-border-color: black");
                } else if (tileInts[i] > 20 && tileInts[i] < 27) { // Red release tile: 21-26 indicate the number on the tile.
                    //
                } else if (tileInts[i] > 30 && tileInts[i] < 37) { // Green release tile: 31-36 indicate the number on the tile.
                    //
                } else if (tileInts[i] > 40 && tileInts[i] < 47) { // Yellow release tile: 41-46 indicate the number on the tile.
                    //
                }
            }

            // Increment count
            count++;
        }
    }
}

