package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class PuzzleLevel extends Level {
    int totalMoves = 0, movesUsed = 0;

    /**
     * Constructor for puzzle level
     *
     * @param levelNum number of the level
     */
    public PuzzleLevel(int levelNum){
        super(levelNum);
    }
}
