package BuilderControllers;

import BuilderModel.*;
import BuilderModel.PieceGroup;
import BuilderModel.LevelModel;
import BuilderModel.Piece;
import BuilderModel.ReleaseTile;
import BuilderModel.Square;
import BuilderModel.Tile;
import PieceFactory.*;
import UndoActionManager.*;
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
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**Handles the level
 * @author Walter
 * @author Stephen
 * @author Robyn
 * @author Arun
 * Created by slafo on 4/10/2016.
 */
public class LevelBuilderController implements Initializable {
    BoardController boardController;
    LevelModel level;
    @FXML
    public Button resizeBoard;
    @FXML
    public Button undoButton;
    @FXML
    public Button redoButton;
    @FXML
    public Button rotateLeftButton;
    @FXML
    public Button rotateRightButton;
    @FXML
    public Button flipHorizontalButton;
    @FXML
    public Button flipVerticalButton;
    @FXML
    public Button deletePieceButton;
    @FXML
    public Button greenButton; // create green release
    @FXML
    public Button redButton; // create red release
    @FXML
    public Button yellowButton; // create yellow release
    @FXML
    public Button blackButton; //create invalid tile
    @FXML
    public Button whiteButton; // create white tile
    @FXML
    public Button hintButton; // create a hint
    @FXML
    public Button homeButton; // Return to menu
    @FXML
    public Button loadButton; // load a level
    @FXML
    public Button saveButton; // reset the level
    @FXML
    public Button resetButton; // reset the level
    @FXML
    public Button addPieceButton; // add piece to bullpen
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

    int numberOfBullpenPieces;
    int gridW = 2;




    int gridH;
    private final static double RectangleSize = 45.83333333;
    Piece selectedPiece; // should adapt to use piece group
    Group selectedGroup; // should adapt to use piece group
    PieceFactory ourPieceFactory = new PieceFactory(); // Generate pieceFactory
    public final static DataFormat pieceShape = new DataFormat("piece");
    boolean placed = false;


    /**
     * Handles returning to the home screen when the home button is pressed
     *
     * @param event action event
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
     * called when reset button is clicked, resets all elements on the levelbuilder based on type of level
     *
     * @param event action event
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
        resetFields(i);
        resetBoard(i);
        resetPieces();

    }

    /** Resets background color for buttons
     *
     *
     */
    public void resetButtons(){
        // set black button to visually selected
        redButton.setStyle("-fx-background-color:transparent");
        greenButton.setStyle("-fx-background-color: transparent");
        yellowButton.setStyle("-fx-background-color: transparent");
        whiteButton.setStyle("-fx-background-color: transparent");
        blackButton.setStyle("-fx-background-color: darkblue");
        hintButton.setStyle("-fx-background-color: transparent");

        //black button is selected
        color = Color.BLACK;
    }

    /**
     * Resets the view of the board
     *
     * @param levelType the type of level that is being edited
     */
    public void resetBoard(int levelType) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // reset the visual aspect of the board
                tilePanes.get(i).get(j).setMinSize(0, 0);
                tilePanes.get(i).get(j).setStyle("-fx-background-color: white");
                tilePanes.get(i).get(j).setStyle("-fx-border-color: black");
                tilePanes.get(i).get(j).getStyleClass().add("board-cell");
                tilePanes.get(i).get(j).setOnDragDetected(null);
                tilePanes.get(i).get(j).setOnDragDone(null);

                if (!level.getBoard().getTiles().isEmpty()) {
                    // reset the underlying tiles for tile actions
                    level.getTile(i, j).setExists(true);
                    level.getTile(i, j).setCovered(-1);
                    level.getTile(i, j).setHint(false);
                    ((ReleaseTile)level.getTile(i, j)).setColor(Color.WHITE);
                }


                boardController.redNumTiles.clear();
                boardController.redNumPanes.clear();
                boardController.greenNumTiles.clear();
                boardController.greenNumPanes.clear();
                boardController.yellowNumTiles.clear();
                boardController.yellowNumPanes.clear();
                // clear the release level specifc parameters

                ((GridSquare)tilePanes.get(i).get(j)).getNumLabel().setText("");
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
     * @param selectedSquare the square that makes up a group that is to be drawn
     * @return individual square used in a group
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
     * @param event action event
     * @throws IOException
     */
    public void handleAddPieceButtonAction(ActionEvent event) throws IOException {
        if (!levelNumber.getText().equals("#")) {
            int numberOfPiecesDrawn = 0;
            // Set the pieces given for the board

            final Stage pieceSelector = new Stage();
            pieceSelector.initModality(Modality.APPLICATION_MODAL);

            ScrollPane gridScroll = new ScrollPane();
            GridPane pieceGrid = new GridPane();

            for (int i = 1; i < 36; i++) {

                // Get the piece to draw
                final Piece pieceToDraw = ourPieceFactory.getPiece(i);

                final PieceGroup pieceSelectorGroup = new PieceGroup(pieceToDraw); // Pieces drawn in window
                final PieceGroup bullpenViewGroup = new PieceGroup(pieceToDraw); // Pieces drawn in bullpen

                for (Square selectedSquare : pieceToDraw.squares) {

                    Rectangle selectedRectangle = drawPieceRectangle
                            (selectedSquare);
                    Rectangle rectangleCopy = drawPieceRectangle(selectedSquare);
                    selectedRectangle.setFill(pieceToDraw.getColor());
                    rectangleCopy.setFill(pieceToDraw.getColor());

                    pieceSelectorGroup.getGroup().getChildren().add(selectedRectangle);
                    bullpenViewGroup.getGroup().getChildren().add(rectangleCopy);
                }
               final LevelBuilderController lbc = this;
                // when piece is clicked on add it to bullpen
                pieceSelectorGroup.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        //AddPieceAction action = new AddPieceAction(pieceToDraw, level.getBullpen());
                        AddPieceAction action = new AddPieceAction(pieceToDraw, bullpenViewGroup.getGroup(), level.getBullpen(), bullpenView, lbc); // Create action

                        if(action.doAction()){
                            undoHistory.push(action); // Push to undo stack
                            redoHistory.clear(); // Clear redo history
                        }
                        bullpenView.add(bullpenViewGroup.getGroup(), numberOfBullpenPieces % 2, numberOfBullpenPieces / 2);
                        bullpenView.setMargin(bullpenViewGroup.getGroup(), new Insets(10, 10, 10, 10));
                        bullpenView.setHalignment(pieceSelectorGroup.getGroup(), HPos.CENTER);
                        bullpenView.setValignment(pieceSelectorGroup.getGroup(), VPos.CENTER);
                        numberOfBullpenPieces++;
                        pieceSelector.close();
                    }
                });

                // make it selectable
                bullpenViewGroup.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if (selectedPiece == pieceToDraw) {
                            selectedPiece = null;
                            bullpenViewGroup.getGroup().setEffect(null);
                            //System.out.println("unique id of selected piece"+selectedPiece.getUniqueID());
                        }
                        else {
                            if (selectedPiece != null) {
                                // remove visual effect of previous selected piece
                                selectedGroup.setEffect(null);
                            }
                            selectedPiece = pieceToDraw;
                            selectedGroup = bullpenViewGroup.getGroup();
                            System.out.println("piece selected");
                            Lighting light = new Lighting();
                            bullpenViewGroup.getGroup().setEffect(light);
                        }
                    }
                });


                bullpenViewGroup.getGroup().setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                        Dragboard db = bullpenViewGroup.getGroup().startDragAndDrop(TransferMode.MOVE);
                /* Put a string on a dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.put(pieceShape, pieceToDraw); //CHANGED: NOW HANDS OVER CLIPBOARD CONTENT
                        db.setContent(content);
                        System.out.println("Drag Detected");
                        event.consume();
                    }
                });

                bullpenViewGroup.getGroup().setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            bullpenViewGroup.getGroup().setVisible(false);
                        }
                        System.out.println("Drag Done");
                        event.consume();
                    }
                });


                pieceGrid.add(pieceSelectorGroup.getGroup(), numberOfPiecesDrawn % 4, numberOfPiecesDrawn / 4);
                pieceGrid.setMargin(pieceSelectorGroup.getGroup(), new Insets(10, 10, 10, 10));

                numberOfPiecesDrawn++;
            }

            gridScroll.setContent(pieceGrid);
            Scene pieceSelect = new Scene(gridScroll, 640, 480);
            pieceSelector.setTitle("Piece Selector");
            pieceSelector.setScene(pieceSelect);
            pieceSelector.show();
        }
    }

    /**
     * Deletes a piece
     */
    public void deletePieceFromBullpen(){
        numberOfBullpenPieces = 0;

        // Get list of IDs
        ArrayList<Piece> pieces = level.getBullpen().getPieces();
        ArrayList<Piece> pieceCopy = new ArrayList<Piece>(); // Pieces are copied here

        // ArrayList<Integer> pieceNums = bullpen.getPieceIDs();

        for(Piece p: pieces){
            pieceCopy.add(p);
        }

        resetPieces();

        for(Piece p: pieceCopy){
            final Piece pieceToDraw = p;
            final PieceGroup bullpenViewGroup = new PieceGroup(p); // Bullpen view group


            /*// Draw each square and add it to the bullpen group
            for (Square selectedSquare : p.squares) {
                Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
                selectedRectangle.setFill(p.getColor());
                bullpenViewGroup.getChildren().add(selectedRectangle);
            }*/

            // Add the actual piece object to the bullpen
            AddPieceAction action = new AddPieceAction(pieceToDraw, selectedGroup, level.getBullpen(), bullpenView, this); // Create action
            //AddPieceAction action = new AddPieceAction(this);
            action.doAction(); // Do action- add to bullpen
            bullpenView.add(bullpenViewGroup.getGroup(), numberOfBullpenPieces % 2, numberOfBullpenPieces / 2);
            bullpenView.setMargin(bullpenViewGroup.getGroup(), new Insets(10, 10, 10, 10));
            bullpenView.setHalignment(bullpenViewGroup.getGroup(), HPos.CENTER);
            bullpenView.setValignment(bullpenViewGroup.getGroup(), VPos.CENTER);

            // when piece is clicked on add it to bullpen
            bullpenViewGroup.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (selectedPiece == pieceToDraw) {
                        selectedPiece = null;
                        bullpenViewGroup.getGroup().setEffect(null);
                        System.out.println("unique id of selected piece"+selectedPiece.getUniqueID());
                    }
                    else {
                        if (selectedPiece != null) {
                            // remove visual effect of previous selected piece
                            selectedGroup.setEffect(null);
                        }
                        selectedPiece = pieceToDraw;
                        selectedGroup = bullpenViewGroup.getGroup();
                        System.out.println("piece selected");
                        Lighting light = new Lighting();
                        bullpenViewGroup.getGroup().setEffect(light);
                    }
                }
            });

            numberOfBullpenPieces++;
        }
    }

    @FXML
    /**
     * Handles deleting a piece from the bullpen
     *
     * @param event
     * @throws IOException
     */
    public void handleDeletePieceAction  (ActionEvent event) throws IOException {
        if (selectedPiece == null) {
            return;
        }
//
//        // Remove piece

        LevelBuilderController lbc = this;
        DeletePieceAction dpa = new DeletePieceAction(selectedPiece, selectedGroup, bullpenView, lbc, level);
        if (dpa.doAction()) {
            System.out.println("delete action performed");
            undoHistory.push(dpa);
            redoHistory.clear();
        }

    }


    /**
     * Handles the action performed when the rotate buttons are pressed
     *
     * @param event action event
     * @throws IOException
     */
    public void handleRotatePieceButtonAction (ActionEvent event) throws IOException {
        if (selectedPiece == null) {
            return;
        }
        // if flip horizontal button is pressed
        if (event.getSource() == rotateLeftButton) {
            RotatePieceAction rpa = new RotatePieceAction( selectedPiece,selectedGroup, 'l', RectangleSize);
            if (rpa.doAction()) {
                System.out.println("rotate action performed");
                undoHistory.push(rpa);
                redoHistory.clear();
            }
        }

        // if flip vertical button is pressed
        if (event.getSource() == rotateRightButton) {
            RotatePieceAction rpa = new RotatePieceAction( selectedPiece,selectedGroup, 'r', RectangleSize);
            if (rpa.doAction()) {
                System.out.println("rotate action performed");
                undoHistory.push(rpa);
                redoHistory.clear();
            }
        }
    }

    /**
     * Handles the action performed when the rotate buttons are pressed
     *
     * @param event action event
     * @throws IOException
     */
    public void handleFlipPieceButtonAction (ActionEvent event) throws IOException {
        if (selectedPiece == null) {
            return;
        }
                // if flip horizontal button is pressed
        if (event.getSource() == flipHorizontalButton) {
            FlipPieceAction fpa = new FlipPieceAction( selectedPiece,selectedGroup, 'h', RectangleSize);
            if (fpa.doAction()) {
                System.out.println("flip action performed");
                undoHistory.push(fpa);
                redoHistory.clear();
            }
        }

        // if flip vertical button is pressed
        if (event.getSource() == flipVerticalButton) {
            FlipPieceAction fpa = new FlipPieceAction( selectedPiece,selectedGroup, 'v', RectangleSize);
            if (fpa.doAction()) {
                System.out.println("flip action performed");
                undoHistory.push(fpa);
                redoHistory.clear();
            }
        }
    }


    /**
     * Gets a specific node in a gridpane
     * http://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column
     *
     * @param row desired row
     * @param column desired column
     * @param gridPane the gridPane to search
     * @return desired node
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
     * @param levelType the type of level being edited
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
     * Clears all the pieces in the bullpenView and in the bullpen
     *
     */
    public void resetPieces() {
        if (!level.getBullpen().getPieces().isEmpty()) {
            level.getBullpen().getPieces().clear();
            bullpenView.getChildren().clear();
            numberOfBullpenPieces = 0;
        }
    }

    /**
     * called when load button is clicked, loads the correct elements on the levelbuilder based on type of level and if
     * there is already a saved levels loads that information
     *
     * need to add actually loading functionality
     *
     * @param event action event
     */
    public void handleLoadButtonAction(ActionEvent event) throws IOException {
        // Return if empty




        if(levelTextField.getText().equals("")){
            return;
        }

        else if(!Pattern.matches("[0-9]+", levelTextField.getText())){
            return;
        }


        // only do loading things if the textfield has a valid value
        if (handleLevelChanged()) {
            // change the elements to match the correct level type
            levelNumber.setText(levelTextField.getText());

        }

        int num = Integer.parseInt(levelTextField.getText());

        resetButtons();
        resetFields(num);
        resetBoard(num);
        resetPieces();


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
     * Called when save button is clicked, writes level information to a file.
     * The file information that is saved is the level number, metric such as moves left or time given,
     * the pieces present on both the board and in the bullpen, and the state of each board tile.
     * The location of pieces on the board is not saved.
     *
     * @param event action event
     */
    public void handleSaveButtonAction(ActionEvent event) throws IOException{
        saveLevel(Integer.parseInt(levelTextField.getText()));

    }

    /**
     * called when mouse clicks on board, inverts the validity of the tile clicked and updates view
     *
     * @param event mouse event
     */
//    public void handleBoardClicked(MouseEvent event) {
//
//
//        boardController.handleBoardClicked(event);
//
//    }

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
     * Resize button is clicked
     * makes a rectangular area of tiles valid according to user input into rows and cols textFields
     *
     * @param event action event
     */
    public void handleResizeButton(ActionEvent event) {

            ResizeReleaseAction rla = new ResizeReleaseAction(level.getBoardTiles(), tilePanes, colsTextField, rowsTextField);
            if (rla.doAction()) {
                System.out.println("resize action performed");
                undoHistory.push(rla);
                redoHistory.clear();
            }


    }

    /**
     * Handles when the undo button is clicked
     */
    public void handleUndo() {
        System.out.println("undo button clicked");
        if (!undoHistory.empty()) {
            IAction i = undoHistory.pop();
            i.undoAction();
            redoHistory.push(i);
        }
    }

    /**
     * Handles when the redo button is pressed
     */
    public void handleRedo() {
        System.out.println("redo button clicked");
        if (!redoHistory.empty()) {
            IAction i = redoHistory.pop();
            i.redoAction();
            undoHistory.push(i);
        }
    }


    /**
     * Changes the background colors of buttons to show selection
     *
     * @param ae action event
     */
    public void changeColor(ActionEvent ae) {

        if (ae.getSource() == redButton) {
            color = Color.RED;
            // highlighting the border of the selected button
            redButton.setStyle("-fx-background-color:darkblue");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");
            hintButton.setStyle("-fx-background-color: transparent");

        }
        if (ae.getSource() == greenButton) {
            color = Color.GREEN;

            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: darkblue");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");
            hintButton.setStyle("-fx-background-color: transparent");
        }
        if (ae.getSource() == yellowButton) {
            color = Color.YELLOW;

            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: darkblue");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");
            hintButton.setStyle("-fx-background-color: transparent");
        }
        if (ae.getSource() == whiteButton) {
            color = Color.WHITE;
            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: darkblue");
            blackButton.setStyle("-fx-background-color: transparent");
            hintButton.setStyle("-fx-background-color: transparent");
        }
        if (ae.getSource() == blackButton) {
            color = Color.BLACK;
            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: darkblue");
            hintButton.setStyle("-fx-background-color: transparent");
        }

        if (ae.getSource() == hintButton) {
            color = Color.ORANGE;
            redButton.setStyle("-fx-background-color:transparent");
            greenButton.setStyle("-fx-background-color: transparent");
            yellowButton.setStyle("-fx-background-color: transparent");
            whiteButton.setStyle("-fx-background-color: transparent");
            blackButton.setStyle("-fx-background-color: transparent");
            hintButton.setStyle("-fx-background-color: darkblue");
        }
        System.out.println("color changed to " + color.toString());
    }

    /**
     * Called when the LevelBuilder view loads
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
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
                pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, j, i);

                tilePanes.get(i).set(j, pane);

                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        boardController.handleBoardClicked(level.getBoardTiles().get(currentRow).get(currentColumn), tilePanes.get(currentRow).get(currentColumn));
                        event.consume();
                    }
                });

                //In case something is dragged over the pane
                pane.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {

                        // need to add something to prevent adding to an occupied tile
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        Dragboard db = event.getDragboard();
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && level.getBoard().isValidMove(droppedPiece, currentRow, currentColumn)) {
                            event.acceptTransferModes(TransferMode.MOVE);
                            System.out.println("Drag Over is valid move");
                        }
                        event.consume();
                    }
                });

                pane.setOnDragEntered(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && level.getBoard().isOutOfBounds(droppedPiece, currentRow, currentColumn)) {
                            //System.out.println(droppedPiece.uniqueID);
                            for (Square selectedSquare : droppedPiece.squares) {
                                // Imitate transparency
                                if(level.getTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists() == false){
                                    getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: rgb(" +
                                        (droppedPiece.getColor().getRed()*255)/2 + ", " +
                                        (droppedPiece.getColor().getGreen()*255)/4 + ", " +
                                        (droppedPiece.getColor().getBlue()*255)/3 + ")"); // Set color to mimic transparency
                                }
                                else{
                                    getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: rgb(" +
                                        (droppedPiece.getColor().getRed()*255)*1.25 + ", " +
                                        (droppedPiece.getColor().getGreen()*255)*1.25 + ", " +
                                        (droppedPiece.getColor().getBlue()*255)*1.25 + ")"); // Set color to mimic transparency
                                }
                            }
                            //System.out.println("Drag Entered is valid move");
                        }

                        event.consume();
                    }
                });

                pane.setOnDragExited(new EventHandler<DragEvent>() { //Event to handle when the drag model is exited
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard(); //Get the dragboard that we have
                        //Get the point in space a drag is being exited
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        //Get the piece in the dragboard
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        //Iterate over all of the squares
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && level.getBoard().isOutOfBounds(droppedPiece, currentRow, currentColumn)) {
                            for (Square selectedSquare : droppedPiece.squares) {
                                //Get the board's view
                                Pane pane = (Pane) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol(), boardView);
                                //
                                if ((level.getTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getCovered() > -1)) {
                                    try{
                                        pane.setStyle("-fx-background-color: rgb(" +
                                                level.getBoard().getPieceFromID(level.getTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                        currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getRed()*255 + ", " +
                                                level.getBoard().getPieceFromID(level.getTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                        currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getGreen()*255 + ", " +
                                                level.getBoard().getPieceFromID(level.getTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                        currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getBlue()*255 + ")");
                                    } catch (PieceNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else if ((level.getTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists() == true)) {
                                    if((level.getTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getHint() == true)){
                                        pane.setStyle("-fx-background-color: orange");
                                    }
                                    else{
                                        pane.setStyle("-fx-background-color: white");
                                    }
                                } else if ((level.getTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists() == false)) {
                                    pane.setStyle("-fx-background-color: black");
                                }
                                event.consume();
                            }
                        }
                    }
                });
                final LevelBuilderController lbc = this;
                pane.setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        //If we have a piece with us
                        System.out.println("gesture sources "+ (event.getGestureSource() != pane));
                        System.out.println("has content "+ event.getDragboard().hasContent(pieceShape));
                        System.out.println("is valid move "+ level.getBoard().isValidMove(droppedPiece, currentRow, currentColumn));

                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && level.getBoard().isValidMove(droppedPiece, currentRow, currentColumn)) {
                            DragPieceAction dpa = new DragPieceAction(level, droppedPiece,bullpenView, selectedGroup, lbc, boardView, currentRow, currentColumn, success);
                            if(dpa.doAction()){
                                System.out.println("drag piece action performed");
                                undoHistory.push(dpa);
                                redoHistory.clear();
                                success = true;
                            }

                        }
                        event.setDropCompleted(success);
                        placed = event.isDropCompleted();
                        event.consume();
                    }
                });

            }
        }
    }

    /** Makes a piece moveable
     *
     * @param tilePane anchor pane of piece
     * @param droppedPiece piece to be made movable
     * @param currentRow row of tile
     * @param currentColumn column of tile
     */
    public void makeMovable(final GridSquare tilePane, final Piece droppedPiece, final int currentRow, final int currentColumn) {
        tilePane.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = tilePane.startDragAndDrop(TransferMode.MOVE);
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                droppedPiece.flipPieceVert();
                content.put(pieceShape, droppedPiece); //CHANGED: NOW HANDS OVER CLIPBOARD CONTENT
                db.setContent(content);
                System.out.println("Drag Detected");
                event.consume();
            }
        });

        tilePane.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {

                    // Determine the colour to set for the tile
                    for(Square squareToRemove : droppedPiece.squares){
                        GridSquare tilePaneToClear = (GridSquare)getNodeByRowColumnIndex(currentRow +
                                (squareToRemove.getRelRow()*-1), currentColumn + squareToRemove.getRelCol(), boardView);
                        tilePaneToClear.setOnDragDetected(null);
                        tilePaneToClear.setOnDragDone(null);

                        if(level.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getCovered() > -1){
                            tilePaneToClear.setStyle("-fx-background-color: #28a2db");
                        }
                        else if(level.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getExists() == true){
                            if(level.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getHint() == true){
                                tilePaneToClear.setStyle("-fx-background-color: orange");
                            }
                            else{
                                tilePaneToClear.setStyle("-fx-background-color: white");
                            }
                        }
                        else if(level.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getExists() == false){
                            tilePaneToClear.setStyle("-fx-background-color: black");
                        }
                    }
                }
                System.out.println("Drag Done");
                event.consume();
            }
        });
    }

    /** Makes a piece deletable
     *
     * @param node node containg piece
     * @param piece piece to be made deletable
     * @param row row of piece
     * @param column column of piece
     */
    public void makeDeletable(final Node node, final Piece piece, final int row, final int column){
        final LevelBuilderController lbc = this;
        node.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event){
                MouseButton button = event.getButton();

                if(button == MouseButton.PRIMARY){
                    // Don't do anything
                }
                else if(button == MouseButton.SECONDARY){
                    /*
                    level.getBoard().removePiece(piece.getUniqueID());
                    piece.flipPieceVert();
                    // Determine the colour to set for the tile
                    for(Square squareToRemove : piece.squares){
                        GridSquare tilePaneToClear = (GridSquare)getNodeByRowColumnIndex(row +
                            (squareToRemove.getRelRow()*-1), column + squareToRemove.getRelCol(), boardView);
                        tilePaneToClear.setOnDragDetected(null);
                        tilePaneToClear.setOnDragDone(null);
                        tilePaneToClear.setOnMouseClicked(null);
                        makeTileChangeableAgain(tilePaneToClear);

                        if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getCovered() > -1){
                            tilePaneToClear.setStyle("-fx-background-color: #28a2db");
                        }
                        else if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists() == true){
                            if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getHint() == true){
                                tilePaneToClear.setStyle("-fx-background-color: orange");
                            }
                            else{
                                tilePaneToClear.setStyle("-fx-background-color: white");
                            }
                        }
                        else if(level.getTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists() == false){
                            tilePaneToClear.setStyle("-fx-background-color: black");
                        }

                    }

                    // Add piece to bullpen
                    level.getBullpen().addPiece(piece);

                    // Add piece view to bullpen view
                    final PieceGroup currentPiece = new PieceGroup(piece);
                    bullpenView.add(currentPiece.getGroup(), numberOfBullpenPieces % 2, numberOfBullpenPieces / 2);
                    bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
                    bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
                    bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
                    gridH = (level.getBullpen().getPieces().size() + 2 - 1) / 2;

                    numberOfBullpenPieces++;
                    */
                    BoardToBullpenAction btba = new BoardToBullpenAction(level, piece, lbc, row, column, boardView, bullpenView);
                    if(btba.doAction()){
                        System.out.println(" board to bullpen action performed");
                        undoHistory.push(btba);
                        redoHistory.clear();
                    }
                }
                else if(button == MouseButton.MIDDLE){
                    // Don't do anything
                }
                event.consume();
            }

        });
    }

    /**
     * Adds the handleBoardClicked mouse event back to a tile
     *
     * @param pane pane to make changeable again
     */
    public void makeTileChangeableAgain(final Pane pane) {
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int currentRow = GridPane.getRowIndex(pane);
                int currentColumn = GridPane.getColumnIndex(pane);
                boardController.handleBoardClicked(level.getBoardTiles().get(currentRow).get(currentColumn), tilePanes.get(currentRow).get(currentColumn));
                event.consume();
            }
        });
    }

    /**
     * Parses the level and sets everything to its specifications
     *
     * @param levelNum number of the level to be loaded
     * @throws IOException
     */
    public void loadLevel(int levelNum) throws IOException {

        // Variables for level information
        // NOTE: will most likely be moved to more global variables
        int lvNum = 0;
        int metric = 0;
        ArrayList<Integer> pieces = new ArrayList<Integer>();
        ArrayList<String> tiles = new ArrayList<String>();

        // Release metrics so that release tiles are added properly
        ReleaseTile redTile[] = new ReleaseTile[6];
        ReleaseTile greenTile[] = new ReleaseTile[6];
        ReleaseTile yellowTile[] = new ReleaseTile[6];
        Pane redPane[] = new Pane[6];
        Pane greenPane[] = new Pane[6];
        Pane yellowPane[] = new Pane[6];
        boolean usedSlots[] = new boolean[18];
        Arrays.fill(usedSlots, Boolean.FALSE);

        // Starts at 0 because file begins with ### and will automatically increment
        int readCount = 0; // Determines what part of the files is being parsed

        try {
            // Parsing objects
            // Get filepath for the right level, and then load it in
            String filepath = "../../BuilderLevels/lvl" + levelNum + ".bdsm";
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Level Error");
            alert.setHeaderText("Level Missing");
            alert.setContentText("The level you were trying to save to is missing");
            return;
        } catch (NullPointerException e){
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
            final PieceGroup bullpenViewGroup = new PieceGroup(pieceToDraw); // Bullpen view group

            // Draw each square and add it to the bullpen group
            for (Square selectedSquare : pieceToDraw.squares) {
                Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
                selectedRectangle.setFill(pieceToDraw.getColor());
                bullpenViewGroup.getGroup().getChildren().add(selectedRectangle);
            }

            // Add the actual piece object to the bullpen
            AddPieceAction action = new AddPieceAction(pieceToDraw, selectedGroup, level.getBullpen(), bullpenView, this); // Create action
            // Create action
            action.doAction(); // Do action- add to bullpen
            bullpenView.add(bullpenViewGroup.getGroup(), numberOfBullpenPieces % 2, numberOfBullpenPieces / 2);
            bullpenView.setMargin(bullpenViewGroup.getGroup(), new Insets(10, 10, 10, 10));
            bullpenView.setHalignment(bullpenViewGroup.getGroup(), HPos.CENTER);
            bullpenView.setValignment(bullpenViewGroup.getGroup(), VPos.CENTER);

            // when piece is clicked on add it to bullpen
            bullpenViewGroup.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (selectedPiece == pieceToDraw) {
                        selectedPiece = null;
                        bullpenViewGroup.getGroup().setEffect(null);
                        System.out.println("unique id of selected piece"+selectedPiece.getUniqueID());
                    }
                    else {
                        if (selectedPiece != null) {
                            // remove visual effect of previous selected piece
                            selectedGroup.setEffect(null);
                        }
                        selectedPiece = pieceToDraw;
                        selectedGroup = bullpenViewGroup.getGroup();
                        System.out.println("piece selected");
                        Lighting light = new Lighting();
                        bullpenViewGroup.getGroup().setEffect(light);
                    }
                }
            });

            bullpenViewGroup.getGroup().setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                    Dragboard db = bullpenViewGroup.getGroup().startDragAndDrop(TransferMode.MOVE);
                /* Put a string on a dragboard */
                    ClipboardContent content = new ClipboardContent();
                    content.put(pieceShape, pieceToDraw); //CHANGED: NOW HANDS OVER CLIPBOARD CONTENT
                    db.setContent(content);
                    System.out.println("Drag Detected");
                    event.consume();
                }
            });

            bullpenViewGroup.getGroup().setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        bullpenViewGroup.getGroup().setVisible(false);
                    }
                    System.out.println("Drag Done");
                    event.consume();
                }
            });

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

                } else if (tileInts[i] == 1 || tileInts[i] == 91) { // Valid blank tile
                    level.getBoardTiles().get(count).get(i).setExists(true);
                    if(tileInts[i] == 91){ // Set a hint tile
                        tilePanes.get(count).get(i).setStyle("-fx-background-color: orange");
                        level.getBoardTiles().get(count).get(i).setHint(true);
                    }
                    else{
                        tilePanes.get(count).get(i).setStyle("-fx-background-color: white");
                    }
                    tilePanes.get(count).get(i).setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                } else if ((tileInts[i] > 20 && tileInts[i] < 27) || (tileInts[i] > 920 && tileInts[i] < 927)) { // Red release tile: 21-26 indicate the number on the tile.
                    level.getBoardTiles().get(count).get(i).setExists(true);

                    if(tileInts[i] > 30){ // Hint tile
                        level.getBoardTiles().get(count).get(i).setHint(true);
                        tilePanes.get(count).get(i).setStyle("-fx-background-color: orange");
                        redTile[tileInts[i]-921] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                        redTile[tileInts[i]-921].setExists(true); // Set to valid tile
                        redTile[tileInts[i]-921].setColor(Color.RED); // Set color
                        redPane[tileInts[i]-921] = tilePanes.get(count).get(i);
                        usedSlots[tileInts[i]-921] = true; // Set slot to used
                    }
                    else{
                        redTile[tileInts[i]-21] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                        redTile[tileInts[i]-21].setExists(true); // Set to valid tile
                        redTile[tileInts[i]-21].setColor(Color.RED); // Set color
                        redPane[tileInts[i]-21] = tilePanes.get(count).get(i);
                        usedSlots[tileInts[i]-21] = true; // Set slot to used
                    }

                } else if ((tileInts[i] > 30 && tileInts[i] < 37) || (tileInts[i] > 930 && tileInts[i] < 937)) { // Green release tile: 31-36 indicate the number on the tile.
                    level.getBoardTiles().get(count).get(i).setExists(true);

                    if(tileInts[i] > 40){ // Hint tile
                        level.getBoardTiles().get(count).get(i).setHint(true);
                        tilePanes.get(count).get(i).setStyle("-fx-background-color: orange");
                        greenTile[tileInts[i]-931] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                        greenTile[tileInts[i]-931].setExists(true); // Set to valid tile
                        greenTile[tileInts[i]-931].setColor(Color.GREEN); // Set color
                        greenPane[tileInts[i]-931] = tilePanes.get(count).get(i);
                        usedSlots[tileInts[i]-925] = true; // Set slot to used
                    }
                    else{
                        greenTile[tileInts[i]-31] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                        greenTile[tileInts[i]-31].setExists(true); // Set to valid tile
                        greenTile[tileInts[i]-31].setColor(Color.GREEN); // Set color
                        greenPane[tileInts[i]-31] = tilePanes.get(count).get(i);
                        usedSlots[tileInts[i]-25] = true; // Set slot to used
                    }

                } else if ((tileInts[i] > 40 && tileInts[i] < 47) || (tileInts[i] > 940 && tileInts[i] < 947)) { // Yellow release tile: 41-46 indicate the number on the tile.
                    level.getBoardTiles().get(count).get(i).setExists(true);

                    if(tileInts[i] > 50){ // Hint tile
                        level.getBoardTiles().get(count).get(i).setHint(true);
                        tilePanes.get(count).get(i).setStyle("-fx-background-color: orange");
                        yellowTile[tileInts[i]-941] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                        yellowTile[tileInts[i]-941].setExists(true); // Set to valid tile
                        yellowTile[tileInts[i]-941].setColor(Color.YELLOW); // Set color
                        yellowPane[tileInts[i]-941] = tilePanes.get(count).get(i);
                        usedSlots[tileInts[i]-929] = true;
                    }
                    else{
                        yellowTile[tileInts[i]-41] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                        yellowTile[tileInts[i]-41].setExists(true); // Set to valid tile
                        yellowTile[tileInts[i]-41].setColor(Color.YELLOW); // Set color
                        yellowPane[tileInts[i]-41] = tilePanes.get(count).get(i);
                        usedSlots[tileInts[i]-29] = true;
                    }

                }
            }

            // Increment count
            count++;
        }

        // Add the release tiles to the board and draw them
        for(int i = 0; i < 6; i++){
            if(usedSlots[i]){ // Add red tile
                boardController.getColorNumTiles(Color.RED).add(redTile[i]); // Add tile
                ((GridSquare)redPane[i]).setNumber(i+1); // Set label
                boardController.getColorNumPanes(Color.RED).add((GridSquare)redPane[i]); // Add pane
                boardController.updateColorNums(boardController.getColorNumTiles(Color.RED), // Update color
                        boardController.getColorNumPanes(Color.RED));
            }
            if(usedSlots[i+6]){ // Add green tile
                boardController.getColorNumTiles(Color.GREEN).add(greenTile[i]); // Add tile
                ((GridSquare)greenPane[i]).setNumber(i+1); // Set label
                boardController.getColorNumPanes(Color.GREEN).add((GridSquare)greenPane[i]); // Add pane
                boardController.updateColorNums(boardController.getColorNumTiles(Color.GREEN), // Update color
                        boardController.getColorNumPanes(Color.GREEN));
            }
            if(usedSlots[i+12]){ // Add yellow tile
                boardController.getColorNumTiles(Color.YELLOW).add(yellowTile[i]); // Add tile
                ((GridSquare)yellowPane[i]).setNumber(i+1); // Set label
                boardController.getColorNumPanes(Color.YELLOW).add((GridSquare)yellowPane[i]); // Add pane
                boardController.updateColorNums(boardController.getColorNumTiles(Color.YELLOW), // Update color
                        boardController.getColorNumPanes(Color.YELLOW));
            }
        }
    }

    /**
     * Saves the level to a file
     *
     * @param levelNum number of the level to be saved
     * @throws FileNotFoundException
     */
    public void saveLevel(int levelNum) throws FileNotFoundException {

        String filepath = "../../BuilderLevels/lvl" + levelNum + ".bdsm";

        try{
            FileWriter out;

            out = new FileWriter(filepath);


            out.write("###"); // Level divider
            out.write("\r\n");
            out.write(levelNumber.getText()); // Print level number
            out.write("\r\n");

            out.write("###"); // Metric divider
            out.write("\r\n");
            switch(levelNum%3){ // Print metric
                case 1: // Puzzle
                    if(!Pattern.matches("[0-9]+", movesRemainField.getText())){
                        out.write("5"); // Default
                    }
                    else{
                        out.write(movesRemainField.getText());
                    }
                    out.write("\r\n");
                    break;
                case 2: // Lightning
                    if(!Pattern.matches("[0-9]+", timerField.getText())){
                        out.write("20"); // Default
                    }
                    else{
                        out.write(timerField.getText());
                    }
                    out.write("\r\n");
                    break;
                case 0: // Release
                    out.write("666");
                    out.write("\r\n");
                    break;
            }

            out.write("###"); // Pieces divider
            out.write("\r\n");
            for(Piece p: level.getBullpen().getPieces()){
                String pieceNum = Integer.toString(p.getPieceID());
                out.write(pieceNum);
                out.write("\r\n");
            }
            // Write board pieces
            ArrayList<Integer> boardIDs = level.getBoard().getPieceIDs();
            for(int i: boardIDs){
                out.write(Integer.toString(i));
                out.write("\r\n");
            }

            out.write("###"); // Tiles divider
            out.write("\r\n");
            // Iterate through the board and output the value for each square
            for(ArrayList<Tile> arr: level.getBoard().getTiles()){
                for(Tile t: arr){
                    int tileCount = 0;

                    if(t.getExists()){ // If it exists, have to find out whether it's a release tile or not
                        if(((ReleaseTile)t).getColor() == Color.WHITE){ // Regular tile
                            if(t.getHint()){
                                out.write("91");
                            }
                            else{
                                out.write("1");
                            }
                        }
                        else if(((ReleaseTile)t).getColor() == Color.RED){ // Red tile
                            if(t.getHint()){
                                out.write("92" + Integer.toString(((ReleaseTile)t).getNum()));
                            }
                            else{
                                out.write("2" + Integer.toString(((ReleaseTile)t).getNum()));
                            }
                        }
                        else if(((ReleaseTile)t).getColor() == Color.GREEN){ // Green tile
                            if(t.getHint()){
                                out.write("93" + Integer.toString(((ReleaseTile)t).getNum()));
                            }
                            else{
                                out.write("3" + Integer.toString(((ReleaseTile)t).getNum()));
                            }
                        }
                        else if(((ReleaseTile)t).getColor() == Color.YELLOW){ // Yellow tile
                            if(t.getHint()){
                                out.write("94" + Integer.toString(((ReleaseTile)t).getNum()));
                            }
                            else{
                                out.write("4" + Integer.toString(((ReleaseTile)t).getNum()));
                            }
                        }
                    }
                    else{
                        out.write("0"); // If it doesn't exist, it's a no-tile
                    }

                    if(tileCount < 11){ // Add spaces
                        out.write(" ");
                    }

                    tileCount++;
                }
                out.write("\r\n"); // New line
            }

            // Close file
            out.close();

            // Pop up save confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kabasuji Builder");
            alert.setHeaderText(null);
            alert.setContentText("The level has been saved.");
            alert.showAndWait();

        }
        catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Level Error");
            alert.setHeaderText("Level Missing");
            alert.setContentText("The level you were trying to save to is missing");
        }
    }

    /**Getter for numberOfBullpenPieces
     *
     * @return the number of pieces in teh bullpen
     */
    public int getNumberOfBullpenPieces() {
        return numberOfBullpenPieces;
    }

    /** Setter for numberOBullpenPieces
     *
     * @param numberOfBullpenPieces new number to set as value
     */
    public void setNumberOfBullpenPieces(int numberOfBullpenPieces) {
        this.numberOfBullpenPieces = numberOfBullpenPieces;
    }

    /**
     * Setter for gridH
     * @param gridH new value to be set
     */
    public void setGridH(int gridH) {
        this.gridH = gridH;
    }
}

