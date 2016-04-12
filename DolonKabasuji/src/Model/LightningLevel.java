package Model;

/**
 * Created by Arthur on 4/10/2016.
 */
public class LightningLevel extends Level {
    int allowedTime, usedTime;
    public LightningLevel(int levelNum, int allowedTime){
        super(levelNum);
        this.allowedTime = allowedTime;
    }
}
