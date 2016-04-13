package KabasujiModel;

/**
 * Created by Arthur on 4/11/2016.
 */
public class GameMenu {

    int levelNumber;
    int unlocked;

    public GameMenu(){
        levelNumber = 1;
        // max level unlocked
        unlocked =1;
    }

    public int getLevelNumber(){
        return levelNumber;
    }
    public void setLevelNumber(int i){
        this.levelNumber = i;
    }

    public void decrementLevelNumber(){
        this.levelNumber --;
        if (this.levelNumber <1){
            this.levelNumber =15;
        }
    }
    public void incrementLevelNumber(){
        this.levelNumber ++;
        if (this.levelNumber >15){
            this.levelNumber =1;
        }
    }

    public int getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }
}
