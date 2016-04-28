package BuilderControllers;

import BuilderModel.*;
import PieceFactory.*;
import UndoActionManager.AddPieceAction;
import UndoActionManager.IAction;
import UndoActionManager.AddPieceAction;
import UndoActionManager.ResizeAction;
import UndoActionManager.ResizeReleaseAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
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
    public Button rotateLeftButton;
    @FXML
    public Button rotateRightButton;
    @FXML
    public Button flipHorizontalButton;
    @FXML
    public Button flipVerticalButton;
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
    public Button addPieceButton;
    @FXML
    public GridPane boardView; // Pane for board
    @FXML
    public GridPane bullpenView;
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
    ArrayList<ArrayList<Pane>> tilePanes;
    // Max row and column size
    int rows = 12;
    int columns = 12;
    Stack<IAction> undoHistory;
    Stack<IAction> redoHistory;
    Bullpen bullpen = new Bullpen();
    int numberOfBullpenPieces;
    int gridW = 2;
    int gridH = 18;
    double RectangleSize = 45.83333333;
    PieceFactory ourPieceFactory = new PieceFactory(); // Generate pieceFactory


    /**
     * Handles returning to the home screen when the home button is pressed
     *
     * @param event
     * @throws IOException
     */
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
        resetButtons();
        resetBoard(i);
        resetFields(i);
        resetPieces();

    }

    /**
     * unsure what this does, maybe the buttons should have a better name?
     *
     */
    public void resetButtons(){
        // set black button to visually selected
        redButton.setStyle("-fx-background-color:transparent");
        greenButton.setStyle("-fx-background-color: transparent");
        yellowButton.setStyle("-fx-background-color: transparent");
        whiteButton.setStyle("-fx-background-color: transparent");
        blackButton.setStyle("-fx-background-color: darkblue");

        //black button is selected
        color = Color.BLACK;
    }

    /**
     * Resets the view of the board
     *
     * @param levelType
     */
    public void resetBoard(int levelType) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // reset the visual aspect of the board
                tilePanes.get(i).get(j).setMinSize(0, 0);
                tilePanes.get(i).get(j).setStyle("-fx-background-color: white");
                tilePanes.get(i).get(j).setStyle("-fx-border-color: black");
                tilePanes.get(i).get(j).getStyleClass().add("board-cell");

                // reset the underlying tiles for tile actions
                level.getTile(i, j).setExists(true);
                boardController.redNumTiles.clear();
                boardController.redNumPanes.clear();
                boardController.greenNumTiles.clear();
                boardController.greenNumPanes.clear();
                boardController.yellowNumTiles.clear();
                boardController.yellowNumPanes.clear();
                // clear the release level specifc parameters
                ((GridSquare)tilePanes.get(i).get(j)).getNumLabel().setText("");
                ((ReleaseTile)level.getTile(i, j)).setColor(Color.WHITE);

            }
        }

        //clear the undo redo stacks
        redoHistory.clear();
        undoHistory.clear();

        //set the text on rows and columns to reflect board
        rowsTextField.setText("12");
        rowsTextField.setText("12");

    }

    /**
     * Draws an individual rectangle for a given square (used for GUI elements)
     *
     * @param selectedSquare
     * @return
     */

    public Rectangle drawPieceRectangle(Square selectedSquare){
        Rectangle selectedRectangle = new Rectangle();
        selectedRectangle.setX((selectedSquare.getRelCol()) * RectangleSize); //Set X position based on the Relative Column
        selectedRectangle.setY((-selectedSquare.getRelRow()) * RectangleSize); //Set Y position based on the Relative Row
        selectedRectangle.setWidth(RectangleSize); //Set the width of each rectangle
        selectedRectangle.setHeight(RectangleSize); //Set the height of each rectangle
        selectedRectangle.setFill(Color.RED); //Color the fill
        selectedRectangle.setStroke(Color.BLACK); //Color the outline

        return selectedRectangle;
    }

    /**
     * Handles pressing a button to add a piece to the bullpen
     *
     * @param event
     * @throws IOException
     */
    public void handleAddPieceButtonAction(ActionEvent event) throws IOException {
        int numberOfPiecesDrawn = 0;
        // Set the pieces given for the board

        final Stage pieceSelector = new Stage();
        ScrollPane gridScroll = new ScrollPane();
        final GridPane pieceGrid = new GridPane();

        for (int i = 1; i < 36; i++) {

            // Get the piece to draw
            final Piece pieceToDraw = ourPieceFactory.getPiece(i);

            final Group pieceSelectorGroup = new Group(); // Pieces drawn in window
            final Group bullpenViewGroup = new Group(); // Pieces drawn in bullpen

            for (Square selectedSquare : pieceToDraw.squares) {

                Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
                Rectangle rectangleCopy = drawPieceRectangle(selectedSquare);

                pieceSelectorGroup.getChildren().add(selectedRectangle);
                bullpenViewGroup.getChildren().add(rectangleCopy);
            }

            // when piece is clicked on add it to bullpen
            pieceSelectorGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    AddPieceAction action = new AddPieceAction(pieceToDraw, bullpen);
                    if(action.doAction()){
                        undoHistory.push(action); // Push to undo stack
                        redoHistory.clear(); // Clear redo history
                    }
                    bullpenView.add(bullpenViewGroup, numberOfBullpenPieces % 2, numberOfBullpenPieces / 2);
                    bullpenView.setMargin(bullpenViewGroup, new Insets(10, 10, 10, 10));
                    bullpenView.setHalignment(pieceSelectorGroup, HPos.CENTER);
                    bullpenView.setValignment(pieceSelectorGroup, VPos.CENTER);
                    numberOfBullpenPieces++;
                    pieceSelector.close();
                }
            });


            pieceGrid.add(pieceSelectorGroup, numberOfPiecesDrawn % 4, numberOfPiecesDrawn / 4);
            pieceGrid.setMargin(pieceSelectorGroup, new Insets(10, 10, 10, 10));

            numberOfPiecesDrawn++;
        }

        gridScroll.setContent(pieceGrid);
        pieceSelector.setScene(new Scene(gridScroll, 640, 480));
        pieceSelector.show();


    }

    public void handleRotatePieceButtonAction (ActionEvent event) throws IOException {
        if (event.getSource() == rotateLeftButton) {
            // highlighting the border of the selected button
            rotateLeftButton.setStyle("-fx-background-color:darkblue");

        }
    }
    /**
     * Gets a specific node in a gridpane
     * http://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column
     *
     * @param row
     * @param column
     * @param gridPane
     * @return
     */
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    /**
     * Resets the Rows and Columns text to reflect a reset board
     *
     * @param levelType
     */
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

    /**
     * Will clear all the pieces in the bullpen
     *
     */
    public void resetPieces() {
        bullpenView.getChildren().clear();
        numberOfBullpenPieces = 0;
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
        resetButtons();
        resetBoard(num);
        resetFields(num);
        resetPieces();
        loadLevel(num);
    }

    /**
     * Called when save button is clicked, writes level information to a file.
     * The file information that is saved is the level number, metric such as moves left or time given,
     * the pieces present on both the board and in the bullpen, and the state of each board tile.
     * The location of pieces on the board is not saved.
     *
     * @param event
     */
    public void handleSaveButtonAction(ActionEvent event) throws IOException{
        saveLevel(Integer.parseInt(levelTextField.getText()));
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
            // highlighting the border of the selected button
            redButton.setStyle("-fx-background-color:darkblue");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");


        }
        if (ae.getSource() == greenButton) {
            color = Color.GREEN;

            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: darkblue");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");
        }
        if (ae.getSource() == yellowButton) {
            color = Color.YELLOW;

            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: darkblue");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");
        }
        if (ae.getSource() == whiteButton) {
            color = Color.WHITE;
            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: darkblue");
            blackButton.setStyle("-fx-background-color: transparent");
        }
        if (ae.getSource() == blackButton) {
            color = Color.BLACK;
            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: darkblue");
        }
        System.out.println("color changed to " + color.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfBullpenPieces = 0;
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

        // Set constraints (size of the cells for pieces)
        for (int i = 0; i < gridW; i++) {
            ColumnConstraints column = new ColumnConstraints(RectangleSize * 6);
            bullpenView.getColumnConstraints().add(column);
        }

        for (int i = 0; i < gridH; i++) {
            RowConstraints row = new RowConstraints(RectangleSize * 6);
            bullpenView.getRowConstraints().add(row);
        }

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
        tilePanes = new ArrayList<ArrayList<Pane>>();

        for (int i = 0; i < rows; i++) {
            ArrayList<Pane> tempArrayList = new ArrayList<>();

            for (int x = 0; x < columns; x++) {
                // adding a blank pane to fill out columns
                tempArrayList.add(new Pane());
            }

            tilePanes.add(tempArrayList);
            for (int j = 0; j < columns; j++) {

                final GridSquare pane = new GridSquare();

                pane.setMinSize(0, 0);
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, j, i);

                tilePanes.get(i).set(j, pane);

                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        event.consume();
                        boardController.handleBoardClicked(level.getBoardTiles().get(currentRow).get(currentColumn), tilePanes.get(currentRow).get(currentColumn));
                    }
                });

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
            String filepath = "DolonBuilder/resources/levels/lvl" + levelNum + ".bdsm";
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
            return;
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

        // Set the pieces given for the board
        ourPieceFactory = new PieceFactory(); // Generate pieceFactory
        for(int i: pieces){
            final Piece pieceToDraw = ourPieceFactory.getPiece(i); // Piece to be loaded
            final Group bullpenViewGroup = new Group(); // Bullpen view group

            // Draw each square and add it to the bullpen group
            for (Square selectedSquare : pieceToDraw.squares) {
                Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
                bullpenViewGroup.getChildren().add(selectedRectangle);
            }

            // Add the actual piece object to the bullpen
            AddPieceAction action = new AddPieceAction(pieceToDraw, bullpen); // Create action
            action.doAction(); // Do action- add to bullpen
            bullpenView.add(bullpenViewGroup, numberOfBullpenPieces % 2, numberOfBullpenPieces / 2);
            bullpenView.setMargin(bullpenViewGroup, new Insets(10, 10, 10, 10));
            bullpenView.setHalignment(bullpenViewGroup, HPos.CENTER);
            bullpenView.setValignment(bullpenViewGroup, VPos.CENTER);
            numberOfBullpenPieces++;
        }

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
                    level.getBoardTiles().get(count).get(i).setExists(false);
                    tilePanes.get(count).get(i).setStyle("-fx-background-color: black");
                } else if (tileInts[i] == 1) { // Valid blank tile
                    level.getBoardTiles().get(count).get(i).setExists(true);
                    tilePanes.get(count).get(i).setStyle("-fx-background-color: white");
                    tilePanes.get(count).get(i).setStyle("-fx-border-color: black");
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

    // Saves a level to a file
    public void saveLevel(int levelNum) throws FileNotFoundException {

        String filepath = "DolonBuilder/resources/levels/lvl" + levelNum + ".bdsm";

        try{

            FileWriter out = new FileWriter(filepath);

            out.write("###"); // Level divider
            out.write("\r\n");
            out.write(levelNumber.getText()); // Print level number
            out.write("\r\n");

            out.write("###"); // Metric divider
            out.write("\r\n");
            switch(levelNum%3){ // Print metric
                case 1: // Puzzle
                    out.write(movesRemainField.getText());
                    out.write("\r\n");
                    break;
                case 2: // Lightning
                    out.write(timerField.getText());
                    out.write("\r\n");
                    break;
                case 0: // Release
                    out.write("666");
                    out.write("\r\n");
                    break;
            }

            out.write("###"); // Pieces divider
            out.write("\r\n");

            out.write("###"); // Tiles divider
            out.write("\r\n");

            // Close file
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

