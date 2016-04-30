package BuilderModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Tile {

    Square square;
    boolean exists;
    int covered;

    public Tile() {
        exists = true;
        covered = -1;
    }
    public Tile(boolean exists){
        this.exists = exists;
        covered = -1;
    }

    /**
     * Returns whether the tile exists or not
     *
     * @return whether the tile exists
     */
    public boolean getExists(){
        return exists;
    }

    /**
     * Returns whether the tile is covered or not
     *
     * @return status of covered
     */
    public int getCovered(){
        return covered;
    }

    /**
     * Sets the square of the tile
     *
     * @param square the square of a piece
     * @param covered status of this tile being covered
     */
    public void setSquare(Square square, int covered){
        this.covered = covered;
        this.square = square;
    }

    /**
     * Removes the square of the tile
     */
    public void removeSquare() {
        covered = -1;
        this.square = null;
    }

    /**
     * Getter for square
     *
     * @return square
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Sets whether the tile exists or not
     *
     * @param b existence
     */
    public void setExists(boolean b){
        exists = b;
    }

    /**
     * switches whether the tile exists or not
     */
    public void flipExists(){
        exists=!exists;
    }
}
