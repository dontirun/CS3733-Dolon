package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderModel.Bullpen;
import BuilderModel.Piece;
import BuilderModel.ReleaseTile;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 * Controls adding piece to the bullpen
 *
 * Created by slafo on 4/26/2016.
 */
public class AddPieceAction implements IAction{
    Piece piece;
    Bullpen bullpen;

    /**
     * Adds piece to the bullpen
     *
     * @param piece piece to be added
     * @param bullpen bullpen for piece to be added to
     */
    public AddPieceAction(Piece piece, Bullpen bullpen) {

        this.piece = piece;
        this.bullpen = bullpen;
    }

    /**
     * Calls the action of adding the piece
     *
     * @return true if successful
     */
    @Override
    public boolean doAction() {
        bullpen.addPiece(piece);
        return true;
    }

    /**
     * Will undo the action of adding the piece
     *
     * @return true if successful
     */
    @Override
    public boolean undoAction() {
        return false;
    }

    /**
     * Will redo the action of adding the piece
     *
     * @return true if successful
     */
    @Override
    public boolean redoAction() {
        return false;
    }


    /**
     * Returns true if the action is a valid one
     *
     * @return true if successful
     */
    @Override
    public boolean isValid() {
        return false;
    }
}
