package BuilderControllers;

import BuilderModel.ReleaseTile;
import UndoActionManager.ColorAction;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Walter on 4/23/2016.
 */
public class BoardController {
    public static ArrayList<ReleaseTile> redNumTiles;
    public static ArrayList<GridSquare> redNumPanes;
    int startIndex;
    int endIndex;

    LevelBuilderController lbc;

    public BoardController(LevelBuilderController lbc) {
        this.lbc = lbc;
        redNumTiles = new ArrayList<ReleaseTile>();
        redNumPanes = new ArrayList<GridSquare>();
    }

    public void handleBoardClicked(MouseEvent event) {
        //Walter  : ???? why is this try catch here??
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


        ColorAction ca = new ColorAction((ReleaseTile) lbc.level.getTile(col, row), (GridSquare) lbc.tilePanes[col][row], lbc.color);
        if (ca.doAction()) {
            System.out.println("color action performed");
            lbc.undoHistory.push(ca);
            lbc.redoHistory.clear();
        }


        // do nothing

    }

}


