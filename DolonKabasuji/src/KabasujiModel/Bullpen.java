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

    //Takes in a piece to add to the bullpen
    //Adds a piece to the bullpen
    //Returns true if successfully added piece, false otherwise
    public boolean addPiece(Piece p){
        return false;
    }

    //Takes in a piece to remove from the bullpen
    //Removes a piece from the bullpen
    //Returns true if successfully removes piece, false otherwise
    public boolean removePiece(Piece p){
        return false;
    }
}
