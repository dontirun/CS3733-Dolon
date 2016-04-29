package UndoActionManager;

import BuilderModel.LevelModel;
import BuilderModel.Tile;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 * Resizes a puzzle or lightning board based on texts given in textfields
 * <p>
 * Created by Walter on 4/16/2016.
 */
public class ResizeAction implements IAction {
    boolean[][] start;//startstate of board validity
    boolean[][] end;//endstate of board validity

    int startCols; //number of columns, indicated by columns textfield
    int startRows; //number of rows, indicated by rows textfield

    TextField rowsTextField; //reference to fxml rows textfield
    TextField colsTextField; //reference to fxml cols textfield

    Tile[][] boardTiles; //reference to all tiles on board
    Pane[][] tilePanes; //reference to all panes on board

    /**
     * Creates a resize action
     *
     * @param boardTiles
     * @param tilePanes
     * @param colsTextField
     * @param rowsTextField
     */
    public ResizeAction(Tile[][] boardTiles, Pane[][] tilePanes, TextField colsTextField, TextField rowsTextField) {
        this.boardTiles = boardTiles;
        this.tilePanes = tilePanes;
        this.rowsTextField = rowsTextField;
        this.colsTextField = colsTextField;
    }

    @Override
    /** does the ResizeAction, whiting out all tiles in a rectangle and blacking out all other ones
     * @return true if action is successful
     */
    public boolean doAction() {
        if (isValid()) {
            //record starting state of board validity
            start = new boolean[12][12];
            for (int col = 0; col < 12; col++) {
                for (int row = 0; row < 12; row++) {
                    start[col][row] = boardTiles[col][row].getExists();
                }
            }

            //get correct amount to shift so the valid tiles are centered
            int cshift = (int) ((12 - startCols) / 2);
            int rshift = (int) ((12 - startRows) / 2);

            //set board tiles to valid or invalid
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 12; j++) {
                    if (i < startCols + cshift && i >= cshift && j < startRows + rshift && j >= rshift) {
                        boardTiles[i][j].setExists(true);
                    } else {
                        boardTiles[i][j].setExists(false);
                    }
                }
            }

            //record ending state
            end = new boolean[12][12];
            for (int col = 0; col < 12; col++) {
                for (int row = 0; row < 12; row++) {
                    end[col][row] = boardTiles[col][row].getExists();
                }
            }


            //redraw it correctly
            redrawBoard();
            return true;
        }
        return false;
    }

    @Override
    /** undoes the ResizeAction
     * @return true if successful
     */
    public boolean undoAction() {
        //set board state to the saved board state
        for (int col = 0; col < 12; col++) {
            for (int row = 0; row < 12; row++) {
                boardTiles[col][row].setExists(start[col][row]);
            }
        }
        //reset the text fields
        colsTextField.setText(Integer.toString(startCols));
        rowsTextField.setText(Integer.toString(startRows));
        //redraw it correctly
        redrawBoard();
        return true;
    }

    @Override
    /**
     * redoes the ResizeAction
     */
    public boolean redoAction() {
        //set board state to saved endstate
        for (int col = 0; col < 12; col++) {
            for (int row = 0; row < 12; row++) {
                boardTiles[col][row].setExists(end[col][row]);
            }
        }
        //redraw it correctly
        redrawBoard();
        return true;
    }

    @Override
    public boolean isValid() {
        try {
            //attempt to parse text input into valid numbers
            startCols = Integer.parseInt(colsTextField.getText().trim());
            startRows = Integer.parseInt(rowsTextField.getText().trim());
            if(startCols<0 || startRows<0 || startCols>12 || startRows>12){
                return false;
            }
        } catch (Exception e) {
            //return false if textfield inputs are invalid
            return false;
        }
        return true;
    }

    public void redrawBoard() {
        for (int col = 0; col < 12; col++) {
            for (int row = 0; row < 12; row++) {

                if (boardTiles[col][row].getExists() == true) {
                    tilePanes[col][row].setStyle("-fx-background-color: white");
                } else {
                    tilePanes[col][row].setStyle("-fx-background-color: black");
                }
            }
        }

    }
}
