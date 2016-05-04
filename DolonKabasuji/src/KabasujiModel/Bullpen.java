package KabasujiModel;

import java.util.ArrayList;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * Models the Bullpen of pieces users can select from and have access to
 */
public class Bullpen {
	private boolean DEBUG = false;
    private ArrayList<Piece> pieces;

    /**
     * Constructor for the bullpen
     */
    public Bullpen() {
        pieces = new ArrayList<>();
    }

    /**
     * Adds a piece to the bullpen.
     * @param addedPiece piece to be added
     */
    public void addPiece(Piece addedPiece) {
        if (DEBUG) {
        	System.out.println(addedPiece.getPieceID());
        }
        pieces.add(addedPiece);
    }

    /**
     * Clears all the pieces out of the bullpen.
     */
    public void clearPieces(){
        pieces.clear();
    }

    /**
     * Gets a piece in the bullpen given the piece that was being sought.
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
     * Getter for the piece IDs
     * @return ArrayList of integers representing non-unique IDs
     */
    public ArrayList<Integer> getPieceIDs(){
        ArrayList<Integer> temp = new ArrayList<>();
        //Build the ArrayList of pieces
        for(Piece p: pieces){
            temp.add(p.getPieceID());
        }
        return temp;
    }

    /**
     * Getter for the pieces
     * @return ArrayList of Piece representing the pieces in the bullpen
     */
    // Getter for pieces
    public ArrayList<Piece> getPieces(){
        return pieces;
    }

    /**
     * Removes a piece from the bullpen.
     * @param uniqueID unique id given to the piece
     * @return true if the piece was successfully removed, otherwise false
     */
    public boolean removePiece(int uniqueID) {
        for (Piece p : pieces) {
            if (p.getUniqueID() == uniqueID) {
                pieces.remove(p);
                return true;
            }
        }
        return false;
    }


}