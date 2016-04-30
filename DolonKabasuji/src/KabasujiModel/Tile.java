package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Tile {

    Square square;
    boolean exists;
    int covered;

    /**
     * Constructor for the tile
     */
    public Tile() {
        exists = true;
        covered = -1;
    }

    /**
     * Constructor for the tile
     * Can set its existence
     *
     * @param exists whether the tile exists or not
     */
    public Tile(boolean exists){
        this.exists = exists;
        covered = -1;
    }

    /**
     * Getter for if it exists
     *
     * @return true if it exists, false otherwise
     */
    public boolean getExists(){
        return exists;
    }

    /**
     * Getter for covered
     *
     * @return int representing it being covered
     */
    public int getCovered(){
        return covered;
    }

    /**
     * Setter for covered
     *
     * @param covered int representing it being covered
     */
    public void setCovered(int covered) {
        this.covered = covered;
    }

    /**
     * Setter for the square in the tile
     *
     * @param square square
     * @param covered int representing it being covered
     */
    public void setSquare(Square square, int covered){
        this.covered = covered;
        this.square = square;
    }

    /**
     * Removes the square from the tile
     */
    public void removeSquare() {
        covered = -1;
        this.square = null;
    }

    /**
     * Getter for the square of the tile
     *
     * @return square
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Sets whether the tile exists or not
     *
     * @param b whether it exists or not
     */
    public void setExists(boolean b){
        exists = b;
    }

    /**
     * Sets the reverese of the tile existing
     */
    public void flipExists(){
        exists=!exists;
    }
}
