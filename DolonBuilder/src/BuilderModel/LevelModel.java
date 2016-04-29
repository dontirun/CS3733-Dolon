package BuilderModel;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Walter on 4/16/2016.
 */
public class LevelModel {
    Tile[][] boardTiles;
    Board field;
    Bullpen bullpen;
    int levelNum;
    int movesAllowed;
    int timeAllowed;


    public LevelModel() {
        field = new Board(12,12);
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

    public LevelModel(String mode){
        field = new Board(12,12);
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

    public String getMode() {
        String result = "none";
        switch (levelNum % 3) {
            case 0:
                result = "puzzle";
            case 1:
                result = "lightning";
            case 2:
                result = "release";
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

    public Tile getTile(int row, int col) {
        return field.getBoardTile(row,col);
    }

    public  ArrayList<ArrayList<Tile>>  getBoardTiles(){
        return field.tiles;
    }

    public Board getField() { return field; }

    public void assignRed() {

    }
}
