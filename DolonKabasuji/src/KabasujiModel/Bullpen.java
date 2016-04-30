package KabasujiModel;

import java.util.ArrayList;
/**
 * Created by Arthur on 4/10/2016.
 */
public class Bullpen {
    ArrayList<Piece> pieces;
    public Bullpen() {
        pieces = new ArrayList<>();
    }
    public Bullpen(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * Takes in a piece to add to the bullpen
     *
     * @param p the piece to be added
     * @return true if successfully added piece, false otherwise
     */
    public boolean addPiece(Piece p){
        return false;
    }

    /**
     * Removes a piece from the bullpen
     *
     * @param p the piece to be removed
     * @return true if successfully removed piece, false otherwise
     */
    public boolean removePiece(Piece p){
        return false;
    }
}
