package KabasujiModel;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Created by Arthur on 4/10/2016.
 */
public abstract class Piece implements Serializable{
    public Square anchor;
    public ArrayList<Square> squares;
    public int pieceID;
    public int DEBUG = 1;
    private double[] colorVals = {0, 0, 1};

    public Piece(int pieceID){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
    }

    public Piece(int pieceID, Color c){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
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

    public Color getColor(){
        return Color.color(colorVals[0], colorVals[1], colorVals[2]);
    }

    public void setColor(Color c){
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
    }

}
