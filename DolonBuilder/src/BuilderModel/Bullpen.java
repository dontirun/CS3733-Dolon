package BuilderModel;

import java.util.ArrayList;

/**
 * Created by Walter on 4/16/2016.
 */
public class Bullpen {
    ArrayList<Piece> pieces = new ArrayList<Piece>();

    public Bullpen() {
    }

    public void addPiece(int id) {
        pieces.add(new Piece(id));
    }

    public boolean removePiece(int id) {
        Piece toBeRemoved;
        for (Piece p : pieces) {
            if (p.getPieceID() == id) {
                pieces.remove(p);
                return true;
            }
        }
        return true;
    }

}
