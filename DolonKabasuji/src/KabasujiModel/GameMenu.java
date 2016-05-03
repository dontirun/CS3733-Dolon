package KabasujiModel;

import java.util.ArrayList;

/**
 * Created by Arthur on 4/11/2016.
 */
public class GameMenu {

    int levelNumber;
    int unlocked;
    int[] maxStars;

    /**
     * Constructor for the game menu
     */
    public GameMenu(){
        levelNumber = 1;
        // max level unlocked
        unlocked =1;
        maxStars = new int[15];
    }

    /**
     * Getter for the level number
     *
     * @return returns the number of the level
     */
    public int getLevelNumber(){
        return levelNumber;
    }

    /**
     * Setter for the level number
     *
     * @param i the number level to be set
     */
    public void setLevelNumber(int i){
        this.levelNumber = i;
    }

    /**
     * Decrements the level number
     */
    public void decrementLevelNumber(){
        this.levelNumber --;
        if (this.levelNumber <1){
            this.levelNumber =15;
        }
    }

    /**
     * Increments the level number
     */
    public void incrementLevelNumber(){
        this.levelNumber ++;
        if (this.levelNumber >15){
            this.levelNumber =1;
        }
    }

    /**
     * Return whether the level is unlocked or not
     *
     * @return unlocked status
     */
    public int getUnlocked() {
        return unlocked;
    }

    /**
     * Sets whether the level is unlocked or not
     *
     * @param unlocked unlocked status
     */
    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }
    public void setMaxStars(int levelNumber, int numStars){
        maxStars[levelNumber-1]= numStars;
    }

    public int getMaxStars(int levelNumber) {
        return maxStars[levelNumber-1];
    }
}
