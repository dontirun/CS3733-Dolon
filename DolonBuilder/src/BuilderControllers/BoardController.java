package BuilderControllers;

import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import UndoActionManager.ColorAction;
import UndoActionManager.TileAction;
import UndoActionManager.TileAction2;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Walter on 4/23/2016.
 */
public class BoardController {
    public static ArrayList<ReleaseTile> redNumTiles;
    public static ArrayList<GridSquare> redNumPanes;
    public static ArrayList<ReleaseTile> greenNumTiles;
    public static ArrayList<GridSquare> greenNumPanes;
    public static ArrayList<ReleaseTile> yellowNumTiles;
    public static ArrayList<GridSquare> yellowNumPanes;

    LevelBuilderController lbc;

    public BoardController(LevelBuilderController lbc) {
        this.lbc = lbc;
        redNumTiles = new ArrayList<ReleaseTile>();
        redNumPanes = new ArrayList<GridSquare>();
        greenNumTiles = new ArrayList<ReleaseTile>();
        greenNumPanes = new ArrayList<GridSquare>();
        yellowNumTiles = new ArrayList<ReleaseTile>();
        yellowNumPanes = new ArrayList<GridSquare>();
    }

    public void handleBoardClicked(MouseEvent event) {
        //Walter: ???? why is this try catch here??
        try {
            // if its not an int don't change the board
            Integer.parseInt(lbc.levelNumber.getText());
        } catch (Exception e) {
            return;
        }
        //get x and y mouse coordinates
        double x = event.getX();
        double y = event.getY();
        //find column and row of tile clicked
        int col = (int) (x / 45.8333333);
        int row = (int) (y / 45.8333333);

        ReleaseTile clickedTile = (ReleaseTile) lbc.level.getTile(col, row);
        GridSquare clickedPane = (GridSquare) lbc.tilePanes[col][row];

        if(lbc.color == Color.BLACK || lbc.color == Color.WHITE){
            TileAction2 ta = new TileAction2(clickedTile, clickedPane, lbc.color);
            if (ta.doAction()) {
                System.out.println("tile action performed");
                lbc.undoHistory.push(ta);
                lbc.redoHistory.clear();
            }

        }else {

            ColorAction ca = new ColorAction(clickedTile, clickedPane, lbc.color);
            if (ca.doAction()) {
                System.out.println("color action performed");
                lbc.undoHistory.push(ca);
                lbc.redoHistory.clear();
            }
        }

        // do nothing

    }

}


