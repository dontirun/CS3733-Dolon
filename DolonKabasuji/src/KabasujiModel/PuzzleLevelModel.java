package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class PuzzleLevelModel extends LevelModel {
    int totalMoves = 0;

    /**
     * Gets the number of moves used
     *
     * @return number of moves used
     */
    public int getMovesUsed() {
        return movesUsed;
    }

    /**
     * Sets the number of moves used
     *
     * @param movesUsed number of moves used
     */
    public void setMovesUsed(int movesUsed) {
        this.movesUsed = movesUsed;
    }

    int movesUsed = 0;

    /**
     * Constructor for puzzle level
     *
     * @param levelNum number of the level
     */
    public PuzzleLevelModel(int levelNum) {
        super(levelNum);
    }

    public PuzzleLevelModel(int levelNum, int totalMoves) {
        super(levelNum);
        this.totalMoves = totalMoves;
    }

    public void setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    @Override
    /**
     * Updates star count
     * @return true if successful
     */
    public boolean updateStars() {
        // don't think this is correct according to specs, supposed to be based on how many pieces are left
        // board.tilesOnBoard()-board.piecesOnBoard.size()*6
        System.out.println(bullpen.getPieces().size());
        switch(bullpen.getPieces().size()){
            case 0:
                stars= 3;
                break;
            case 1:
                stars= 2;
                break;
            case 2:
                stars = 1;
                break;
            default:
                stars = 0;
        }

        if(stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }

        return true;
    }
}
