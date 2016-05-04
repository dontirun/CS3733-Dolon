package KabasujiModel;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * Models a Lightning Level.
 */
public class LightningLevelModel extends LevelModel {
    int allowedTime = 10; //Default amount of time for the level model
    int usedTime;

    /**
     * Constructor for tLightning Level.
     * @param levelNum Level Number
     */
    public LightningLevelModel(int levelNum){
        super(levelNum);
    }

    /**
     * Secondary constructor for Lightning Level.
     * @param levelNum Level Number
     * @param allowedTime Allowed time for the game
     */
    public LightningLevelModel(int levelNum, int allowedTime){
        super(levelNum);
        this.allowedTime = allowedTime;
    }

    /**
     * Gets the amount of time allowed
     * @return allowedTime integer
     */
    public int getAllowedTime() {
        return allowedTime;
    }

    /**
     * Set the amount of time allowed for a Lightning Level
     * @param allowedTime Allowed Time for the Lightning Level
     */
    public void setAllowedTime(int allowedTime){
        this.allowedTime = allowedTime;
    }

    @Override
    /**
     * Updates the star count based on how many tiles are not covered.
     * @return true if updates were successful, which they always are.
     */
    public boolean updateStars() {
        int tilesNotCovered = board.tilesUncovered();
        if (tilesNotCovered == 0){ //Perfect game
            stars = 3;
        }
        else if (tilesNotCovered <= 6 && tilesNotCovered > 0){ //On their way to greatness
            stars = 2;
        }
        else if (tilesNotCovered <= 12 && tilesNotCovered > 6){ //Barely unlocked the next level
            stars = 1;
        }
        else { //Haven't unlocked enough yet
            stars = 0;
        }

        if (stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }
        return true;
    }
}
