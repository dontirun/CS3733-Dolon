package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class PuzzleLevelModel extends LevelModel {
    int totalMoves = 0, movesUsed = 0;

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
        switch(board.tilesOnBoard()-board.piecesOnBoard.size()*6){
            case 0:
                stars= 3;
                break;
            case 6:
                stars= 2;
                break;
            case 12:
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
