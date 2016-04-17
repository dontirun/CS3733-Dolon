package KabasujiModel;

import java.util.ArrayList;

/**
 * Created by Arthur on 4/10/2016.
 */
public abstract class Piece {
    public Square anchor;
    public ArrayList<Square> squares;
    public int pieceID;
    public int DEBUG = 1;

    public Piece(int pieceID){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
    }

    /** rotates piece 90 degrees, returns true if successful, false otherwise
     *
     * @return
     */
    public boolean rotatePiece(){
        return false;
    }

    /** flips piece, returns true if successful, false otherwise
     *
     * @return
     */
    public boolean flipPiece(){
        return false;
    }

    public int getPieceID() {
        return pieceID;
    }

    protected abstract void construct();

}
