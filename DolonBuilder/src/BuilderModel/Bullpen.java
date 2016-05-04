package BuilderModel;


import java.util.ArrayList;

/**
 * Created by Walter on 4/16/2016.
 */
public class Bullpen {
    ArrayList<Piece> pieces;

    /**
     * Constructor for the bullpen
     */
    public Bullpen() {
        pieces = new ArrayList<>();
    }

    /**
     * Adds a piece to the bullpen
     *
     * @param addedPiece piece to be added
     */
    public void addPiece(Piece addedPiece) {
        System.out.println(addedPiece.getPieceID());
        pieces.add(addedPiece);
    }

    /**
     * Prints out the bullpen
     */
    public void printBullpen() {
        System.out.println("this is what's in the bullpen");
        for (Piece p : pieces) {
            System.out.println("Unique ID"+p.getUniqueID());
        }
    }

    /**
     * @param uniqueid unique id given to the piece
     * @return true if the piece was successfully removed, otherwise false
     */
    public boolean removePiece(int uniqueid) {
        for (Piece p : pieces) {
            if (p.getUniqueID() == uniqueid) {
                pieces.remove(p);
                System.out.println("got to true");
                return true;
            }
        }
        System.out.println("got to false");
        return false;
    }


    /**
     * @param desiredPiece piece to be searched for
     * @return piece that was desired
     * @throws PieceNotFoundException
     */
    public Piece getPiece(Piece desiredPiece) throws PieceNotFoundException {
        for (Piece p : pieces) {
            if (p.getUniqueID() == desiredPiece.getUniqueID()) {
                return p;
            }
        }
        //We didn't get anything; uh oh!
        throw new PieceNotFoundException("Could not find piece with unique ID: " + desiredPiece.getUniqueID());
    }


    /**
     * Getter for the pieces
     *
     * @return arraylist of pieces
     */
    // Getter for pieces
    public ArrayList<Piece> getPieces(){
        return pieces;
    }

    /**
     * Getter for the piece IDs
     *
     * @return arraylist of integers
     */
    public ArrayList<Integer> getPieceIDs(){
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for(Piece p: pieces){
            temp.add(p.getPieceID());
        }

        return temp;
    }

}
