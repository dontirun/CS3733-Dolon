package UndoActionManager;

import BuilderModel.Bullpen;
import BuilderModel.Piece;

/**
 * Created by slafo on 4/28/2016.
 */
public class RotatePieceAction implements IAction{
    Piece piece;
    Bullpen bullpen;

    public RotatePieceAction(Piece piece, Bullpen bullpen, char direction) {

        this.piece = piece;
        this.bullpen = bullpen;
    }

    @Override
    public boolean doAction() {
        // need to get piece
        return true;
    }

    @Override
    public boolean undoAction() {
        return false;
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