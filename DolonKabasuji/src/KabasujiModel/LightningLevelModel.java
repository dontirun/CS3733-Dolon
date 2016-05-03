package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class LightningLevelModel extends LevelModel {
    int allowedTime, usedTime;

    /**
     * Constructor for the lightning level
     *
     * @param levelNum level number
     *
     */
    public LightningLevelModel(int levelNum, int allowedTime){
        super(levelNum);
        this.allowedTime = allowedTime;
    }
    public LightningLevelModel(int levelNum){
        super(levelNum);
        this.allowedTime = 10;
    }

    /**
     * Set the amount of time allowed
     * @param allowedTime
     */
    public void setAllowedTime(int allowedTime){
        this.allowedTime = allowedTime;
    }

    /**
     * Gets the amount of time allowed
     * @return
     */
    public int getAllowedTime() {
        return allowedTime;
    }

    @Override
    /**
     * Updates star count
     * @return true if successful
     */
    public boolean updateStars() {
        int tilesNotCovered = board.tilesOnBoard()-board.piecesOnBoard.size()*6;
        if(tilesNotCovered==0){
            stars=3;
        }else if(tilesNotCovered<=6){
            stars = 2;
        }else if (tilesNotCovered<=12){
            stars =1;
        }else{
            stars = 0;
        }

        if(stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }

        return true;
    }
}
