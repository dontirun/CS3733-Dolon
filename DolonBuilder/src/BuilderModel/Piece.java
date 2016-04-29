package BuilderModel;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arthur on 4/10/2016.
 */
public abstract class Piece implements Serializable{
    public Square anchor;
    public ArrayList<Square> squares;
    public int pieceID;

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int uniqueID;
    public int pieceBoardNum = -1;
    public int DEBUG = 1;
    private double[] colorVals = {0, 0, 1};

    public int getUniqueID() {
        return uniqueID;
    }

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
    /** rotates piece 90 degrees clockwise, returns true if successful, false otherwise
     * Uses rotation matrix [0 1]
     *                     [-1 0]
     *
     * @return
     */
    public boolean rotatePieceRight(){
        for(Square s: squares){
            s.setRelCol(s.getRelRow()); // Sets column
            s.setRelRow(s.getRelCol() * -1); // Sets row
        }

        return true;
    }

    /** rotates piece 90 degrees counterclockwise, returns true if successful, false otherwise
     * Uses rotation matrix [0 -1]
     *                      [1 0]
     *
     * @return
     */
    public boolean rotatePieceLeft(){
        for(Square s: squares){
            s.setRelCol(s.getRelRow() * -1); // Sets column
            s.setRelRow(s.getRelCol()); // Sets row
        }

        return true;
    }

    /** flips piece horizontally, returns true if successful, false otherwise
     *
     * @return
     */
    public boolean flipPieceHoriz(){
        for(Square s: squares){
            s.setRelCol(s.getRelCol() * -1); // Flips across the Y axis (change columns)
        }

        return true;
    }

    /** flips piece vertically, returns true if successful, false otherwise
     *
     * @return
     */
    public boolean flipPieceVert(){
        for(Square s: squares){
            s.setRelRow(s.getRelRow() * -1); // Flips across the X axis (change rows)
        }

        return true;
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

    public int getPieceBoardNum() {
        return pieceBoardNum;
    }

    public void setPieceBoardNum(int pieceBoardNum){
        this.pieceBoardNum = pieceBoardNum;
    }

}
