package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderModel.LevelModel;
import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Stack;


/** Handles resize for release levels
 * Created by Walter on 4/24/2016.
 */
public class ResizeReleaseAction implements IAction {

    Stack<ReleaseTileAction> undoHistory; //keeps track of all undo moves to undo if undo is called
    Stack<ReleaseTileAction> redoHistory; //keeps track of all redo moves to redo if redo is called

    int startCols; //number of columns, indicated by columns textfield
    int startRows; //number of rows, indicated by rows textfield


    TextField rowsTextField;//reference to fxml rows textfield
    TextField colsTextField;//reference to fxml cols textfield

    LevelModel level;
    Tile[][] tiles;//reference to all tiles on board
    Pane[][] panes;//reference to all panes on board

    public ResizeReleaseAction(Tile[][] tiles, Pane[][] panes, TextField colsTextField, TextField rowsTextField) {
        this.tiles = tiles;
        this.panes = panes;
        this.rowsTextField = rowsTextField;
        this.colsTextField = colsTextField;
    }

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
                    ReleaseTileAction rta = new ReleaseTileAction((ReleaseTile) tiles[i][j], (GridSquare) panes[i][j], Color.WHITE);

                    if (rta.doAction()) {
                        undoHistory.push(rta);
                        redoHistory.clear();
                    }

                } else {
                    ReleaseTileAction rta = new ReleaseTileAction((ReleaseTile) tiles[i][j], (GridSquare) panes[i][j], Color.BLACK);
                    if (rta.doAction()) {
                        undoHistory.push(rta);
                        redoHistory.clear();
                    }
                    ReleaseTileAction rta2 = new ReleaseTileAction((ReleaseTile) tiles[i][j], (GridSquare) panes[i][j], Color.BLACK);
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

    @Override
    public boolean isValid() {
        return true;
    }

}
