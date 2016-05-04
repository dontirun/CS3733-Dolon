package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderModel.LevelModel;
import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Stack;


/**Handles resize for board resize action
 * Created by Walter on 4/24/2016.
 */
public class ResizeReleaseAction implements IAction {
    boolean[][] start;
    boolean[][] end;

    Stack<ReleaseTileAction> undoHistory;
    Stack<ReleaseTileAction> redoHistory;

    int startCols;
    int startRows;

    TextField rowsTextField;
    TextField colsTextField;

    LevelModel level;
    ArrayList<ArrayList<Tile>> tiles;
    ArrayList<ArrayList<Pane>> panes;

    /**
     * Constructor
     *
     * @param tiles all tiles on the board
     * @param panes all panes on the board
     * @param colsTextField textfield containing cols input
     * @param rowsTextField textfield containing rows input
     */
    public ResizeReleaseAction( ArrayList<ArrayList<Tile>> tiles,  ArrayList<ArrayList<Pane>> panes, TextField colsTextField, TextField rowsTextField) {
        this.tiles = tiles;
        this.panes = panes;
        this.rowsTextField = rowsTextField;
        this.colsTextField = colsTextField;
    }

    /**
     * Resize the board
     *
     * @return true if successful
     */
    @Override
    public boolean doAction() {
        undoHistory = new Stack<ReleaseTileAction>();
        redoHistory = new Stack<ReleaseTileAction>();
        //resize board

        startCols = Integer.parseInt(colsTextField.getText().trim());
        startRows = Integer.parseInt(rowsTextField.getText().trim());
        int cshift = (int) ((12 - startCols) / 2);
        int rshift = (int) ((12 - startRows) / 2);

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (i < startCols + cshift && i >= cshift && j < startRows + rshift && j >= rshift) {
                    ReleaseTileAction rta = new ReleaseTileAction((ReleaseTile) tiles.get(j).get(i), (GridSquare) panes.get(j).get(i), Color.WHITE);

                    if (rta.doAction()) {
                        undoHistory.push(rta);
                        redoHistory.clear();
                    }

                } else {
                    ReleaseTileAction rta = new ReleaseTileAction((ReleaseTile) tiles.get(j).get(i), (GridSquare) panes.get(j).get(i), Color.BLACK);
                    if (rta.doAction()) {
                        undoHistory.push(rta);
                        redoHistory.clear();
                    }
                    ReleaseTileAction rta2 = new ReleaseTileAction((ReleaseTile) tiles.get(j).get(i), (GridSquare) panes.get(j).get(i), Color.BLACK);
                    if (rta2.doAction()) {
                        undoHistory.push(rta2);
                        redoHistory.clear();
                    }

                }
            }
        }

        //redraw it correctly
        //redrawBoard();
        return true;
    }

    /**
     * Undo resizing the board
     * @return true if successful
     */
    @Override
    public boolean undoAction() {
        ReleaseTileAction rta;
        while (undoHistory.size() > 0) {
            rta = undoHistory.pop();
            rta.undoAction();
            redoHistory.push(rta);
        }
        return true;
    }

    /**
     * Redo resizing the board
     * @return true if successful
     */
    @Override
    public boolean redoAction() {
        ReleaseTileAction rta;
        while (redoHistory.size() > 0) {
            rta = redoHistory.pop();
            rta.redoAction();
            undoHistory.push(rta);
        }
        return true;
    }

    /**
     * Check if the action of resizing the board is valid
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        return true;
    }

}