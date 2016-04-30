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

    public void setAllowedTime(int allowedTime){
        this.allowedTime = allowedTime;
    }

    public int getAllowedTime() {
        return allowedTime;
    }
}
