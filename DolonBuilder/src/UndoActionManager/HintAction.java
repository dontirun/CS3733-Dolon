package UndoActionManager;

import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Handles adding hint status to a tile
 * <p>
 * Created by Walter on 4/24/2016.
 */
public class HintAction implements IAction {

    Tile tile;//affected tile
    Pane pane;//affected pane

    /**Constructor
     * @param tile tile which corresponds with pane clicked
     * @param pane pane clicked
     */
    public HintAction(Tile tile, Pane pane) {
        this.tile = tile;
        this.pane = pane;
    }

    @Override
    /** attempt to blackout (invalidate) or whiteout(validate) tile
     * @return true if successful
     */
    public boolean doAction() {
        if (isValid()) {
            tile.setHint(!tile.getHint());
            redrawPane();
            return true; //action succeeded
        }

        return false;//action failed if not valid

    }

    @Override
    /**
     * undoes the action by inverting the validity of a tile and redrawing its pane
     * @return true if successful
     */
    public boolean undoAction() {
        tile.setHint(!tile.getHint());
        redrawPane();
        return true; //action succeeded
    }

    @Override
    /**
     * redoes the action by inverting the validity of a tile and redrawing its pane
     * @return true if successful
     */
    public boolean redoAction() {
        tile.setHint(!tile.getHint());
        redrawPane();
        return true; //action succeeded
    }

    @Override
    /** returns valid if a whiteout(validate) on a black tile or a blackout(invalidate) on white tile is attempted
     *  actions that dont change the tile are not considered valid
     * @return true if valid
     */
    public boolean isValid() {
        boolean validation = true;
        if (tile.getExists() == false) {
            validation = false;
        }

        return validation;
    }

    /**
     * Redraws pane, setting color to white or black based on tile state
     */
    public void redrawPane() {
        if (tile.getHint()== true) {
            pane.setStyle("-fx-background-color: orange");
           // pane.setStyle("-fx-border-color: black");
        } else {
            pane.setStyle("-fx-background-color: white");
        }
    }
}
