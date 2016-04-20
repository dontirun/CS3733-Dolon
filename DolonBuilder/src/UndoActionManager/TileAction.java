package UndoActionManager;

import BuilderModel.Tile;
import javafx.scene.layout.Pane;

/**
 * Created by Walter on 4/16/2016.
 */
public class TileAction implements IAction {

    Tile tile;
    Pane pane;

    public TileAction(Tile tile, Pane pane) {
        this.tile = tile;
        this.pane = pane;
    }

    @Override
    public boolean doAction() {
        //invert the validity of it
        tile.flipExists();
        //redraw it correctly
        redrawPane();
        return true;
    }

    @Override
    public boolean undoAction() {
        tile.flipExists();
        //redraw it correctly
        redrawPane();
        return true;
    }

    @Override
    public boolean redoAction() {
        return doAction();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public void redrawPane() {
        if (tile.getExists() == true) {
            System.out.println("white");
            pane.setStyle("-fx-background-color: white");
           // pane.setStyle("-fx-border-color: black");
        } else {
            System.out.println("black");
            pane.setStyle("-fx-background-color: black");
        }
    }
}
