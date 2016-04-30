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
    public PuzzleLevelModel(int levelNum){
        super(levelNum);
    }

    public PuzzleLevelModel(int levelNum, int totalMoves){
        super(levelNum);
        this.totalMoves = totalMoves;
    }

    public void setTotalMoves(int totalMoves){
        this.totalMoves =  totalMoves;
    }

    public int getTotalMoves(){
        return totalMoves;
    }

}
