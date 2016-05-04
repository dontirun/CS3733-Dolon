package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderControllers.LevelBuilderController;
import BuilderModel.LevelModel;
import BuilderModel.Piece;
import BuilderModel.PieceGroup;
import BuilderModel.Square;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.logging.Level;

/**
 * Created by Walter on 5/4/2016.
 */
public class DragPieceAction implements IAction {
    LevelModel level;
    Piece droppedPiece;
    GridPane bullpenView;
    Group selectedGroup;
    LevelBuilderController lbc;
    GridPane boardView;
    int currentRow;
    int currentColumn;
    boolean success;

    GridSquare tilePane;//dummy tilePane for undoing
    public DragPieceAction(LevelModel level, Piece droppedPiece, GridPane bullpenView, Group selectedGroup, LevelBuilderController lbc, GridPane boardView, int currentRow, int currentColumn, boolean success) {
        this.level = level;
        this.droppedPiece = droppedPiece;
        this.bullpenView = bullpenView;
        this.selectedGroup = selectedGroup;
        this.lbc = lbc;
        this.boardView = boardView;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
        this.success = success;
    }

    @Override
    public boolean doAction() {

        Color color = droppedPiece.getColor();
        for (Square selectedSquare : droppedPiece.squares) {
            GridSquare tilePane = (GridSquare)lbc.getNodeByRowColumnIndex(currentRow + (selectedSquare.getRelRow()*-1), currentColumn + selectedSquare.getRelCol(), boardView);
            lbc.makeDeletable(tilePane, droppedPiece, currentRow, currentColumn);
            lbc.makeMovable(tilePane, droppedPiece, currentRow, currentColumn);
            this.tilePane = tilePane;

        }
        level.getBoard().removePiece(droppedPiece.getUniqueID());
        level.getBoard().addPiece(droppedPiece, currentRow, currentColumn);
        level.getBoard().printBoardAsDebug();
        // Only place if it's a valid move
        success = true;
        level.getBullpen().removePiece(droppedPiece.getUniqueID());
        // Remove view from bullpen
        bullpenView.getChildren().remove(selectedGroup);
        // Redraw bullpen
        lbc.deletePieceFromBullpen();
        return false;
    }

    @Override
    public boolean undoAction() {
        //MouseButton m = MouseButton.SECONDARY;

        //tilePane.fireEvent(tilePane, new MouseEvent(MouseEvent.MOUSE_CLICKED, MouseButton.SECONDARY));
        //Event.fireEvent(tilePane, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.SECONDARY, 1, true, true, true, true, true, true, true, true, true, true, null));
        /*
        Piece piece = droppedPiece;
        int row = currentRow;
        int column = currentColumn;
        //
        level.getBoard().removePiece(piece.getUniqueID());
        piece.flipPieceVert();
        // Determine the colour to set for the tile
        for(Square squareToRemove : piece.squares){
            GridSquare tilePaneToClear = (GridSquare)lbc.getNodeByRowColumnIndex(row +
                    (squareToRemove.getRelRow()*-1), column + squareToRemove.getRelCol(), boardView);
            tilePaneToClear.setOnDragDetected(null);
            tilePaneToClear.setOnDragDone(null);
            tilePaneToClear.setOnMouseClicked(null);
            lbc.makeTileChangeableAgain(tilePaneToClear);

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
        bullpenView.add(currentPiece.getGroup(), lbc.getNumberOfBullpenPieces() % 2, lbc.getNumberOfBullpenPieces() / 2);
        bullpenView.setMargin(currentPiece.getGroup(), new Insets(10, 10, 10, 10));
        bullpenView.setHalignment(currentPiece.getGroup(), HPos.CENTER);
        bullpenView.setValignment(currentPiece.getGroup(), VPos.CENTER);
        lbc.setGridH ((level.getBullpen().getPieces().size() + 2 - 1) / 2);

        lbc.setNumberOfBullpenPieces(lbc.getNumberOfBullpenPieces()+1);
        */

        return true;
    }

    @Override
    public boolean redoAction() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
