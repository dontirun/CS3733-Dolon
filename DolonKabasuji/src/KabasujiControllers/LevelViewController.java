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

import static java.util.Collections.copy;

/**
 * Created by Arthur on 4/10/2016.
 */
public class LevelViewController implements Initializable {
    @FXML
    Button homeButton;
    @FXML
    Button backLevel;
    @FXML
    Button forwardLevel;
    @FXML
    Label levelNumber;
    @FXML
    Label allowedLabel;
    @FXML
    Label limitLabel;
    @FXML
    GridPane boardView;
    @FXML
    GridPane bullpenView;
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
    @FXML
    Button flipHoriz;
    @FXML
    Button flipVert;
    @FXML
    Button rotateLeft;
    @FXML
    Button rotateRight;
    Timer timer;
    Piece selectedPiece; // For rotation/flipping
    Group selectedGroup; // For rotation/flipping
    GameMenu menu;

    boolean placed = false;
    private int readInLevelNumber = 0;

    ArrayList<ArrayList<Pane>> tilePanes;
    public final static DataFormat pieceShape = new DataFormat("piece");
    private final static double squareWidth = 45.8333333;
    LevelModel ourModel;
    int timeLeft;

    // max rows and columns, might need to be changed
    int rows = 12;
    int columns = 12;

    int gridW = 2;
    int gridH;
    int numberOfPiecesDrawn;

    boolean frozenStars; // true if the star count is unchangable ie timer ran out

    /**
     * Constructor for the controller
     */
    public LevelViewController() {
        numberOfPiecesDrawn = 0;
    }

    /**
     * Handles the home button being pressed
     *
     * @param event action event
     * @throws IOException
     */
    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        // Return to home menu
        if (event.getSource() == homeButton) {

            //menuToLevelController(levelNumber);
            //get reference to the button's stage
            stage = (Stage) homeButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            //
        }
        //create a new scene with root and set the stage

    }

    /**
     * Draws an individual rectangle for a given square (used for GUI elements)
     *
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
     * Handles the action performed when the rotate buttons are pressed
     *
     * @param event action event
     * @throws IOException
     */
    public void handleRotatePieceButtonAction (ActionEvent event) throws IOException {
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
     * Handles the action performed when the rotate buttons are pressed
     *
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

    /**
     * gets called every time the view is loaded
     *
     * @param url
     * @param resourceBundle
     */

    @Override
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

                        // need to add something to prevent adding to an occupied tile
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        Dragboard db = event.getDragboard();
                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getField().isValidMove(droppedPiece, currentRow, currentColumn)) {
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
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getField().isOutOfBounds(droppedPiece, currentRow, currentColumn)) {
                            //System.out.println(droppedPiece.uniqueID);
                            for (Square selectedSquare : droppedPiece.squares) {
                                // Imitate transparency
                                if((ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists() == false)){
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
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getField().isOutOfBounds(droppedPiece, currentRow, currentColumn)) {
                            for (Square selectedSquare : droppedPiece.squares) {
                                //Get the board's view
                                Pane pane = (Pane) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol(), boardView);
                                //
                                if ((ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getCovered() > -1)) {
                                    try {
                                        pane.setStyle("-fx-background-color: rgb(" +
                                            ourModel.getField().getPieceFromID(ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                    currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getRed()*255 + ", " +
                                            ourModel.getField().getPieceFromID(ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                    currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getGreen()*255 + ", " +
                                            ourModel.getField().getPieceFromID(ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1),
                                                    currentColumn + selectedSquare.getRelCol()).getCovered()).getColor().getBlue()*255 + ")");
                                    } catch (PieceNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if ((ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists() == true)) {
                                    if((ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getHint() == true)){
                                        pane.setStyle("-fx-background-color: orange");
                                    }
                                    else{
                                        pane.setStyle("-fx-background-color: white");
                                    }
                                } else if ((ourModel.getField().getBoardTile(currentRow + (selectedSquare.getRelRow() * -1), currentColumn + selectedSquare.getRelCol()).getExists() == false)) {
                                    pane.setStyle("-fx-background-color: black");
                                }
                                event.consume();
                            }
                        }
                    }
                });

                pane.setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);

                        Piece droppedPiece = (Piece) db.getContent(pieceShape);
                        //If we have a piece with us
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getField().isValidMove(droppedPiece, currentRow, currentColumn)) {

                            Color color = droppedPiece.getColor();
                            for (Square selectedSquare : droppedPiece.squares) {
                                GridSquare tilePane = (GridSquare) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView);
                                tilePane.setStyle("-fx-background-color: RED");
                                makeDeletable(tilePane, droppedPiece, currentRow, currentColumn);
                            }
                            ourModel.getField().addPiece(droppedPiece, currentRow, currentColumn);
                            ourModel.getField().printBoardAsDebug();
                            // Only place if it's a valid move
                            success = true;
                            if(ourModel.getLevelNum() % 3==1) {//if its a puzzle level, decrease the moves counter
                                decreaseMovesCount();
                            }
                            ourModel.removePieceFromBullpen(droppedPiece.getUniqueID());
                            bullpenView.getChildren().remove(selectedGroup); // Remove view
                            redrawBullpen();
                            //numberOfPiecesDrawn--;
                            bullpenView.setGridLinesVisible(true);
                            updateStars();


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


        bullpenView.setGridLinesVisible(true);
        // getNodeByRowColumnIndex(0, 0, bullpenView).getTransforms().add(new Rotate(90, 0, 0));
    }

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
            //final Group bullpenViewGroup = new Group(); // Bullpen view group

            /*
            // Draw each square and add it to the bullpen group
            for (Square selectedSquare : p.squares) {
                Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
                selectedRectangle.setFill(p.getColor());
                bullpenViewGroup.getChildren().add(selectedRectangle);
            }
            */

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

    private void makeDeletable(final Node node, final Piece piece, final int row, final int column) {


        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if(button==MouseButton.PRIMARY){
                    // do nothing
                }else if(button==MouseButton.SECONDARY){
                    ourModel.getField().removePiece(piece.getUniqueID());
                    System.out.println("Make deletable column" + column);
                    System.out.println("Make deletable row:" + row);
                    piece.flipPieceVert();
                    for (Square squareToRemove : piece.squares) {
                        GridSquare tilePaneToClear = (GridSquare) getNodeByRowColumnIndex(row + (squareToRemove.getRelRow()*-1), column + squareToRemove.getRelCol(), boardView);
                        //System.out.println(ourModel.getField().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getHint());
                        if ((ourModel.getField().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getCovered() > -1)) {
                            tilePaneToClear.setStyle("-fx-background-color: #28a2db");
                        }
                        else if ((ourModel.getField().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists() == true)) {
                            if((ourModel.getField().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getHint() == true)){
                                tilePaneToClear.setStyle("-fx-background-color: orange");
                            }
                            else{
                                tilePaneToClear.setStyle("-fx-background-color: white");
                            }
                        } else if ((ourModel.getField().getBoardTile(row + (squareToRemove.getRelRow() * -1), column + squareToRemove.getRelCol()).getExists() == false)) {
                            tilePaneToClear.setStyle("-fx-background-color: black");
                        }
                        tilePaneToClear.setOnMouseClicked(null);
                    }

                    // Add piece to bullpen
                    ourModel.getBullpen().addPiece(piece);

                    // Add view to bullpen view
                    final PieceGroup currentPiece = new PieceGroup(piece);
                    bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
                    bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
                    bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
                    bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
                    gridH = (ourModel.getBullpen().getPieces().size() + 2 - 1) / 2;

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
     * Draw a piece on the board given the information about the piece
     *
     * @param pieceToDraw the piece to draw
     */
    private void generateShapeFromPiece(final Piece pieceToDraw) {
        PieceGroup currentPiece = new PieceGroup(pieceToDraw);
        bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
        bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
        bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
        numberOfPiecesDrawn++;
    }

    /**
     * Gets a node by row and column
     * http://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column
     *
     *
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
     *
     * @param level level number to be set
     */
    public void setLevelNumber(int level) {
        this.levelNumber.setText(Integer.toString(level));
    }

    /**
     * Parses the level
     *
     * TODO: Use some set of globals or way to pass values into a level
     *
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
            String filepath = "/levels/lvl" + levelNum + ".bdsm";
            FileReader input = new FileReader(getClass().getResource(filepath).getPath()); // Read in file
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
                                    allowedLabel.setText("Moves Allowed");
                                    javafx.scene.image.Image puz = new javafx.scene.image.Image("/images/PuzzleIcon.png");
                                    levelIcon.setImage(puz);
                                    break;
                                case 2: //Lightning Level instantiation
                                    ourModel = new LightningLevelModel(lvNum);
                                    allowedLabel.setText("Time left");
                                    startCountDown();
                                    javafx.scene.image.Image lit = new javafx.scene.image.Image("/images/lightning.png");
                                    levelIcon.setImage(lit);
                                    break;
                                case 0: //Release Level implementation
                                    ourModel = new ReleaseLevelModel(lvNum);
                                    allowedLabel.setText("");
                                    limitLabel.setText("");
                                    javafx.scene.image.Image rel = new javafx.scene.image.Image("/images/ReleaseIcon.png");
                                    levelIcon.setImage(rel);
                                    break;
                            }
                            break;
                        case 2: // Metric for time
                            metric = Integer.parseInt(dataLine);
                            switch (lvNum % 3) {
                                case 1:
                                    ((PuzzleLevelModel) ourModel).setTotalMoves(metric);
                                    limitLabel.setText(Integer.toString(metric));
                                    break;
                                case 2:
                                    ((LightningLevelModel) ourModel).setAllowedTime(metric);
                                    break;
                            }
                            break;
                        case 3: // Pieces
                            int pieceID = Integer.parseInt(dataLine);
                            final Piece ourPiece = new PieceFactory().getPiece(pieceID);
                            ourModel.addPieceToBullpen(ourPiece);

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

                        case 4: // Tiles

                            tiles.add(dataLine); //Add elements to tile lines
                            String tileLines[] = dataLine.split(" "); //Split data along spaces
                            int[] tileInts = new int[tileLines.length]; //Size the tile ints properly
                            //Iterate over the tileLines
                            for (int i = 0; i < tileLines.length; i++){
                                tileInts[i] = Integer.parseInt(tileLines[i]); //Parse the tiles as ints
                            }
                            //Set values
                            System.out.println("We have the board elements");
                            ArrayList<Pane> tempPaneLine = new ArrayList<>();
                            for (int i = 0; i < columns; i++){ //Iterate over all of the array elemnts
                                int offset; //For appropriate tile elements
                                //Determine what type of tile needs to be set
                                GridSquare tempPane = new GridSquare();
                                if (tileInts[i] == 0) { //Null tile
                                    Tile tempTile = new Tile();
                                    tempTile.setExists(false); //Set that it doesn't exist
                                    tempPane.setStyle("-fx-background-color: black");
                                    ourModel.getField().setBoardTile(tempTile, count, i); //Set the tile to be empty there

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
                                    ourModel.getField().setBoardTile(tempTile, count, i);
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
                                    ourModel.getField().setBoardTile(tempTile, count, i);
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
                                    ourModel.getField().setBoardTile(tempTile, count, i);
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
                                    }
                                    ourModel.getField().setBoardTile(tempTile, count, i);
                                    yellowTile[tileInts[i] - offset] = (ReleaseTile) tempTile;
                                    yellowPane[tileInts[i] - offset] = tempPane;
                                    yellowTile[tileInts[i] - offset].setColor(Color.YELLOW);
                                    usedSlots[tileInts[i] - offset + 12] = true; //Add 12 because new range of elements
                                }
                                tilePanes.get(count).get(i).setStyle(tempPane.getStyle());
                                // only if a release tile
                                if (tempPane.getNumber() > 0) {
                                    ((GridSquare)tilePanes.get(count).get(i)).setNumber(tempPane.getNumber());
                                    ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().setTextFill(((ReleaseTile)ourModel.getField().getBoardTile(count,i)).getColor());
                                    ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().autosize();
                                    ((GridSquare)tilePanes.get(count).get(i)).getNumLabel().setStyle("-fx-font: 40 arial;");
                                    if(((ReleaseTile)ourModel.getField().getBoardTile(count,i)).getColor() == Color.YELLOW){
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
//        catch (NullPointerException e){
//        }
        /* Copied from builder, must be adapted
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
        */
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
        timeLeft = ((LightningLevelModel) ourModel).getAllowedTime();
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
                            //do this when time is up
                            frozenStars = true; //freeze the star count so that it cannot be changed


                        }
                    }
                });
            }
        }, 1000, 1000);
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

    private void updateStars() {
        if(frozenStars) return; //if the stars are frozen (prevented from changing) exit the function
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
        //!!! needs to be fixed
        if(menu.getMaxStars(ourModel.getLevelNum())>0 && ourModel.getLevelNum()<15){//if the play
            forwardLevel.setManaged(true);
            forwardLevel.setVisible(true);
        }
        if(ourModel.getMaxStars()>0){
            if(ourModel.getLevelNum()+1>menu.getUnlocked()){ //if the next level is unlocked and the number is greater than the current
                menu.setUnlocked(ourModel.getLevelNum()+1);  //max unlocked level, then correctly set the max unlocked level
            }
        }
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
        updateStars();
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
    public void setMenu(GameMenu gm){
        menu=gm;
    }

    @FXML
    public void handleResetButtonAction(){
        try {
            ourModel.getBullpen().clearPieces();
            bullpenView.getChildren().clear();
            numberOfPiecesDrawn= 0;
            loadLevel(ourModel.getLevelNum());
            updateStars();
            frozenStars=false;
            if(ourModel.getLevelNum()%3==2){
                timer.cancel();
            }

        } catch (Exception e) {
            System.out.println("couldnt reset level");
        }
    }

}