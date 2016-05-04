package KabasujiModel;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * Models a Puzzle Level.
 */
public class PuzzleLevelModel extends LevelModel {
    private int totalMoves = 0;
    private int movesUsed = 0;

    /**
     * Gets the number of moves used.
     * @return movesUsed
     */
    public int getMovesUsed() {
        return movesUsed;
    }

    /**
     * Gets the total number of moves available.
     * @return totalMoves
     */
    public int getTotalMoves() {
        return totalMoves;
    }

    /**
     * Constructor for puzzle level
     * @param levelNum Number of the level
     */
    public PuzzleLevelModel(int levelNum) {
        super(levelNum);
    }

    /**
     * Constructor for puzzle level, with total moves featured already
     * @param levelNum Number of the level
     * @param totalMoves Total Moves that can be made
     */
    public PuzzleLevelModel(int levelNum, int totalMoves) {
        super(levelNum);
        this.totalMoves = totalMoves;
    }

    /**
     * Sets the number of moves used.
     * @param movesUsed number of moves used
     */
    public void setMovesUsed(int movesUsed) {
        this.movesUsed = movesUsed;
    }

    /**
     * Sets the total number of moves available.
     * @param totalMoves number of moves available to the player
     */
    public void setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
    }

    @Override
    /**
     * Updates star count based on how many pieces still in the bullpen
     * @return true if successful, which it always is.
     */
    public boolean updateStars() {
        //System.out.println(bullpen.getPieces().size()); //Was for debugging purposes
        switch (bullpen.getPieces().size()){
            case 0:
                stars = 3;
                break;
            case 1:
                stars = 2;
                break;
            case 2:
                stars = 1;
                break;
            default:
                stars = 0;
        }

        if (stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }

        return true;
    }
}
