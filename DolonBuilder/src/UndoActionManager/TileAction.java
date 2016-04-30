package UndoActionManager;

import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**Handles blackout (invalidate) and whiteout (validate) of tiles for puzzle and lightning levels
 *
 * Created by Walter on 4/24/2016.
 */
public class TileAction implements IAction {

    Tile tile;//affected tile
    Pane pane;//affected pane
    Color color;//either black or white
    boolean startHint;

    /**
     *
     * @param tile tile which corresponds with pane clicked
     * @param pane pane clicked
     * @param color either black or white, indicating a blackout or whiteout action for a tile
     */
    public TileAction(Tile tile, Pane pane, Color color) {

        this.tile = tile;
        this.pane = pane;
        this.color = color;
    }

    @Override
    /** attempt to blackout (invalidate) or whiteout(validate) tile
     *
     */
    public boolean doAction() {
        if(isValid()) {

                //invert the validity of it
                tile.flipExists();
            if (tile.getHint() == true) {
                tile.setHint(false);
                startHint = true;
            }
                //redraw it correctly
                redrawPane();

            return true; //action succeeded
        }
        return false;//action failed if not valid
    }

    @Override
    /**
     * undoes the action by inverting the validity of a tile and redrawing its pane
     */
    public boolean undoAction() {
        //invert the validity of a tile
        tile.flipExists();
        tile.setHint(startHint);
        //redraw it correctly
        redrawPane();
        return true;
    }

    @Override
    /**
     * redoes the action by inverting the validity of a tile and redrawing its pane
     */
    public boolean redoAction() {
        //invert the validity of it
        tile.flipExists();
        if (tile.getHint() == true) {
            tile.setHint(false);
        }
        //redraw it correctly
        redrawPane();

        return true; //action succeeded
    }

    @Override
    /** returns valid if a whiteout(validate) on a black tile or a blackout(invalidate) on white tile is attempted
     *  actions that dont change the tile are not considered valid
     */
    public boolean isValid() {

        boolean validation= true;
        if(color == Color.WHITE && tile.getExists() ==true){ //validating an already valid tile
            validation = false;
        }
        if(color == Color.BLACK && tile.getExists() ==false){//invalidating an already invalid tile
            validation = false;
        }

        return validation;
    }

    /**Redraws pane, setting color to white or black based on tile state
     *
     */
    public void redrawPane() {
        if (tile.getExists() == true) {
            if(tile.getHint()==true){
                pane.setStyle("-fx-background-color: orange");
            }else {
                pane.setStyle("-fx-background-color: white");
                pane.setStyle("-fx-border-color: black");
            }
        } else {
            pane.setStyle("-fx-background-color: black");
        }
    }
}
