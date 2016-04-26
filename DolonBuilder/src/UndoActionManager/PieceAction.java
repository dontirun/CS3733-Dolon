package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderModel.Bullpen;
import BuilderModel.Piece;
import BuilderModel.ReleaseTile;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 * Created by slafo on 4/26/2016.
 */
public class PieceAction implements IAction{
    Piece piece;
    Bullpen bullpen;
    FlowPane bullpenView;

    public PieceAction(Piece piece, Bullpen bullpen) {

        this.piece = piece;
        this.bullpen = bullpen;
    }

    @Override
    public boolean doAction() {
        bullpen.addPiece(piece);
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
