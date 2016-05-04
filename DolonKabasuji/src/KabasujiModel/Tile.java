package KabasujiModel;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Arun Donti, andonti@wpi.edu
 * Models the abstraction of a Tile for use in a Board
 */
public class Tile {

    Square square;
    boolean exists;
    int covered;
    boolean hint;

    /**
     * Constructor for the tile
     */
    public Tile() {
        exists = true;
        covered = -1; //Covered represents the unique piece ID
        hint = false;
    }

    /**
     * Constructor for the tile
     * Can set its existence
     * @param exists whether the tile exists or not
     */
    public Tile(boolean exists){
        this.exists = exists;
        covered = -1;
        hint = false;
    }

    /**
     * Sets the reverse of the tile existing
     */
    public void flipExists(){
        exists=!exists;
    }

    /**
     * Sets the reverse of the tile having a hint.
     */
    public void flipHint(){
        hint = !hint;
    }

    /**
     * Getter for covered
     * @return int representing it being covered
     */
    public int getCovered(){
        return covered;
    }

    /**
     * Getter for if it exists
     * @return true if it exists, false otherwise
     */
    public boolean getExists(){
        return exists;
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
     * Getter for the square of the tile
     * @return square
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Removes the square from the tile
     */
    public void removeSquare() {
        covered = -1;
        this.square = null;
    }

    /**
     * Setter for covered
     * @param covered int representing it being covered, corresponding to unique Piece ID
     */
    public void setCovered(int covered) {
        this.covered = covered;
    }


    /**
     * Sets whether the tile exists or not
     * @param b boolean representing the exists state.
     */
    public void setExists(boolean b){
        exists = b;
    }

    /**
     * Setter for hint
     * @param b boolean representing new hint state.
     */
    public void setHint(Boolean b){
        this.hint = b;
    }

    /**
     * Setter for the square in the tile.
     * @param square square
     * @param covered int representing it being covered
     */
    public void setSquare(Square square, int covered){
        this.covered = covered;
        this.square = square;
    }

}
