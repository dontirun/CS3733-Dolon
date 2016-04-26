package KabasujiControllers;

import KabasujiModel.Level;
import PieceFactory.PieceFactory;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.event.Event;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.io.*;
import java.lang.Math;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import KabasujiModel.*;
import PieceFactory.*;

import static java.lang.Boolean.FALSE;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_RIGHT;

/**
 * Created by Arthur on 4/10/2016.
 */
public class LevelViewController implements Initializable{
    @FXML
    Button homeButton;
    @FXML
    Button backLevel;
    @FXML
    Button forwardLevel;
    @FXML
    Label levelNumber;
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

    boolean placed = false;

    final static DataFormat pieceShape = new DataFormat("piece");
    PieceFactory ourPieceFactory;

    // max rows and columns, might need to be changed
    int rows = 12;
    int columns = 12;

    int gridW = 2;
    int gridH = 18;
    double RectangleSize = 45.83333333;
    int numberOfPiecesDrawn;
    public LevelViewController(){
        numberOfPiecesDrawn = 0;
    }

    public void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        // Return to home menu
        if(event.getSource() == homeButton){

            //menuToLevelController(levelNumber);
            //get reference to the button's stage
            stage=(Stage) homeButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else{
            //
        }
        //create a new scene with root and set the stage

    }

    // gets called every time the view is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardView.getStyleClass().add("board");

        // Set constraints (size of the cells)
        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(45.8333333);
            boardView.getColumnConstraints().add(column);
        }

        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(45.8333333);
            boardView.getRowConstraints().add(row);
        }

        // Set grid (will later look at the grid for the level and set it that way)
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                final Pane pane = new Pane();
                pane.setMinSize(0, 0);
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
                pane.getStyleClass().add("board-cell");
                boardView.add(pane, i, j);



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


        // Set constraints (size of the cells)
        for(int i = 0; i < gridW; i++) {
            ColumnConstraints column = new ColumnConstraints(45.8333333*6);
            bullpenView.getColumnConstraints().add(column);
        }

        for(int i = 0; i < gridH; i++) {
            RowConstraints row = new RowConstraints(45.8333333*6);
            bullpenView.getRowConstraints().add(row);
        }


        bullpenView.setGridLinesVisible(true);
        // getNodeByRowColumnIndex(0, 0, bullpenView).getTransforms().add(new Rotate(90, 0, 0));
    }



    //Draw a piece on the board given the information about the piece
    void generateShapeFromPiece(final Piece pieceToDraw){
        final Group pieceGroup = new Group();
        for(Square selectedSquare : pieceToDraw.squares){ //Iterate over all of the squares in the piece
            Rectangle selectedRectangle = new Rectangle();
            selectedRectangle.setX((selectedSquare.getRelCol()) * RectangleSize); //Set Y position based on the Relative Column
            selectedRectangle.setY((-selectedSquare.getRelRow()) * RectangleSize); //Set Y position based on the Relative Row
            selectedRectangle.setWidth(RectangleSize); //Set the width of each rectangle
            selectedRectangle.setHeight(RectangleSize); //Set the height of each rectangle
            selectedRectangle.setFill(Color.RED); //Color the fill
            selectedRectangle.setStroke(Color.BLACK); //Color the outline
            pieceGroup.getChildren().add(selectedRectangle);
        }



        pieceGroup.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
                Dragboard db = pieceGroup.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.put(pieceShape, pieceToDraw);
                db.setContent(content);
                event.consume();
            }
        });

        pieceGroup.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
        /* the drag and drop gesture ended */
        /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    pieceGroup.setVisible(FALSE);
                }
                event.consume();
            }
        });

        bullpenView.add(pieceGroup, numberOfPiecesDrawn % 2, numberOfPiecesDrawn / 2);
        bullpenView.setMargin(pieceGroup, new javafx.geometry.Insets(10, 10, 10, 10));
        bullpenView.setHalignment(pieceGroup, HPos.CENTER);
        bullpenView.setValignment(pieceGroup, VPos.CENTER);
        numberOfPiecesDrawn++;
    }

    /*
    Got from someone on stackoverflow
     */

    public Node getNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {
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

    // Sets the level number when loading a level
    public void setLevelNumber(int level){
        this.levelNumber.setText(Integer.toString(level));
    }

    // Level parsing function
    // TODO: Use some set of globals or way to pass values into a level
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
            String filepath = "DolonKabasuji/resources/levels/lvl" + levelNum + ".bdsm";
            FileReader input = new FileReader(filepath); // Read in file
            BufferedReader buf = new BufferedReader(input);
            String dataLine;

            // Parse file
            while((dataLine = buf.readLine()) != null){
                if(dataLine.contains("###")){ // Go to next section
                    readCount++;
                }
                else{ // Information to parse
                    switch(readCount){
                        case 1: // Level Number
                            lvNum = Integer.parseInt(dataLine);
                            break;
                        case 2: // Rows
                            rows = Integer.parseInt(dataLine);
                            break;
                        case 3: // Columns
                            columns = Integer.parseInt(dataLine);
                            break;
                        case 4: // Metric
                            metric = Integer.parseInt(dataLine);
                            break;
                        case 5: // Pieces
                            pieces.add(Integer.parseInt(dataLine));
                            break;
                        case 6: // Tiles
                            tiles.add(dataLine);
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // NOTE: here we tie into the GUI
        setLevelNumber(lvNum);


        // Set board to appropriate size
        int cshift = (int) ((12 - columns) / 2);
        int rshift = (int) ((12 - rows) / 2);

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (i < columns + cshift && i >= cshift && j < rows + rshift && j >= rshift) {
                    //getNodeByRowColumnIndex(j, i, boardView).setExists(true);
                    getNodeByRowColumnIndex(j, i, boardView).setStyle("-fx-background-color: white");
                    //tilePanes[i][j].setStyle("-fx-background-color: white");
                    // tilePanes[i][j].setStyle("-fx-border-color: black");
                } else {
                    //boardTiles[i][j].setExists(false);
                    getNodeByRowColumnIndex(j, i, boardView).setStyle("-fx-background-color: black");
                    //tilePanes[i][j].setStyle("-fx-background-color: black");
                }
            }
        }


        // Set the pieces given for the board
        ourPieceFactory = new PieceFactory(); // Generate pieceFactory
        for(int i: pieces){
            generateShapeFromPiece(ourPieceFactory.getPiece(i));
        }

        // Set the specific tiles of the board (non-square board shapes)
        int count = 0;
        for(String s: tiles){
            // Break up lines and convert to int
            String[] tileLines = s.split(" ");
            int[] tileInts = new int[tileLines.length];

            for(int i = 0; i < tileLines.length; i++){
                tileInts[i] = Integer.parseInt(tileLines[i]);
            }

            // Set values

            // Increment count
            count++;
        }

    }
}
