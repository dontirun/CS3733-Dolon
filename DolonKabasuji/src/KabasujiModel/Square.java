package KabasujiModel;

import java.io.Serializable;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * Models a square, which are the elements of piece.
 */
public class Square implements Serializable {
    private int relCol, relRow;

    /**
     * Constructor for the square.
     * @param relCol relative column
     * @param relRow relative row
     */
    public Square(int relCol, int relRow){
        this.relCol = relCol;
        this.relRow = relRow;
    }

    /**
     * Getter for the relative column from the anchor
     * @return relative column
     */
    public int getRelCol() {
        return relCol;
    }

    /**
     * Getter for the relative row from the anchor
     * @return relative row
     */
    public int getRelRow() {
        return relRow;
    }

    /**
     * Setter for the relative column  from the anchor
     * @param relCol relative column
     */
    public void setRelCol(int relCol) {
        this.relCol = relCol;
    }

    /**
     * Setter for the relative row from the anchor
     * @param relRow relative row
     */
    public void setRelRow(int relRow) {
        this.relRow = relRow;
    }
}
