package BuilderModel;

import java.io.Serializable;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Square implements Serializable {
    int relCol;
    int relRow;

    /**
     * Constructor for the Square
     *
     * @param relCol relative column
     * @param relRow relative row
     */
    public Square(int relCol, int relRow){
        this.relCol = relCol;
        this.relRow = relRow;
    }

    /**
     * Getter for relative column
     *
     * @return relative column
     */
    public int getRelCol() {
        return relCol;
    }

    /**
     * Setter for relative column
     *
     * @param relCol relative column
     */
    public void setRelCol(int relCol) {
        this.relCol = relCol;
    }

    /**
     * Getter for relative row
     *
     * @return relative row
     */
    public int getRelRow() {
        return relRow;
    }

    /**
     * Setter for relative row
     *
     * @param relRow relative row
     */
    public void setRelRow(int relRow) {
        this.relRow = relRow;
    }
}
