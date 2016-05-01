package UndoActionManager;

import BuilderModel.Bullpen;
import BuilderModel.Piece;

/**
 * Created by slafo on 4/28/2016.
 */
public class RotatePieceAction implements IAction{
    Piece piece;
    Bullpen bullpen;

    /**
     * Constructor
     *
     * @param piece
     * @param bullpen
     * @param direction
     */
    public RotatePieceAction(Piece piece, Bullpen bullpen, char direction) {

        this.piece = piece;
        this.bullpen = bullpen;
    }

    /**
     * Rotate a piece
     *
     * @return
     */
    @Override
    public boolean doAction() {
        // need to get piece
        return true;
    }

    /**
     * Undo rotating a piece
     *
     * @return
     */
    @Override
    public boolean undoAction() {
        return false;
    }

    /**
     * Redo rotating a piece
     *
     * @return
     */
    @Override
    public boolean redoAction() {
        return false;
    }

    /**
     * Check if a rotation is valid
     *
     * @return
     */
    @Override
    public boolean isValid() {
        return false;
    }
}