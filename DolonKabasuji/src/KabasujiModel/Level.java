package KabasujiModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Arthur on 4/10/2016.
 */
public abstract class Level {
    int levelNum, maxStars, numStars;
    boolean isUnlocked;
    Bullpen bullpen;
    Board board;

    //Takes in Level number to configure the level.
    //
    public Level(int LevelNum){
        this.levelNum = levelNum;
        bullpen = new Bullpen();
        board = new Board();
        //Import then saved number of stars
    }
    //Takes in a board file string
    //opens the file from relative string name and reads it in, setting up the board properly.
    //Returns true upon success, false upon failure
    public boolean loadBoard(String fileName){
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
        }
        catch (FileNotFoundException fileException) {
            System.out.println("File " + fileName + " not found.");
            return false;
        }

        return false;
    }

    //Checks to see if conditions have been satisfied for the player to move on to the next level.
    //Returns true if conditions were met, false upon failure
    public boolean hasPassed() {
        return false;
    }

    //Not quite sure what this does yet
    /*
    public boolean doMove(Move m){
        return true;
    }
    */

    //Checks to see if board conditions suggest that stars needed to be changed.
    //Returns true if the star count was changed, false otherwise.
    public boolean updateStars() {
        return false;
    }


}
