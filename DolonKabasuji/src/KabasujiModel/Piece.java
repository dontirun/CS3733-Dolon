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
    public int uniqueID;
    public int DEBUG = 1;
    private double[] colorVals = {1, 0, 0};

    /**
     * Getter for the unique id
     *
     * @return unique id
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * Setter for the unique id
     *
     * @param uniqueID unique id
     */
    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }


    /**
     * Constructor for the piece
     *
     * @param pieceID id of the piece to be generated
     */
    public Piece(int pieceID){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
    }

    /**
     * Constructs the piece with a color
     *
     * @param pieceID id of the piece to be generated
     * @param c color of the piece to be generated
     */
    /* commented out fo rnow because I don't know if we are ever going to hit this
    public Piece(int pieceID, Color c){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
    }
    */
    /** rotates piece 90 degrees clockwise
     * Uses rotation matrix [0 1]
     *                     [-1 0]
     *
     * @return true if successful, false otherwise
     */
    public boolean rotatePieceRight(){
        for(Square s: squares){
            int row = s.getRelRow();
            int column = s.getRelCol();

            s.setRelCol(row); // Sets column
            s.setRelRow(column * -1); // Sets row
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
            int row = s.getRelRow();
            int column = s.getRelCol();

            s.setRelCol(row * -1); // Sets column
            s.setRelRow(column); // Sets row
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
     * @return returns true if successful, false otherwise
     */
    public boolean flipPieceVert(){
        for(Square s: squares){
            s.setRelRow(s.getRelRow() * -1); // Flips across the X axis (change rows)
        }

        return true;
    }

    /**
     * Getter for piece id
     *
     * @return id of the piece
     */
    public int getPieceID() {
        return pieceID;
    }

    /**
     * Constructs the piece
     */
    protected abstract void construct();

    /**
     * Getter for the color of the piece
     *
     * @return color
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


}
