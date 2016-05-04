package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderControllers.LevelBuilderController;
import BuilderModel.Bullpen;
import BuilderModel.Piece;
import BuilderModel.PieceGroup;
import BuilderModel.ReleaseTile;
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
    Bullpen bullpen;
    GridPane bullpenView;
    LevelBuilderController lbc;



    /**
     * Adds piece to the bullpen
     *
     * @param piece piece to be added
     * @param bullpen bullpen for piece to be added to
     */
    public AddPieceAction(Piece piece, Group pieceGroup, Bullpen bullpen, GridPane bullpenView, LevelBuilderController lbc) {

        this.piece = piece;
        this.pieceGroup=pieceGroup;
        this.bullpen = bullpen;
        this.bullpenView=bullpenView;
        this.lbc=lbc;
    }

    /**
     * Calls the action of adding the piece
     *
     * @return true if successful
     */
    @Override
    public boolean doAction() {
        if(isValid()) {
            bullpen.addPiece(piece);
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
        bullpen.removePiece(piece.getUniqueID());
        System.out.println("unique id" + piece.getUniqueID());
        bullpenView.getChildren().remove(piece);
        lbc.deletePieceFromBullpen();
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
