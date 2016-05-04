package KabasujiControllers;

import PieceFactory.PieceFactory;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.Button;

import java.io.*;
import java.net.URL;
import java.util.*;

import KabasujiModel.*;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Arun Donti, andonti@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * @author Stephen Lafortune, srlafortune@wpi.edu
 * @author Walter Ho, who@wpi.edu
 * Controls the interactions between the visual elements and the Model.
 */
public class LevelViewController implements Initializable {
    //Visual references
    @FXML
    Button homeButton, backLevel, forwardLevel, flipHoriz, flipVert, rotateLeft, rotateRight;
    @FXML
    Label levelNumber, allowedLabel, limitLabel;
    @FXML
    GridPane boardView, bullpenView;
    @FXML
    ImageView levelIcon, backArrow, forwardArrow, firstStar, secondStar, thirdStar, homeIcon;

    Timer timer;
    Piece selectedPiece; // For rotation/flipping
    Group selectedGroup; // For rotation/flipping
    GameMenu menu;

    boolean placed = false;
    boolean timerActive = false;
    private int readInLevelNumber = 0;

    ArrayList<ArrayList<Pane>> tilePanes;
    public final static DataFormat pieceShape = new DataFormat("piece");
    private final static double squareWidth = 45.8333333;
    LevelModel ourModel;
    int timeLeft;
    boolean DEBUG = false;
    // max rows and columns, might need to be changed
    int rows = 12, columns = 12, gridW = 2, gridH, numberOfPiecesDrawn;

    boolean frozenStars; // true if the star count is unchangable ie timer ran out

    /**
     * Constructor for the controller
     */
    public LevelViewController() {
        numberOfPiecesDrawn = 0;
    }

    /**
     * Handles the home button being pressed
     * @param event action event
     * @throws IOException if it could not load the display element
     */
    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        // Return to home menu
        if (event.getSource() == homeButton) {
            stage = (Stage) homeButton.getScene().getWindow();
            //load up Start screen FXML document
            root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Draws an individual rectangle for a given square (used for GUI elements)
     * @param selectedSquare the square that makes up a group that is to be drawn
     * @return individual square used in a group
     */
    public Rectangle drawPieceRectangle(Square selectedSquare){
        Rectangle selectedRectangle = new Rectangle();
        selectedRectangle.setX((selectedSquare.getRelCol()) * squareWidth); //Set X position based on the Relative Column
        selectedRectangle.setY((-selectedSquare.getRelRow()) * squareWidth); //Set Y position based on the Relative Row
        selectedRectangle.setWidth(squareWidth); //Set the width of each rectangle
        selectedRectangle.setHeight(squareWidth); //Set the height of each rectangle
        selectedRectangle.setFill(Color.RED); //Color the fill
        selectedRectangle.setStroke(Color.BLACK); //Color the outline
        return selectedRectangle;
    }

    /**
     * Handles the action performed when the rotate buttons are pressed.
     * @param event action event
     * @throws IOException
     */
    public void handleRotatePieceButtonAction (ActionEvent event) throws IOException {
        //If we don't have a selected piece, exit
        if(selectedPiece == null){
            return;
        }

        // if rotate left button is pressed
        if (event.getSource() == rotateLeft) {
            selectedPiece.rotatePieceLeft();
        }

        // if rotate right button is pressed
        if (event.getSource() == rotateRight) {
            selectedPiece.rotatePieceRight();
        }

        // clears the squares in the group and adds in the repositioned ones
        selectedGroup.getChildren().clear();
        for (Square selectedSquare : selectedPiece.squares) {
            Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
            selectedRectangle.setFill(selectedPiece.getColor());
            selectedGroup.getChildren().add(selectedRectangle);
        }
    }

    /**
     * Handles the action performed when the flip buttons are pressed.
     * @param event action event
     * @throws IOException
     */
    public void handleFlipPieceButtonAction (ActionEvent event) throws IOException {
        if(selectedPiece == null){
            return;
        }
        // if flip horizontal button is pressed
        if (event.getSource() == flipHoriz) {
            selectedPiece.flipPieceHoriz();
        }

        // if flip vertical button is pressed
        if (event.getSource() == flipVert) {
            // highlighting the border of the selected button
            selectedPiece.flipPieceVert();
        }

        // clears the squares in the group and adds in the repositioned ones
        selectedGroup.getChildren().clear();

        for (Square selectedSquare : selectedPiece.squares) {
            Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
            selectedRectangle.setFill(selectedPiece.getColor());
            selectedGroup.getChildren().add(selectedRectangle);
        }
    }

    @Override
    /**
     * Initialize the game upon every entry to the game world
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boardView.getStyleClass().add("board");

        // Set constraints (size of the cells)
        for (int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(squareWidth);
            boardView.getColumnConstraints().add(column);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(squareWidth);
            boardView.getRowConstraints().add(row);
        }

        // Draw grid on board
        tilePanes = new ArrayList<>();
        // Set grid (will later look at the grid for the level and set it that way)
        //Iterate over the board and draw everything appropriate
        for (int i = 0; i < rows; i++) {
            ArrayList<Pane> tempArrayList = new ArrayList<>();

            for (int x = 0; x < columns; x++) {
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
                //In case something is dragged over the pane
                pane.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        if (DEBUG){
                            ourModel.getBoard().printBoardAsDebug();
                        }

                        // need to add something to prevent adding to an occupied tile
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        Dragboard db = event.getDragboard();
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        //If we're in a Release Level or a Puzzle Level
                        if (ourModel.getLevelNum() % 3 == 1 || ourModel.getLevelNum() % 3 == 0) {
                            if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape)
                                    && ourModel.getBoard().isValidMove(droppedPiece, currentRow, currentColumn)) {
                                event.acceptTransferModes(TransferMode.MOVE);
                                if (DEBUG){
                                    System.out.println("Drag Over is valid move");
                                }

                            }
                        }
                        //If we're in a lightning level
                        if (ourModel.getLevelNum() % 3 == 2) {
                            if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape)
                                    && ourModel.getBoard().isValidLightningMove(droppedPiece, currentRow, currentColumn)) {
                                event.acceptTransferModes(TransferMode.MOVE);
                                if (DEBUG) {
                                    System.out.println("Drag Over is valid move");
                                }
                            }
                        }
                        event.consume();
                    }
                });
                //Entering a drag maneuver for a pane
                pane.setOnDragEntered(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //Get information about what's being dragged in
                        Dragboard db = event.getDragboard();
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getBoard().isOutOfBounds(droppedPiece, currentRow, currentColumn)) {
                            //System.out.println(droppedPiece.uniqueID);
                            for (Square selectedSquare : droppedPiece.squares) {
                                // Imitate transparency
                                //If we're hovering over a tile that doesn't exist
                                if((!ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists())){
                                    getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: rgb(" +
                                        (droppedPiece.getColor().getRed()*255)/2 + ", " +
                                        (droppedPiece.getColor().getGreen()*255)/4 + ", " +
                                        (droppedPiece.getColor().getBlue()*255)/3 + ")"); // Set color to mimic transparency
                                }
                                //If we're hovering over a normal pane
                                else{
                                    getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: rgb(" +
                                            (droppedPiece.getColor().getRed()*255)*1.25 + ", " +
                                            (droppedPiece.getColor().getGreen()*255)*1.25 + ", " +
                                            (droppedPiece.getColor().getBlue()*255)*1.25 + ")"); // Set color to mimic transparency
                                }
                            }
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
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getBoard().isOutOfBounds(droppedPiece, currentRow, currentColumn)) {
                            for (Square selectedSquare : droppedPiece.squares) {
                                //Get the board's view
                                Pane pane = (Pane) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol(), boardView);
                                //If we had a piece here
                                if ((ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getCovered() > -1)) {
                                    //Try to get the piece from the ID and set the color
                                    try {
                                        pane.setStyle("-fx-background-color: rgb(" +
                                            ourModel.getBoard().getPieceFromID(ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                    currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getRed() * 255 + ", " +
                                            ourModel.getBoard().getPieceFromID(ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                    currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getGreen() * 255 + ", " +
                                            ourModel.getBoard().getPieceFromID(ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                    currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getBlue() * 255 + ")");
                                    }
                                    catch (PieceNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //If we're in a part of the board that actually exists (not a black tile)
                                else if (ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists()) {
                                    //Check if we have to set it back to a hint
                                    if (ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getHint()){
                                        pane.setStyle("-fx-background-color: orange");
                                    }
                                    //otherwise, it's an empty white tile
                                    else {
                                        pane.setStyle("-fx-background-color: white");
                                    }
                                }
                                //If we're in a black tile
                                else if (!ourModel.getBoard().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists()) {
                                    pane.setStyle("-fx-background-color: black");
                                }
                                event.consume();
                            }
                        }
                    }
                });
                //When the drag is dropped and a piece can be placed
                pane.setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        //Get information about the dragboard
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);

                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        //If we have a piece with us, and we're in puzzle level
                        if (ourModel.getLevelNum() % 3 == 1) {
//                            if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getBoard().isValidMove(droppedPiece, currentRow, currentColumn)) {
//                            }
                            Color color = droppedPiece.getColor();
                            //Iterate over the pieces
                            for (Square selectedSquare : droppedPiece.squares) {
                                GridSquare tilePane = (GridSquare) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView);
                                makeDeletable(tilePane, droppedPiece, currentRow, currentColumn);
                                makeMovable(tilePane, droppedPiece, currentRow, currentColumn); //Make them movable and deletable
                            }
                            ourModel.getBoard().removePiece(droppedPiece.getUniqueID()); //Remove the piece from the bullpen
                            decreaseMovesCount();
                            ourModel.getBoard().addPiece(droppedPiece, currentRow, currentColumn); //Add the piece to the board
                            if (DEBUG) {
                                ourModel.getBoard().printBoardAsDebug();
                            }
                            // Only place if it's a valid move
                            success = true;
                            ourModel.getBullpen().removePiece(droppedPiece.getUniqueID());
                            bullpenView.getChildren().remove(selectedGroup); // Remove view
                            redrawBullpen();
                            try {
                                updateStars();
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //If we have a Lightning Level
                        if (ourModel.getLevelNum() % 3 == 2) {
                            Color color = droppedPiece.getColor();
                            for (Square selectedSquare : droppedPiece.squares) {
                                GridSquare tilePane = (GridSquare) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView);
                            }
                            //Add piece to the board
                            ourModel.getBoard().addPieceLightning(droppedPiece, currentRow, currentColumn);
                            ourModel.getBoard().printBoardAsDebug();
                            // Only place if it's a valid move
                            success = true;
                            ourModel.getBullpen().removePiece(droppedPiece.getUniqueID());
                            bullpenView.getChildren().remove(selectedGroup); // Remove view
                            redrawBullpen();

                            // add a random Piece back to the bullpen
                            // pick one of the random hexominos
                            int pieceID = (int )(Math.random() * 35 + 1);
                            final Piece ourPiece = new PieceFactory().getPiece(pieceID);
                            ourModel.getBullpen().addPiece(ourPiece);

                            final PieceGroup currentPiece = new PieceGroup(ourPiece);
                            // Add to bullpen
                            bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
                            bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
                            bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
                            bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
                            gridH = (ourModel.getBullpen().getPieces().size() + 2 - 1) / 2;
                            currentPiece.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                                public void handle(MouseEvent event) {
                                    if (selectedPiece == ourPiece) {
                                        selectedPiece = null;
                                        currentPiece.getGroup().setEffect(null);
                                    }
                                    else {
                                        if (selectedPiece != null) {
                                            // remove visual effect of previous selected piece
                                            selectedGroup.setEffect(null);
                                        }
                                        selectedPiece = ourPiece;
                                        selectedGroup = currentPiece.getGroup();
                                        System.out.println("piece selected");
                                        Lighting light = new Lighting();
                                        currentPiece.getGroup().setEffect(light);
                                    }
                                }
                            });

                            numberOfPiecesDrawn++;
                            //numberOfPiecesDrawn--;
                            try {
                                updateStars();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //If we have a Release Level
                        if (ourModel.getLevelNum() % 3 == 0) {
                            Color color = droppedPiece.getColor();
                            for (Square selectedSquare : droppedPiece.squares) {
                                GridSquare tilePane = (GridSquare) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView);
                            }
                            ourModel.getBoard().addPiece(droppedPiece, currentRow, currentColumn);
                            ourModel.getBoard().printBoardAsDebug();
                            // Only place if it's a valid move
                            success = true;
                            ourModel.getBullpen().removePiece(droppedPiece.getUniqueID());
                            bullpenView.getChildren().remove(selectedGroup); // Remove view
                            redrawBullpen();
                            //numberOfPiecesDrawn--;
                            try {
                                updateStars();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }


                        event.setDropCompleted(success);
                        placed = event.isDropCompleted();
                        event.consume();
                    }
                });
            }

        }
        // Set constraints (size of the cells for pieces)
        for (int i = 0; i < gridW; i++) {
            ColumnConstraints column = new ColumnConstraints(squareWidth * 6);
            bullpenView.getColumnConstraints().add(column);
        }
        for (int i = 0; i < gridH; i++) {
            RowConstraints row = new RowConstraints(squareWidth * 6);
            bullpenView.getRowConstraints().add(row);
        }
    }

    /**
     * Redraws the bullpen for when a piece is added or removed from the bullpen
     */
    public void redrawBullpen(){
        ArrayList<Piece> pieces = ourModel.getBullpen().getPieces();
        ArrayList<Piece> pieceCopy = new ArrayList<Piece>(); // Pieces are copied here

        for(Piece p: pieces){
            pieceCopy.add(p);
        }

        // Reset board
        bullpenView.getChildren().clear();
        ourModel.getBullpen().getPieces().clear();
        numberOfPiecesDrawn = 0;

        for(Piece p: pieceCopy){
            final Piece pieceToDraw = p;
            final PieceGroup currentPiece = new PieceGroup(p);

            // Add the actual piece object to the bullpen
            ourModel.getBullpen().addPiece(p);
            bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
            bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
            bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
            bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);

            // when piece is clicked on add it to bullpen
            currentPiece.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (selectedPiece == pieceToDraw) {
                        selectedPiece = null;
                        currentPiece.getGroup().setEffect(null);
                    }
                    else {
                        if (selectedPiece != null) {
                            // remove visual effect of previous selected piece
                            selectedGroup.setEffect(null);
                        }
                        selectedPiece = pieceToDraw;
                        selectedGroup = currentPiece.getGroup();
                        System.out.println("piece selected");
                        Lighting light = new Lighting();
                        currentPiece.getGroup().setEffect(light);
                    }
                }
            });

            numberOfPiecesDrawn++;
        }
    }

    /**
     * Makes a GridSquare deletable from the board
     * @param node Node to make deletable
     * @param piece Associated piece
     * @param row a row location to make deletable, not changing
     * @param column a column location to make deletable, not changing
     */
    private void makeDeletable(final Node node, final Piece piece, final int row, final int column) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if(button == MouseButton.PRIMARY){
                    // do nothing
                }
                else if(button == MouseButton.SECONDARY){
                    ourModel.getBoard().removePiece(piece.getUniqueID());
                    System.out.println("Make deletable column" + column);
                    System.out.println("Make deletable row:" + row);
                    piece.flipPieceVert();
                    for (Square squareToRemove : piece.squares) {
                        GridSquare tilePaneToClear = (GridSquare) getNodeByRowColumnIndex(row + (squareToRemove.getRelRow()*-1), column + squareToRemove.getRelCol(), boardView);
                        //System.out.println(ourModel.getBoard().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getHint());
                        if ((ourModel.getBoard().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getCovered() > -1)) {
                            tilePaneToClear.setStyle("-fx-background-color: #28a2db");
                        }
                        //If what we're dragging over exists and isn't covered
                        else if (ourModel.getBoard().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists()) {
                            //If we have a hint on that tile
                            if (ourModel.getBoard().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getHint()){
                                tilePaneToClear.setStyle("-fx-background-color: orange");
                            }
                            //If it's a regular tile
                            else{
                                tilePaneToClear.setStyle("-fx-background-color: white");
                            }
                        }
                        //It's a black tile
                        else if (!ourModel.getBoard().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists()) {
                            tilePaneToClear.setStyle("-fx-background-color: black");
                        }
                        tilePaneToClear.setOnDragDetected(null);
                        tilePaneToClear.setOnDragDone(null);
                    }

                    // Add piece back to bullpen
                    ourModel.getBullpen().addPiece(piece);

                    // Add view to bullpen view
                    final PieceGroup currentPiece = new PieceGroup(piece);
                    bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
                    bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
                    bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
                    bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
                    gridH = (ourModel.getBullpen().getPieces().size() + 2 - 1) / 2;
                    //Change effects and lighting levels
                    currentPiece.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            if (selectedPiece == currentPiece.getPiece()) {
                                selectedPiece = null;
                                currentPiece.getGroup().setEffect(null);
                            }
                            else {
                                if (selectedPiece != null) {
                                    // remove visual effect of previous selected piece
                                    selectedGroup.setEffect(null);
                                }
                                selectedPiece = currentPiece.getPiece();
                                selectedGroup = currentPiece.getGroup();
                                System.out.println("piece selected");
                                Lighting light = new Lighting();
                                currentPiece.getGroup().setEffect(light);
                            }
                        }
                    });

                    //Increment pieces drawn
                    numberOfPiecesDrawn++;
                    decreaseMovesCount();
                }
                else if(button==MouseButton.MIDDLE){
                    // do nothing
                }
                event.consume();
            }
        });
    }

    /**
     * Makes a GridSquare movable on the board
     * @param tilePane GridSquare to make movable
     * @param droppedPiece Associated piece
     * @param currentRow a row location to make movable, not changing
     * @param currentColumn a column location to make movable, not changing
     */
    private void makeMovable(final GridSquare tilePane, final Piece droppedPiece, final int currentRow, final int currentColumn){
        //When a drag is detected
        tilePane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = tilePane.startDragAndDrop(TransferMode.MOVE);
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                droppedPiece.flipPieceVert();
                content.put(pieceShape, droppedPiece); //CHANGED: NOW HANDS OVER CLIPBOARD CONTENT
                db.setContent(content);
                if (DEBUG) {
                    System.out.println("Drag Detected");
                }
                event.consume();
            }
        });
        //When a drag is completed
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
                            //If the piece is covered
                            if(ourModel.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getCovered() > -1){
                               tilePaneToClear.setStyle("-fx-background-color: #28a2db");
                            }
                            //If the piece exists, but is not covered
                            else if(ourModel.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getExists()){
                                //If it's a hint tile
                                if(ourModel.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getHint()) {
                                    tilePaneToClear.setStyle("-fx-background-color: orange");
                                }
                                //If it's a regular piece
                                else{
                                    tilePaneToClear.setStyle("-fx-background-color: white");
                                }
                            }
                            //If the piece does not exist
                            else if(!ourModel.getTile(currentRow + (squareToRemove.getRelRow() * -1), currentColumn + squareToRemove.getRelCol()).getExists()){
                                tilePaneToClear.setStyle("-fx-background-color: black");
                            }
                        }
                    }
                if (DEBUG){
                    System.out.println("Drag Done");
                }
                event.consume();
                }
            });
    }

    /**
     * Gets a node by row and column
     * http://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column used as source
     * @param row row of the desired node
     * @param column column of the desired node
     * @param gridPane gridpane to search for the node
     * @return
     */
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    /**
     * Sets the level number when loading a level
     * @param level level number to be set
     */
    public void setLevelNumber(int level) {
        this.levelNumber.setText(Integer.toString(level));
    }

    /**
     * Parses the level and sets up certain elements together
     * @param levelNum level number to load
     * @throws IOException
     */
    public void loadLevel(int levelNum) throws IOException {
        // Variables for level information
        // NOTE: will most likely be moved to more global variables
        int lvNum = 0;
        int metric = 0;
        int count = 0;
        ArrayList<Integer> pieces = new ArrayList<>(); 
        ArrayList<String> tiles = new ArrayList<>();

        // Release metrics so that release tiles are added properly
        ReleaseTile redTile[] = new ReleaseTile[6];
        ReleaseTile  greenTile[] = new ReleaseTile[6];
        ReleaseTile  yellowTile[] = new ReleaseTile[6];
        GridSquare redPane[] = new GridSquare[6];
        GridSquare greenPane[] = new GridSquare[6];
        GridSquare yellowPane[] = new GridSquare[6];
        boolean usedSlots[] = new boolean[18];
        Arrays.fill(usedSlots, Boolean.FALSE);

        // Starts at 0 because file begins with ### and will automatically increment
        int readCount = 0; // Determines what part of the files is being parsed

        try {
            // Parsing objects
            // Get filepath for the right level, and then load it in
            String filepath = "../GameLevels/lvl" + levelNum + ".bdsm";
            FileReader input = new FileReader(filepath); // Read in file
            BufferedReader buf = new BufferedReader(input);
            String dataLine;

            // Parse file
            while ((dataLine = buf.readLine()) != null) {
                if (dataLine.contains("###")) { // Go to next section
                    readCount++;
                } else { // Information to parse
                    switch (readCount) {
                        case 1: // LevelModel Number
                            lvNum = Integer.parseInt(dataLine);
                            // NOTE: here we tie into the GUI
                            setLevelNumber(lvNum);
                            //Instantiate the level model in the controller
                            switch (lvNum % 3) {
                                case 1: //Puzzle Level implementation
                                    ourModel = new PuzzleLevelModel(lvNum);
                                    limitLabel.setVisible(true);
                                    allowedLabel.setText("Moves Allowed");
                                    allowedLabel.setVisible(true);
                                    javafx.scene.image.Image puz = new javafx.scene.image.Image("/images/PuzzleIcon.png");
                                    levelIcon.setImage(puz);
                                    break;
                                case 2: //Lightning Level instantiation
                                    ourModel = new LightningLevelModel(lvNum);
                                    limitLabel.setVisible(true);
                                    allowedLabel.setText("Time left");
                                    allowedLabel.setVisible(true);
                                    javafx.scene.image.Image lit = new javafx.scene.image.Image("/images/lightning.png");
                                    levelIcon.setImage(lit);
                                    break;
                                case 0: //Release Level implementation
                                    ourModel = new ReleaseLevelModel(lvNum);
                                    allowedLabel.setVisible(false);
                                    limitLabel.setVisible(false);
                                    clearCountDown();
                                    javafx.scene.image.Image rel = new javafx.scene.image.Image("/images/ReleaseIcon.png");
                                    levelIcon.setImage(rel);
                                    break;
                            }
                            break;
                        case 2: // Metric for time
                            metric = Integer.parseInt(dataLine);
                            switch (lvNum % 3) {
                                case 1: //Puzzle Level Instantiation
                                    ((PuzzleLevelModel) ourModel).setTotalMoves(metric);
                                    limitLabel.setText(Integer.toString(metric));
                                    break;
                                case 2: //Lightning Level Instantiation
                                    ((LightningLevelModel) ourModel).setAllowedTime(metric);
                                    limitLabel.setText(Integer.toString(metric));
                                    startCountDown();
                                    break;
                                default:
                                    limitLabel.setText(null);
                                    break;
                            }
                            break;
                        case 3: // Parsing pieces
                            int pieceID = Integer.parseInt(dataLine);
                            final Piece ourPiece = new PieceFactory().getPiece(pieceID);
                            ourModel.getBullpen().addPiece(ourPiece);

                            final PieceGroup currentPiece = new PieceGroup(ourPiece);
                            // Add to bullpen
                            bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
                            bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
                            bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
                            bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
                            gridH = (ourModel.getBullpen().getPieces().size() + 2 - 1) / 2;

                            currentPiece.getGroup().setOnMousePressed(new EventHandler<MouseEvent>() {
                                public void handle(MouseEvent event) {
                                    if (selectedPiece == ourPiece) {
                                        selectedPiece = null;
                                        currentPiece.getGroup().setEffect(null);
                                    }
                                    else {
                                        if (selectedPiece != null) {
                                            // remove visual effect of previous selected piece
                                            selectedGroup.setEffect(null);
                                        }
                                        selectedPiece = ourPiece;
                                        selectedGroup = currentPiece.getGroup();
                                        System.out.println("piece selected");
                                        Lighting light = new Lighting();
                                        currentPiece.getGroup().setEffect(light);
                                    }
                                }
                            });

                            numberOfPiecesDrawn++;

                            break;

                        case 4: // Parsing tiles
                            tiles.add(dataLine); //Add elements to tile lines
                            String tileLines[] = dataLine.split(" "); //Split data along spaces
                            int[] tileInts = new int[tileLines.length]; //Size the tile ints properly
                            //Iterate over the tileLines
                            for (int i = 0; i < tileLines.length; i++){
                                tileInts[i] = Integer.parseInt(tileLines[i]); //Parse the tiles as ints
                            }
                            //Set values
                            if (DEBUG){
                                System.out.println("We have the board elements");
                            }
                            ArrayList<Pane> tempPaneLine = new ArrayList<>();
                            for (int i = 0; i < columns; i++){ //Iterate over all of the array elemnts
                                int offset; //For appropriate tile elements
                                //Determine what type of tile needs to be set
                                GridSquare tempPane = new GridSquare();
                                if (tileInts[i] == 0) { //Null tile
                                    Tile tempTile = new Tile();
                                    tempTile.setExists(false); //Set that it doesn't exist
                                    tempPane.setStyle("-fx-background-color: black");
                                    ourModel.getBoard().setBoardTile(tempTile, count, i); //Set the tile to be empty there

                                }
                                else if (tileInts[i] == 1 || tileInts[i] == 91) { //Valid blank tile
                                    Tile tempTile = new Tile();
                                    if (tileInts[i] == 91){
                                        tempPane.setStyle("-fx-background-color: orange");
                                        tempTile.setHint(true);
                                    }
                                    else {
                                        tempPane.setStyle("-fx-background-color: white");
                                    }
                                    ourModel.getBoard().setBoardTile(tempTile, count, i);
                                }
                                //Red release tile: 21-26 indicate
                                else if ((tileInts[i] > 20 && tileInts[i] < 27) || (tileInts[i] > 920 && tileInts[i] < 927)){
                                    ReleaseTile tempTile = new ReleaseTile();
                                    if (tileInts[i] > 900) {//definitely a hint
                                        offset = 921;
                                        tempTile.setHint(true);
                                        tempTile.setNum(tileInts[i]-offset + 1);
                                        tempTile.setColor(Color.RED);
                                        tempPane.setStyle("-fx-background-color: orange");
                                        tempPane.setNumber(tileInts[i]-offset + 1);
                                        tempPane.numLabel.setTextFill(Color.RED);
                                    }
                                    else {
                                        offset = 21;
                                        tempTile.setNum(tileInts[i]-offset + 1);
                                        tempTile.setColor(Color.RED);
                                        tempPane.setStyle("-fx-background-color: white");
                                        tempPane.setNumber(tileInts[i]-offset + 1);
                                        tempPane.numLabel.setTextFill(Color.RED);
                                    }
                                    ourModel.getBoard().setBoardTile(tempTile, count, i);
                                    redTile[tileInts[i] - offset] = (ReleaseTile)tempTile;
                                    redPane[tileInts[i] - offset] = tempPane;
                                    redTile[tileInts[i] - offset].setColor(Color.RED);
                                    usedSlots[tileInts[i] - offset] = true;
                                }
                                //Green release tile: 31-36 indicate
                                else if ((tileInts[i] > 30 && tileInts[i] < 37) || (tileInts[i] > 930 && tileInts[i] < 937)) {
                                    ReleaseTile tempTile = new ReleaseTile();
                                    if (tileInts[i] > 900) {//definitely a hint
                                        offset = 931;
                                        tempTile.setNum(tileInts[i]-offset + 1);
                                        tempTile.setColor(Color.RED);
                                        tempTile.setHint(true);
                                        tempPane.setStyle("-fx-background-color: orange");
                                        tempPane.setNumber(tileInts[i]-offset + 1);
                                        tempPane.numLabel.setTextFill(Color.GREEN);
                                    }
                                    else {  //Not a hint
                                        offset = 31;
                                        tempTile.setNum(tileInts[i]-offset + 1);
                                        tempTile.setColor(Color.RED);
                                        tempPane.setStyle("-fx-background-color: white");
                                        tempPane.setNumber(tileInts[i]-offset + 1);
                                        tempPane.numLabel.setTextFill(Color.GREEN);
                                    }
                                    ourModel.getBoard().setBoardTile(tempTile, count, i);
                                    greenTile[tileInts[i] - offset] = (ReleaseTile)tempTile;
                                    greenPane[tileInts[i] - offset] = tempPane;
                                    greenTile[tileInts[i] - offset].setColor(Color.GREEN);
                                    usedSlots[tileInts[i] - offset + 6] = true; //Add 6 because new range of elements
                                }
                                //Yellow release tile: 41-46 indicate
                                else if ((tileInts[i] > 40 && tileInts[i] < 47) || (tileInts[i] > 940 && tileInts[i] < 947)) {
                                    ReleaseTile tempTile = new ReleaseTile();
                                    if (tileInts[i] > 900) {//definitely a hint
                                        offset = 941;
                                        tempTile.setNum(tileInts[i]-offset + 1);
                                        tempTile.setColor(Color.RED);
                                        tempTile.setHint(true);
                                        tempPane.setStyle("-fx-background-color: orange");
                                        tempPane.setNumber(tileInts[i]-offset + 1);
                                        // the goldish color replacing yellow
                                        tempPane.numLabel.setTextFill(Color.web("#d5ae27"));
                                    } else {  //Not a hint
                                        offset = 41;
                                        tempTile.setNum(tileInts[i]-offset + 1);
                                        tempTile.setColor(Color.RED);
                                        tempPane.setStyle("-fx-background-color: white");
                                        tempPane.setNumber(tileInts[i]-offset + 1);
                                        // the goldish color replacing yellow
                                        tempPane.numLabel.setTextFill(Color.web("#d5ae27"));
                                    }
                                    ourModel.getBoard().setBoardTile(tempTile, count, i);
                                    yellowTile[tileInts[i] - offset] = (ReleaseTile) tempTile;
                                    yellowPane[tileInts[i] - offset] = tempPane;
                                    yellowTile[tileInts[i] - offset].setColor(Color.YELLOW);
                                    usedSlots[tileInts[i] - offset + 12] = true; //Add 12 because new range of elements
                                }
                                tilePanes.get(count).get(i).setStyle(tempPane.getStyle());
                                tilePanes.get(count).get(i).setOnMouseClicked(null);
                                tilePanes.get(count).get(i).setOnDragDetected(null);
                                tilePanes.get(count).get(i).setOnDragDone(null);
                                ((GridSquare)tilePanes.get(count).get(i)).clearNumber();
                                // only if a release tile
                                if (tempPane.getNumber() > 0) {
                                    ((GridSquare)tilePanes.get(count).get(i)).setNumber(tempPane.getNumber());
                                    ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().setTextFill(((ReleaseTile)ourModel.getBoard().getBoardTile(count,i)).getColor());
                                    ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().autosize();
                                    ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().setStyle("-fx-font: 40 arial;");
                                    if(((ReleaseTile)ourModel.getBoard().getBoardTile(count,i)).getColor() == Color.YELLOW){
                                        ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().setTextFill(Color.web("#d5ae27"));
                                    }
                                }
                                tilePanes.get(count).get(i).setBorder(new Border(new BorderStroke(Color.BLACK,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                            }
                            count++;
                            break;
                    }
                }
            }

        }
        catch (FileNotFoundException e) {
        }

        if (! (menu.getMaxStars(ourModel.getLevelNum()) > 0)) {//if the player has never achieved at least one star
            forwardLevel.setManaged(false); //hide the forwardlevelbutton
            forwardLevel.setVisible(false);
        }else{
            forwardLevel.setManaged(true); //hide the forwardlevelbutton
            forwardLevel.setVisible(true);
        }
        if(ourModel.getLevelNum()==1){//if its the first level, hide the backlevelbutton
            backLevel.setManaged(false);
            backLevel.setVisible(false);
        }else{
            backLevel.setManaged(true);
            backLevel.setVisible(true);
        }

    }

    /**
     * Starts the count down for the timer
     */
    private void startCountDown() {
        timer = new Timer();
        timeLeft = ((LightningLevelModel)ourModel).getAllowedTime();
        timerActive = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timeLeft--;
                        limitLabel.setText("" + timeLeft);
                        if (timeLeft <= 0) {
                            timer.cancel();
                            timerActive = false;
                            //do this when time is up
                            frozenStars = true; //freeze the star count so that it cannot be changed


                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    /**
     * Starts the count down for the timer
     */
    private void clearCountDown() {
        if(timerActive){
            timer.cancel();
            timerActive = false;
        }

        frozenStars = false;
    }

    /**
     * Updates the number of moves taken and the moves left label
     */
    private void decreaseMovesCount() {
        int movesLeft = Integer.parseInt(limitLabel.getText());
        movesLeft--;
        limitLabel.setText(Integer.toString(movesLeft));
        int movesUsed = ((PuzzleLevelModel)ourModel).getMovesUsed();
        ((PuzzleLevelModel)ourModel).setMovesUsed(movesUsed++);
    }

    /**
     * Update the stars of the game.
     * @throws FileNotFoundException if it can't find its resources
     */
    private void updateStars() throws FileNotFoundException {
        if (DEBUG){
            System.out.println("update stars in levelviewcontroller");
        }
        if (frozenStars) return; //if the stars are frozen (prevented from changing) exit the function
        if (DEBUG) {
            System.out.println("more update stars in levelviewcontroller");
        }
        ourModel.updateStars();
        if(ourModel.getMaxStars()> menu.getMaxStars(ourModel.getLevelNum())){//update max stars for game menu
            menu.setMaxStars(ourModel.getLevelNum(), ourModel.getMaxStars());
        }
        javafx.scene.image.Image fullStar = new javafx.scene.image.Image("/images/fullStar.png");
        javafx.scene.image.Image emptyStar = new javafx.scene.image.Image("/images/emptyStar.png");
        System.out.println(ourModel.getStars());
        switch (ourModel.getStars()) {
            case 0:
                firstStar.setImage(emptyStar);
                secondStar.setImage(emptyStar);
                thirdStar.setImage(emptyStar);
                break;
            case 1:
                firstStar.setImage(fullStar);
                secondStar.setImage(emptyStar);
                thirdStar.setImage(emptyStar);
                break;
            case 2:
                firstStar.setImage(fullStar);
                secondStar.setImage(fullStar);
                thirdStar.setImage(emptyStar);
                break;
            case 3:
                firstStar.setImage(fullStar);
                secondStar.setImage(fullStar);
                thirdStar.setImage(fullStar);
                break;
        }

        if(menu.getMaxStars(ourModel.getLevelNum()) > 0 && ourModel.getLevelNum() < 15){//if the play
            forwardLevel.setManaged(true);
            forwardLevel.setVisible(true);
        }
        if(ourModel.getMaxStars() > 0){
            if(ourModel.getLevelNum()+1>menu.getUnlocked()){ //if the next level is unlocked and the number is greater than the current
                menu.setUnlocked(ourModel.getLevelNum()+1);  //max unlocked level, then correctly set the max unlocked level
            }
        }
        menu.saveGameStats();
    }

    /**
     * Handles moving to the next level
     */
    @FXML
    public void handleForwardLevel() {
        ourModel.getBullpen().clearPieces();
        bullpenView.getChildren().clear();
        numberOfPiecesDrawn= 0;
        try {
            loadLevel(ourModel.getLevelNum() + 1);
        } catch (Exception e) {
            System.out.println("couldnt load level");
        }
        try {
            updateStars();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles moving to the next level
     */
    @FXML
    public void handleBackLevel() {
        try {
            if(ourModel.getLevelNum()%3==2){
                timer.cancel();
            }
            ourModel.getBullpen().clearPieces();
            bullpenView.getChildren().clear();
            numberOfPiecesDrawn= 0;
            loadLevel(ourModel.getLevelNum() - 1);
            updateStars();
        } catch (Exception e) {
            System.out.println("couldnt load level");
        }
    }

    /**
     * Sets the GameMenu of this object
     * @param menu new game menu
     */
    public void setMenu(GameMenu menu){
        this.menu = menu;
    }

    /**
     * Handles Reset Button Actions
     */
    @FXML
    public void handleResetButtonAction(){
        try {
            //Cancel the timer if we've got a LightningLevel
            if (ourModel.getLevelNum() % 3 == 2){
                timer.cancel();
            }
            ourModel.getBullpen().clearPieces();
            ourModel.getBoard().clearBoard();
            bullpenView.getChildren().clear();
            numberOfPiecesDrawn = 0;
            loadLevel(ourModel.getLevelNum());
            updateStars();
            frozenStars = false;


        }
        catch (Exception e) {
            System.out.println("Could not reset level.");
        }
    }

}