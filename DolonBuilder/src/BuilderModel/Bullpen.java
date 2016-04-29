package BuilderModel;


import java.util.ArrayList;

/**
 * Created by Walter on 4/16/2016.
 */
public class Bullpen {
    ArrayList<Piece> pieces;

    public Bullpen() {
        pieces = new ArrayList<>();
    }

    public void addPiece(Piece addedPiece) {
        System.out.println(addedPiece.getPieceID());
        pieces.add(addedPiece);
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

    /*
    public Piece getPiece(Piece desiredPiece) {
        for (Piece p : pieces) {
            if (p == desiredPiece) {
                return p;
            }
        }
    }
    */

    // Getter for pieces
    public ArrayList<Piece> getPieces(){
        return pieces;
    }

}
