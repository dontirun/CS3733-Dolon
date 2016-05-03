package KabasujiModel;

import javafx.scene.paint.Color;

import java.util.ArrayList;

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
        int coveredRed = 0;
        int coveredGreen = 0;
        int coveredYellow = 0;
        for (ArrayList<Tile> a : board.tiles){ //Iterate over all the rows
            for (Tile t : a) { //Iterate over all the columns
                try{
                ReleaseTile r = (ReleaseTile)t;
                if(r.getCovered()!= -1){ //if tile covered by a piece
                    if(r.getColor()== Color.RED){
                        coveredRed++;
                    }else if(r.getColor()==Color.GREEN){
                        coveredGreen++;
                    }else if(r.getColor()==Color.YELLOW){
                        coveredYellow++;
                    }
                }}catch(Exception e){
                    //System.out.println("could not cast to release tile while updating stars");
                }
            }
        }


        int stars = 0;
        //add a star for each full set
        if(coveredRed==6) stars++;
        if(coveredGreen==6) stars++;
        if(coveredYellow==6) stars++;
        this.stars= stars;

        if(stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }
        //hack

        return true;
    }
}
