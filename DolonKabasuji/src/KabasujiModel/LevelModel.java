package KabasujiModel;

import java.util.ArrayList;

/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Stephen Lafortune, srlafortune@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * Models the specific level, containing all of the necessary information and container for the eele
 */
public abstract class LevelModel {
    int levelNum, maxStars, stars;
    protected boolean DEBUG = false;
    private boolean isUnlocked;
    Bullpen bullpen;
    Board board;

    /**
     * Takes in LevelModel number to configure the level.
     * @param levelNum level to be configured
     */
    public LevelModel(int levelNum){
        this.levelNum = levelNum;
        bullpen = new Bullpen();
        board = new Board();
        //Import then saved number of stars
    }

    /**
     * Returns the tile in a certain row and column.
     * @param row Row of Tile
     * @param col Column of Tile
     * @return tile
     */
    public Tile getTile(int row, int col) {
        return board.getBoardTile(row,col);
    }

    /**
     * Gets the Board of the level.
     * @return The board of the level
     */
    public Board getBoard() { return board; }

    /**
     * Gets the ArrayList of all the Tiles that make up the level.
     * @return ArrayList of ArrayLists of Tile
     */
    public  ArrayList<ArrayList<Tile>>  getBoardTiles(){
        return board.tiles;
    }

    /**
     * Gets the bullpen.
     * @return Bullpen of the model
     */
    public Bullpen getBullpen(){
        return bullpen;
    }

    /**
     * Gets the level number of the level.
     * @return Level Number
     */
    public int  getLevelNum() {
        return levelNum;
    }

    /**
     * Gets the max number of stars.
     * @return Max number of stars
     */
    public int getMaxStars(){
        return maxStars;
    }

    /**
     * Gets all of the pieces on the bullpen.
     * @return pieces on the board
     */
    public ArrayList<Piece> getPiecesOnBoard(){
        return board.piecesOnBoard;
    }

    /**
     * Gets the number of stars obtained for the level
     * @return number of stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * Checks to see if conditions have been satisfied for the player to move on to the next level.
     * @return true if conditions were met, false upon failure
     */
    public boolean hasPassed() {
        return false;
    }

    /**
     * Sets the level number of the model.
     * @param levelNum to set.
     */
    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    /**
     * Sets the unlocked state of this level
     * @param b Unlocked state
     */
    public void setUnlocked(boolean b){
        isUnlocked = b;
    }

    /**
     * Checks to see if board conditions suggest that stars needed to be changed.
     * Returns true if the star count was changed, false otherwise.
     * @return true if stars have been updated
     */
    public boolean updateStars() {
        return false;
    }

}




