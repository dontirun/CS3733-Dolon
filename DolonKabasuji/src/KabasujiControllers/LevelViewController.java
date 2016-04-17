package KabasujiControllers;

import KabasujiModel.Level;
import PieceFactory.PieceFactory;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import java.lang.Math;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
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

    final DataFormat pieceShape = new DataFormat("piece");

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


        // Initiallize tiles
        PieceFactory ourPieceFactory = new PieceFactory();

        for (int i = 1; i < 36; i++) {
            generateShapeFromPiece(ourPieceFactory.getPiece(i));
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
        bullpenView.setHalignment(pieceGroup, HPos.CENTER);
        bullpenView.setValignment(pieceGroup, VPos.CENTER);
        numberOfPiecesDrawn++;
    }

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
}
