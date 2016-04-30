package BuilderModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Tile {

    Square square;
    boolean exists;
    int covered;
    boolean hint;

    public Tile() {
        exists = true;
        covered = -1;
        hint = false;
    }
    public Tile(boolean exists){
        this.exists = exists;
        covered = -1;
        hint = false;
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
     * Getter for hint
     *
     * @return  bool representing it being a hint
     */
    public boolean getHint(){
        return hint;
    }

    /**
     * Setter for hint
     *
     *
     */
    public void setHint(Boolean b){
        this.hint = b;
    }

    /**
     * Sets the reverese of the tile existing
     */
    public void flipHint(){
        hint=!hint;
    }

    /**
     * Setter for covered
     *
     * @param covered int representing it being covered, corresponding to unique Piece ID
     */
    public void setCovered(int covered) {
        this.covered = covered;
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
