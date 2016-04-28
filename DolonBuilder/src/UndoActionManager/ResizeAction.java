package UndoActionManager;

import BuilderModel.LevelModel;
import BuilderModel.Tile;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


/**
 * Created by Walter on 4/16/2016.
 */
public class ResizeAction implements IAction {
    boolean[][] start;
    boolean[][] end;

    int startCols;
    int startRows;

    int endCols;
    int endRows;

    TextField rowsTextField;
    TextField colsTextField;

    LevelModel level;
    ArrayList<ArrayList<Tile>>  boardTiles;
    ArrayList<ArrayList<Pane>> tilePanes;

    public ResizeAction(ArrayList<ArrayList<Tile>>  boardTiles, ArrayList<ArrayList<Pane>> tilePanes, TextField colsTextField, TextField rowsTextField) {
        this.boardTiles = boardTiles;
        this.tilePanes=tilePanes;
        this.rowsTextField= rowsTextField;
        this.colsTextField= colsTextField;
    }

    @Override
    public boolean doAction() {
        //record starting state
        // change this later to be dynamic
        start= new boolean[12][12];
        for(int row = 0; row<12; row++){
            for(int col = 0;col<12; col++){
                start[row][col]=boardTiles.get(row).get(col).getExists();
            }
        }
        //resize board
        startCols = Integer.parseInt(colsTextField.getText().trim());
        startRows = Integer.parseInt(rowsTextField.getText().trim());
        int cshift = (int) ((12 - startCols) / 2);
        int rshift = (int) ((12 - startRows) / 2);

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (i < startCols + cshift && i >= cshift && j < startRows + rshift && j >= rshift) {
                    boardTiles.get(j).get(i).setExists(true);
                    //tilePanes[i][j].setStyle("-fx-background-color: white");
                   // tilePanes[i][j].setStyle("-fx-border-color: black");
                } else {
                    boardTiles.get(j).get(i).setExists(false);
                    //tilePanes[i][j].setStyle("-fx-background-color: black");
                }
            }
        }

        //record ending state
        // change this later to be dynamic
        end= new boolean[12][12];
        for(int col = 0; col<12; col++){
            for(int row = 0;row<12; row++){
                end[row][col]=boardTiles.get(row).get(col).getExists();
            }
        }


        //redraw it correctly
        redrawBoard();
        return true;
    }

    @Override
    public boolean undoAction() {
        for(int col = 0; col<12; col++){
            for(int row = 0;row<12; row++){
                boardTiles.get(row).get(col).setExists(start[row][col]);
            }
        }
        colsTextField.setText(Integer.toString(startCols));
        rowsTextField.setText(Integer.toString(startRows));
        //redraw it correctly
        redrawBoard();
        return true;
    }
    @Override
    public boolean redoAction() {
        for(int col = 0; col<12; col++){
            for(int row = 0;row<12; row++){
                boardTiles.get(row).get(col).setExists(end[row][col]);
            }
        }
        //redraw it correctly
        redrawBoard();
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public void redrawBoard() {
        for(int col = 0; col<12; col++){
            for(int row = 0;row<12; row++){

                if (boardTiles.get(row).get(col).getExists() == true) {
                    tilePanes.get(row).get(col).setStyle("-fx-background-color: white");
                 //   tilePanes[col][row].setStyle("-fx-border-color: black");
                } else {
                    tilePanes.get(row).get(col).setStyle("-fx-background-color: black");
                }
            }
        }

    }
}
