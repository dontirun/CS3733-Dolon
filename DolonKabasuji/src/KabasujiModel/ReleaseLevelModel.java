package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class ReleaseLevelModel extends LevelModel {
    /**
     * Constructor for release level
     *
     * @param levelNum number of the level
     */
    public ReleaseLevelModel(int levelNum){
        super(levelNum);
    }

    @Override
    /**
     * Updates star count
     * @return true if successful
     */
    public boolean updateStars() {

        if(stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }

        return true;
    }
}
