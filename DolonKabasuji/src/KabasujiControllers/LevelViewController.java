package KabasujiControllers;

import PieceFactory.PieceFactory;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.Button;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
    Timer timer;
    int timeLeft;
    boolean placed = false;

    private final static DataFormat pieceShape = new DataFormat("piece");
    private final static double squareWidth = 45.8333333;
    PieceFactory ourPieceFactory;

    // max rows and columns, might need to be changed
    int rows = 12;
    int columns = 12;

    int gridW = 2;
    int gridH = 18;
    double RectangleSize = 45.83333333;
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
            ColumnConstraints column = new ColumnConstraints(45.8333333);
            boardView.getColumnConstraints().add(column);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(45.8333333);
            boardView.getRowConstraints().add(row);
        }

        // Set grid (will later look at the grid for the level and set it that way)
        //Iterate over the board and draw everything appropriate
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                final Pane pane = new Pane();
                pane.setMinSize(0, 0);
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, i, j);
                //In case something is dragged over the pane
                pane.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        // need to add something to prevent adding to an occupied tile
                        if (event.getGestureSource() != pane && event.getDragboard().hasContent(pieceShape)) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                        event.consume();
                    }
                });

                pane.setOnDragEntered(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        if (event.getGestureSource() != pane &&
                                event.getDragboard().hasContent(pieceShape)) {
                            int currentRow = GridPane.getRowIndex(pane);
                            int currentColumn = GridPane.getColumnIndex(pane);
                            Piece droppedPiece = (Piece) db.getContent(pieceShape);
                            for (Square selectedSquare : droppedPiece.squares) {
                                getNodeByRowColumnIndex(currentRow + selectedSquare.getRelRow(), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: #ffacb1");
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
                                getNodeByRowColumnIndex(currentRow + selectedSquare.getRelRow(), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: WHITE");
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
                            for (Square selectedSquare : droppedPiece.squares) {
                                getNodeByRowColumnIndex(currentRow + selectedSquare.getRelRow(), currentColumn + selectedSquare.getRelCol(), boardView).setStyle("-fx-background-color: RED");
                            }
                            success = true;
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
        int rows = 0;
        int columns = 0;
        int metric = 0;
        ArrayList<Integer> pieces = new ArrayList<Integer>();
        ArrayList<String> tiles = new ArrayList<String>();

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
        } catch (NullPointerException e){
            return;
        }

        switch(lvNum%3){
            case 1://puzzle
                allowedLabel.setText("Moves Allowed");
                limitLabel.setText("");
                javafx.scene.image.Image puz = new javafx.scene.image.Image("/images/PuzzleIcon.png");
                levelIcon.setImage(puz);
                break;
            case 2://lightning
                allowedLabel.setText("Time left");
                startCountDown();
                javafx.scene.image.Image lit = new javafx.scene.image.Image("/images/lightning.png");
                levelIcon.setImage(lit);
                break;
            case 0://release
                allowedLabel.setText("");
                limitLabel.setText("");
                javafx.scene.image.Image rel = new javafx.scene.image.Image("/images/ReleaseIcon.png");
                levelIcon.setImage(rel);
                break;

        }

        // NOTE: here we tie into the GUI
        setLevelNumber(lvNum);

        // TODO: Must tie in metric

        // Set the pieces given for the board
        ourPieceFactory = new PieceFactory(); // Generate pieceFactory
        for (int i : pieces) {
            generateShapeFromPiece(ourPieceFactory.getPiece(i));
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
                    /*
                    level.getBoardTiles().get(count).get(i).setExists(false);
                    tilePanes.get(count).get(i).setStyle("-fx-background-color: black");
                    */

                } else if (tileInts[i] == 1) { // Valid blank tile
                    /*
                    level.getBoardTiles().get(count).get(i).setExists(true);
                    tilePanes.get(count).get(i).setStyle("-fx-background-color: white");
                    tilePanes.get(count).get(i).setStyle("-fx-border-color: black");
                    */

                } else if (tileInts[i] > 20 && tileInts[i] < 27) { // Red release tile: 21-26 indicate the number on the tile
                    /*
                    redTile[tileInts[i]-21] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                    redTile[tileInts[i]-21].setExists(true); // Set to valid tile
                    redTile[tileInts[i]-21].setColor(Color.RED); // Set color
                    redPane[tileInts[i]-21] = tilePanes.get(count).get(i);
                    usedSlots[tileInts[i]-21] = true; // Set slot to used
                    */

                } else if (tileInts[i] > 30 && tileInts[i] < 37) { // Green release tile: 31-36 indicate the number on the tile.
                    /*
                    greenTile[tileInts[i]-31] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                    greenTile[tileInts[i]-31].setExists(true); // Set to valid tile
                    greenTile[tileInts[i]-31].setColor(Color.GREEN); // Set color
                    greenPane[tileInts[i]-31] = tilePanes.get(count).get(i);
                    usedSlots[tileInts[i]-25] = true; // Set slot to used
                    */

                } else if (tileInts[i] > 40 && tileInts[i] < 47) { // Yellow release tile: 41-46 indicate the number on the tile.
                    /*
                    yellowTile[tileInts[i]-41] = (ReleaseTile)level.getBoardTiles().get(count).get(i);
                    yellowTile[tileInts[i]-41].setExists(true); // Set to valid tile
                    yellowTile[tileInts[i]-41].setColor(Color.YELLOW); // Set color
                    yellowPane[tileInts[i]-41] = tilePanes.get(count).get(i);
                    usedSlots[tileInts[i]-29] = true;
                    */

                }
            }

            // Increment count
            count++;
        }

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
        timeLeft = 10;
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

}