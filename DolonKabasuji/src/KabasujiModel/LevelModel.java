package KabasujiModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Arthur on 4/10/2016.
 */
public abstract class LevelModel {


    int levelNum, maxStars, numStars;
    boolean isUnlocked;
    Bullpen bullpen;
    Board board;
    int stars;

    /**
     * Takes in LevelModel number to configure the level.
     *
     * @param levelNum level to be configured
     */
    public LevelModel(int levelNum){
        this.levelNum = levelNum;
        bullpen = new Bullpen();
        board = new Board();
        //Import then saved number of stars
    }

    /**
     * Takes in a board file string
     * opens the file from relative string name and reads it in, setting up the board properly.
     * Returns true upon success, false upon failure
     *
     * @param fileName file to load the board from
     * @return
     */

    public boolean loadBoard(String fileName){
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            return true;
        }
        catch (FileNotFoundException fileException) {
            System.out.println("File " + fileName + " not found.");
            return false;
        }

    }

    /**
     * Checks to see if conditions have been satisfied for the player to move on to the next level.
     * Returns true if conditions were met, false upon failure
     *
     * @return returns whether the level has been passed or not
     */

    public boolean hasPassed() {
        return false;
    }

    //Not quite sure what this does yet
    /*
    public boolean doMove(Move m){
        return true;
    }
    */

    /**
     * Checks to see if board conditions suggest that stars needed to be changed.
     * Returns true if the star count was changed, false otherwise.
     *
     * @return true if stars have been updated
     */

    public boolean updateStars() {
        return false;
    }

    /**
     * Returns the tile in a certain row and column
     *
     * @param row row of tile
     * @param col column of tile
     * @return tile
     */
    public Tile getTile(int row, int col) {
        return board.getBoardTile(row,col);
    }

    /**
     * Gets the arraylist of all the tiles that make up the level
     *
     * @return array of tiles
     */
    public  ArrayList<ArrayList<Tile>>  getBoardTiles(){
        return board.tiles;
    }

    /**
     * @return returns the board of the level
     */
    public Board getField() { return board; }

    /**
     * Adds a piece to the bullpen
     * @param p
     */
    public void addPieceToBullpen(Piece p){
        bullpen.addPiece(p);
    }

    /**
     * Gets the bullpen
     * @return
     */
    public Bullpen getBullpen(){
        return bullpen;
    }

    /**
     * Sets the level number of the model
     * @param levelNum
     */
    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    /**
     * Gets the level number of the model
     * @return
     */
    public int  getLevelNum() {
        return levelNum;
    }
}




