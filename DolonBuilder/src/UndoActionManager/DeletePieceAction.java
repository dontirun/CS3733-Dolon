package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderControllers.LevelBuilderController;
import BuilderModel.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Controls deleting piece to the bullpen
 *
 * Created by Walter Ho on 4/26/2016.
 */
public class DeletePieceAction implements IAction{
    Piece piece;
    Group pieceGroup;



    GridPane bullpenView;
    LevelBuilderController lbc;
    LevelModel level;


    /**
     * Deletes piece to the bullpen
     *
     * @param piece piece to be added
     */
    public DeletePieceAction(Piece piece, Group pieceGroup, GridPane bullpenView, LevelBuilderController lbc, LevelModel level) {
        this.piece = piece;
        this.pieceGroup = pieceGroup;
        this.bullpenView = bullpenView;
        this.lbc = lbc;
        this.level = level;
    }

    /**
     * Calls the action of deleting the piece
     *
     * @return true if successful
     */
    @Override
    public boolean doAction() {
        if (piece == null) {
            return false;
        }

        // Remove piece
        bullpenView.getChildren().remove(pieceGroup);
        level.getBullpen().removePiece(piece.getUniqueID());

        lbc.deletePieceFromBullpen();
        return true;
    }

    /**
     * Will undo the action of deleting the piece
     *
     * @return true if successful
     */
    @Override
    public boolean undoAction() {
        return true;
    }

    /**
     * Will redo the action of deleting the piece
     *
     * @return true if successful
     */
    @Override
    public boolean redoAction() {
        return doAction();
    }


    /**
     * Returns true if the action is a valid one
     *
     * @return true if successful
     */
    @Override
    public boolean isValid() {
        return true;
    }
}
