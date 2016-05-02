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

    boolean placed = false;
    private int readInLevelNumber = 0;

    ArrayList<ArrayList<Pane>> tilePanes;
    private final static DataFormat pieceShape = new DataFormat("piece");
    private final static double squareWidth = 45.8333333;
    LevelModel ourModel;
    int timeLeft;

    // max rows and columns, might need to be changed
    int rows = 12;
    int columns = 12;

    int gridW = 2;
    int gridH = 18;
    int numberOfPiecesDrawn;

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
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getField().getBoardTile(currentRow, currentColumn).getExists() &&
                                ourModel.getField().getBoardTile(currentRow, currentColumn).getCovered() < 0) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                        event.consume();
                    }
                });

                pane.setOnDragEntered(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        int currentRow = GridPane.getRowIndex(pane);
                        int currentColumn = GridPane.getColumnIndex(pane);
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape) && ourModel.getField().getBoardTile(currentRow, currentColumn).getExists() &&
                                ourModel.getField().getBoardTile(currentRow, currentColumn).getCovered() < 0) {

                            Piece droppedPiece = (Piece) db.getContent(pieceShape);
                            System.out.println(droppedPiece.uniqueID);
                            for (Square selectedSquare : droppedPiece.squares) {
                                getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: #ffacb1");
                            }
                        }
                        event.consume();
                    }
                });

                pane.setOnDragExited(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        System.out.println(event.isDropCompleted());
                        if (!placed) {
                            Dragboard db = event.getDragboard();
                            int currentRow = GridPane.getRowIndex(pane);
                            int currentColumn = GridPane.getColumnIndex(pane);
                            Piece droppedPiece = (Piece) db.getContent(pieceShape);
                            for (Square selectedSquare : droppedPiece.squares) {
                                Pane pane = (Pane) getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView);
                                event.consume();
                            }
                        }
                        placed = false;
                    }
                });

                pane.setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        //If we have a piece with us
                        if (db.hasContent(pieceShape)) {
                            int currentRow = GridPane.getRowIndex(pane);
                            int currentColumn = GridPane.getColumnIndex(pane);
                            Piece droppedPiece = (Piece) db.getContent(pieceShape);
                            Color color = droppedPiece.getColor();
                            for (Square selectedSquare : droppedPiece.squares) {
                                getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: "+color.toString());
                            }
                            // Only place if it's a valid move
                            if(ourModel.getField().isValidMove(droppedPiece, currentRow, currentColumn)){
                                success = true;
                                ourModel.getField().addPiece(droppedPiece, currentRow, currentColumn);
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


        bullpenView.setGridLinesVisible(true);
        // getNodeByRowColumnIndex(0, 0, bullpenView).getTransforms().add(new Rotate(90, 0, 0));
    }


    /**
     * Draw a piece on the board given the information about the piece
     *
     * @param pieceToDraw the piece to draw
     */
    private void generateShapeFromPiece(final Piece pieceToDraw) {
        PieceGroup currentPiece = new PieceGroup(pieceToDraw, pieceShape);
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
                                    limitLabel.setText("");
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

                            final PieceGroup currentPiece = new PieceGroup(ourPiece, pieceShape);
                            // Add to bullpen
                            bullpenView.add(currentPiece.getGroup(), numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
                            bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
                            bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
                            bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);

                            // when piece is clicked on add it to bullpen
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
                                Tile tempTile = new Tile(); //A tile, defaults to existing
                                Pane tempPane = new Pane();
                                if (tileInts[i] == 0) { //Null tile
                                    tempTile.setExists(false); //Set that it doesn't exist
                                    tempPane.setStyle("-fx-background-color: black");
                                    ourModel.getField().setBoardTile(tempTile, count, i); //Set the tile to be empty there

                                }
                                else if (tileInts[i] == 1 || tileInts[i] == 91) { //Valid blank tile
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
                                    if (tileInts[i] > 900) {//definitely a hint
                                        offset = 921;
                                        tempTile.setHint(true);
                                        tempPane.setStyle("-fx-background-color: orange");
                                    }
                                    else {
                                        offset = 21;
                                        tempPane.setStyle("-fx-background-color: white");
                                    }
                                    ourModel.getField().setBoardTile(tempTile, count, i);
                                    redTile[tileInts[i] - offset] = (ReleaseTile)tempTile;
                                    redPane[tileInts[i] - offset] = tempPane;
                                    redTile[tileInts[i] - offset].setColor(Color.RED);
                                    usedSlots[tileInts[i] - offset] = true;
                                }
                                //Green release tile: 31-36 indicate
                                else if ((tileInts[i] > 30 && tileInts[i] < 37) || (tileInts[i] > 930 && tileInts[i] < 937)) {
                                    if (tileInts[i] > 900) {//definitely a hint
                                        offset = 931;
                                        tempTile.setHint(true);
                                        tempPane.setStyle("-fx-background-color: orange");
                                    }
                                    else {  //Not a hint
                                        offset = 31;
                                        tempPane.setStyle("-fx-background-color: white");
                                    }
                                    ourModel.getField().setBoardTile(tempTile, count, i);
                                    greenTile[tileInts[i] - offset] = (ReleaseTile)tempTile;
                                    greenPane[tileInts[i] - offset] = tempPane;
                                    greenTile[tileInts[i] - offset].setColor(Color.GREEN);
                                    usedSlots[tileInts[i] - offset + 6] = true; //Add 6 because new range of elements
                                }
                                //Yellow release tile: 41-46 indicate
                                else if ((tileInts[i] > 40 && tileInts[i] < 47) || (tileInts[i] > 940 && tileInts[i] < 947)) {
                                    if (tileInts[i] > 900) {//definitely a hint
                                        offset = 941;
                                        tempTile.setHint(true);
                                        tempPane.setStyle("-fx-background-color: orange");
                                    } else {  //Not a hint
                                        offset = 41;
                                        tempPane.setStyle("-fx-background-color: white");
                                    }
                                    ourModel.getField().setBoardTile(tempTile, count, i);
                                    yellowTile[tileInts[i] - offset] = (ReleaseTile) tempTile;
                                    yellowPane[tileInts[i] - offset] = tempPane;
                                    yellowTile[tileInts[i] - offset].setColor(Color.YELLOW);
                                    usedSlots[tileInts[i] - offset + 12] = true; //Add 12 because new range of elements
                                }
                                tilePanes.get(count).get(i).setStyle(tempPane.getStyle());
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

    }

    /**
     * Starts the count down for the timer
     */
    private void startCountDown() {
        timer = new Timer();
        timeLeft = ((LightningLevelModel)ourModel).getAllowedTime();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timeLeft--;
                        limitLabel.setText(""+timeLeft);
                        if(timeLeft<=0){
                            timer.cancel();
                            //do this when time is up

                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    /**
     * Handles moving to the next level
     */
    @FXML
    public void handleForwardLevel(){
        try{
            loadLevel(ourModel.getLevelNum()+1);
        }catch(Exception e){
            System.out.println("couldnt load level");
        }
    }

}