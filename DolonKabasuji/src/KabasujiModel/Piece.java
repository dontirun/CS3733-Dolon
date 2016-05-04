package KabasujiModel;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Stephen Lafortune, srlafortune@wpi.edu
 * Models the concept of a piece, which is placed on the board and in the bullpen
 */
public abstract class Piece implements Serializable{
    public Square anchor;
    public ArrayList<Square> squares;
    public int pieceID;
    private int uniqueID;
    public int DEBUG = 1;
    private double[] colorVals = {0.157, 0.635, 0.859};

    /**
     * Constructor for the piece.
     * @param pieceID id of the piece to be generated
     */
    public Piece(int pieceID){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
    }

    /**
     * Constructs the piece with a color.
     * @param pieceID id of the piece to be generated
     * @param c color of the piece to be generated
     */
    public Piece(int pieceID, Color c){
        this.pieceID = pieceID;
        squares = new ArrayList<>();
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
    }

    /**
     * Constructs the piece
     */
    protected abstract void construct();

    /**
     * Flips piece horizontally.
     * @return true if successful, false otherwise
     */
    public boolean flipPieceHoriz(){
        //Iterate down the squares
        for(Square s : squares){
            s.setRelCol(s.getRelCol() * -1); // Flips across the Y axis (change columns)
        }
        return true;
    }

    /**
     * Flips piece vertically.
     * @return true if successful, false otherwise
     */
    public boolean flipPieceVert(){
        //Iterate down the squares
        for(Square s : squares){
            s.setRelRow(s.getRelRow() * -1); // Flips across the X axis (change rows)
        }
        return true;
    }

    /**
     * Gets the color of the piece.
     * @return color
     */
    public Color getColor(){
        return Color.color(colorVals[0], colorVals[1], colorVals[2]);
    }

    /**
     * Getter for pieceID
     * @return PieceID
     */
    public int getPieceID() {
        return pieceID;
    }

    /**
     * Gets the uniqueID
     * @return uniqueID of the piece
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * Rotates piece 90 degrees counterclockwise.
     * Uses rotation matrix [0 -1]
     *                      [1 0]
     * @return true if successful, false otherwise
     */
    public boolean rotatePieceLeft(){
        //Iterate down the pieces
        for(Square s : squares){
            int row = s.getRelRow();
            int column = s.getRelCol();
            s.setRelCol(row * -1); // Sets column
            s.setRelRow(column); // Sets row
        }
        return true;
    }

    /**
     * Rotates piece 90 degrees clockwise.
     * Uses rotation matrix [0 1]
     *                     [-1 0]
     * @return true if successful, false otherwise
     */
    public boolean rotatePieceRight(){
        //Iterate down the squares
        for(Square s : squares){
            int row = s.getRelRow();
            int column = s.getRelCol();
            s.setRelCol(row); // Sets column
            s.setRelRow(column * -1); // Sets row
        }
        return true;
    }

    /**
     * Sets the color of the Piece.
     * @param c color to set
     */
    public void setColor(Color c){
        colorVals[0] = c.getRed();
        colorVals[1] = c.getGreen();
        colorVals[2] = c.getBlue();
    }

    /**
     * Sets the uniqueID of the Piece.
     * @param uniqueID of the Piece
     */
    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }


}
