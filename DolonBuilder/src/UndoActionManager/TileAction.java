package UndoActionManager;

import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by Walter on 4/24/2016.
 */
public class TileAction implements IAction {

    Tile tile;
    Pane pane;
    Color color;

    public TileAction(Tile tile, Pane pane, Color color) {

        this.tile = tile;
        this.pane = pane;
        this.color = color;
    }

    @Override
    public boolean doAction() {
        if(isValid()) {
            if(color == Color.BLACK) {

                //invert the validity of it
                tile.flipExists();
                //redraw it correctly
                redrawPane();
            }
        }
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

        boolean validation= true;
        if(color == Color.WHITE && tile.getExists() ==true){
            validation = false;
        }
        if(color == Color.BLACK && tile.getExists() ==false){
            validation = false;
        }

        return validation;
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
