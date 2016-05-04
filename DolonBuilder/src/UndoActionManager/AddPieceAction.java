package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderControllers.LevelBuilderController;
import BuilderModel.*;
import javafx.scene.Group;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Controls adding piece to the bullpen
 *
 * Created by slafo on 4/26/2016.
 */
public class AddPieceAction implements IAction{
    Piece piece;
    Group pieceGroup;

    GridPane bullpenView;
    LevelBuilderController lbc;
    LevelModel level;


    /**
     * Adds piece to the bullpen
     *
     * @param piece piece to be added
     */
    public AddPieceAction(Piece piece, Group pieceGroup, GridPane bullpenView, LevelBuilderController lbc, LevelModel level) {

        this.piece = piece;
        this.pieceGroup=pieceGroup;
        this.bullpenView=bullpenView;
        this.lbc=lbc;
        this.level = level;
    }

    /**
     * Calls the action of adding the piece
     *
     * @return true if successful
     */
    @Override
    public boolean doAction() {
        if(isValid()) {
            level.getBullpen().addPiece(piece);
            return true;
        }
        return false;
    }

    /**
     * Will undo the action of adding the piece
     *
     * @return true if successful
     */
    @Override
    public boolean undoAction() {
        // Remove piece
        level.getBullpen().removePiece(piece.getUniqueID());
        bullpenView.getChildren().remove(piece);
        lbc.deletePieceFromBullpen();
        level.getBullpen().printBullpen();
        System.out.println("Size of bullpen" + bullpenView.getChildren().toString());
        return true;
    }

    /**
     * Will redo the action of adding the piece
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
