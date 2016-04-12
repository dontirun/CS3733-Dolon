package Game;

/**
 * Created by Arthur on 4/10/2016.
 */
public class PuzzleTile extends Tile {
    /** returns the square covering it if it has one, null otherwise
     *
     * @return
     */
    public Square isCovered(){
        return super.square;
    }
}
