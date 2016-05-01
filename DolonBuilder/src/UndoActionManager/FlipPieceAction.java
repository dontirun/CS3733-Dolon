package UndoActionManager;

import BuilderModel.Bullpen;
import BuilderModel.Piece;

/**
 * Created by slafo on 4/28/2016.
 */
public class FlipPieceAction implements IAction{
    Piece piece;
    Bullpen bullpen;

    /**
     * Constructor
     *
     * @param piece
     * @param bullpen
     * @param direction
     */
    public FlipPieceAction(Piece piece, Bullpen bullpen, char direction) {

        this.piece = piece;
        this.bullpen = bullpen;
    }

    /**
     * Flip the piece
     *
     * @return
     */
    @Override
    public boolean doAction() {
        // need to get piece
        return true;
    }

    /**
     * Undo the flip
     * @return
     */
    @Override
    public boolean undoAction() {
        return false;
    }

    /**
     * Redo the flip
     * @return
     */
    @Override
    public boolean redoAction() {
        return false;
    }

    /**
     * Check if the flip action is valid
     * @return
     */
    @Override
    public boolean isValid() {
        return false;
    }
}
