package KabasujiModel;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * @author Walter Ho, who@wpi.edu
 * Models a Release Level.
 */
public class ReleaseLevelModel extends LevelModel {
    /**
     * Constructor for release level
     * @param levelNum number of the level
     */
    public ReleaseLevelModel(int levelNum){
        super(levelNum);
    }

    @Override
    /**
     * Updates star count based how many sets of colored numbers are completely covered
     * @return true if successful, which it always is
     */
    public boolean updateStars() {
        //System.out.println("update stars in the model for Release Level"); //Was for debugging purposes
        int coveredRed = 0, coveredGreen = 0, coveredYellow = 0;
        for (ArrayList<Tile> a : getBoard().tiles){ //Iterate over all the rows
            for (Tile t : a) { //Iterate over all the columns
                try{ //Catch any exceptions with casting
                    ReleaseTile r = (ReleaseTile)t;
                    if(r.getCovered() != -1) { //if tile covered by a piece
                        if (r.getColor() == Color.RED) {
                            coveredRed++;
                            if (DEBUG) {
                                System.out.println(coveredRed + " Red are covered");
                            }
                        }
                        else if (r.getColor() == Color.GREEN) {
                            coveredGreen++;
                            if (DEBUG) {
                                System.out.println(coveredGreen + " Green are covered");
                            }

                        } else if (r.getColor() == Color.YELLOW) {
                            coveredYellow++;
                            if (DEBUG) {
                                System.out.println(coveredYellow + " Yellow are covered");
                            }

                        }
                    }
                }
                catch(Exception e){
                    if (DEBUG) {
                        //System.out.println("Could not cast to release tile while updating stars."); //Too many print statements to include
                    }
                }
            }
        }
        //Figure out how many stars we have
        int stars = 0;
        //Add a star for each full set
        if (coveredRed == 6) stars++;
        if (coveredGreen == 6) stars++;
        if (coveredYellow == 6) stars++;
        this.stars = stars;

        if(stars > maxStars){ // Update highest star count if we have surpassed the current highest
            maxStars = stars;
        }
        return true;
    }
}
