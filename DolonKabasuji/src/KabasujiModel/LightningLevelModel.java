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
     * @param allowedTime time allowed for the level to be played
     */
    public LightningLevelModel(int levelNum, int allowedTime){
        super(levelNum);
        this.allowedTime = allowedTime;
    }
}
