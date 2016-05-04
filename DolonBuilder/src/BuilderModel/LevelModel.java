package BuilderModel;

import java.util.ArrayList;
import java.util.Queue;

/** Model for level
 * Created by Walter on 4/16/2016.
 */
public class LevelModel {
    Board field;
    Bullpen bullpen;
    int levelNum;
    int movesAllowed;
    int timeAllowed;

    /**
     * Getter for the bullpen
     *
     * @return the bullpen
     */
    public Bullpen getBullpen() {
        return bullpen;
    }

    /**
     * Setter for the bullpen
     *
     * @param bullpen
     */
    public void setBullpen(Bullpen bullpen) {
        this.bullpen = bullpen;
    }




    /**
     * Constructor for the Level
     */
    public LevelModel() {
        field = new Board(12,12);
        bullpen = new Bullpen();
        System.out.println("LevelModel has been constructed");
        // commenting out for now
        /*
        boardTiles = new ReleaseTile[12][12];
        for (int c = 0; c < 12; c++) {
            for (int r = 0; r < 12; r++) {
                boardTiles[c][r] = new ReleaseTile();
            }
        }
        */
    }

    /**
     * Constructor for the Level when given a mode
     *
     * @param mode game mode
     */
    public LevelModel(String mode){
        field = new Board(12,12);
        bullpen = new Bullpen();

        System.out.println("LevelModel has been constructed 2");
        // commenting out for now
        /*
        if(mode.equals("release")){
            boardTiles = new ReleaseTile[12][12];
            for (int c = 0; c < 12; c++) {
                for (int r = 0; r < 12; r++) {
                    boardTiles[c][r] = new ReleaseTile();
                }
            }
        }
        */
    }

    /**
     * Returns the game mode of the level
     *
     * @return game mode
     */
    public String getMode() {
        String result = "none";
        switch (levelNum % 3) {
            case 0:
                result = "puzzle";
                break;
            case 1:
                result = "lightning";
                break;
            case 2:
                result = "release";
                break;
        }
        return result;
    }

    /*
    public boolean getValid(int col, int row) {
        return boardTiles[col][row].exists;
    }

    public void makeInvalid(int col, int row) {
        boardTiles[col][row].setExists(false);
    }

    public void makeValid(int col, int row) {
        boardTiles[col][row].setExists(true);
    }

    public void flipValid(int col, int row) {
        boardTiles[col][row].flipExists();
    }
    */

    /**
     * Returns the tile in a certain row and column
     *
     * @param row row of tile
     * @param col column of tile
     * @return tile at specified row and column
     */
    public Tile getTile(int row, int col) {
        return field.getBoardTile(row,col);
    }

    /**
     * Gets the arraylist of all the tiles that make up the level
     *
     * @return array of tiles
     */
    public  ArrayList<ArrayList<Tile>>  getBoardTiles(){
        return field.tiles;
    }

    /** Gets the board on the level
     * @return returns the board of the level
     */
    public Board getBoard() { return field; }

}
