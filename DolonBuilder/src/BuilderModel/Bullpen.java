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

    public boolean removePiece(int uniqueid) {
        Piece toBeRemoved;
        for (Piece p : pieces) {
            if (p.getUniqueID() == uniqueid) {
                pieces.remove(p);
                return true;
            }
        }
        return true;
    }


    public Piece getPiece(Piece desiredPiece) throws PieceNotFoundException {
        for (Piece p : pieces) {
            if (p.getUniqueID() == desiredPiece.getUniqueID()) {
                return p;
            }
        }
        //We didn't get anything; uh oh!
        throw new PieceNotFoundException("Could not find piece with unique ID: " + desiredPiece.getUniqueID());
    }


    // Getter for pieces
    public ArrayList<Piece> getPieces(){
        return pieces;
    }

}
