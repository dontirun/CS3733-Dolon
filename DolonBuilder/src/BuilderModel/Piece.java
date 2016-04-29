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

    /**
     * Sets the unique id of the piece to keep track of it in the bullpen and board
     *
     * @param uniqueID the unique id of the piece
     */
    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int uniqueID;
    public int pieceBoardNum = -1;
    public int DEBUG = 1;
    private double[] colorVals = {0, 0, 1};

    /**
     * Getter for the unique id
     *
     * @return the unique id of the piece
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * Constructor for piece
     *
     * @param pieceID piece id used to construct the piece from the factory
     */
    public Piece(int pieceID){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
    }

    /**
     * Constructor of the piece including color
     *
     * @param pieceID piece id used to construct the piece from the factory
     * @param c color of the piece
     */
    public Piece(int pieceID, Color c){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
    }
    /** rotates piece 90 degrees clockwise
     * Uses rotation matrix [0 1]
     *                     [-1 0]
     *
     * @return true if successful, false otherwise
     */
    public boolean rotatePieceRight(){
        for(Square s: squares){
            s.setRelCol(s.getRelRow()); // Sets column
            s.setRelRow(s.getRelCol() * -1); // Sets row
        }

        return true;
    }

    /** rotates piece 90 degrees counterclockwise
     * Uses rotation matrix [0 -1]
     *                      [1 0]
     *
     * @return true if successful, false otherwise
     */
    public boolean rotatePieceLeft(){
        for(Square s: squares){
            s.setRelCol(s.getRelRow() * -1); // Sets column
            s.setRelRow(s.getRelCol()); // Sets row
        }

        return true;
    }

    /** flips piece horizontally
     *
     * @return returns true if successful, false otherwise
     */
    public boolean flipPieceHoriz(){
        for(Square s: squares){
            s.setRelCol(s.getRelCol() * -1); // Flips across the Y axis (change columns)
        }

        return true;
    }

    /** flips piece vertically
     *
     * @return true if successful, false otherwise
     */
    public boolean flipPieceVert(){
        for(Square s: squares){
            s.setRelRow(s.getRelRow() * -1); // Flips across the X axis (change rows)
        }

        return true;
    }

    /**
     * Getter for id of the piece
     *
     * @return id of the piece
     */
    public int getPieceID() {
        return pieceID;
    }

    /**
     * When ran constructs the piece from the piecefactory?
     */
    protected abstract void construct();

    /**
     * Gets the color of the piece
     *
     * @return color of the piece
     */
    public Color getColor(){
        return Color.color(colorVals[0], colorVals[1], colorVals[2]);
    }

    /**
     * Sets the color of the piece
     *
     * @param c color
     */
    public void setColor(Color c){
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
    }

    /**
     * Getter for piece board num
     *
     * @return the piece board num, forget what this is
     */
    public int getPieceBoardNum() {
        return pieceBoardNum;
    }

    /**
     * Sets the piece board num
     *
     * @param pieceBoardNum piece board num
     */
    public void setPieceBoardNum(int pieceBoardNum){
        this.pieceBoardNum = pieceBoardNum;
    }

}
