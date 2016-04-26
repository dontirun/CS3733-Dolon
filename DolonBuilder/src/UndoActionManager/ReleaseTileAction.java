package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static BuilderControllers.BoardController.*;


/**
 * Created by Walter on 4/24/2016.
 */
public class ReleaseTileAction implements IAction {

    ReleaseTile tile;
    GridSquare pane;
    Color bw;
    Color affectedColor;
    boolean affected;
    ArrayList<ReleaseTile> affectedColorTiles;
    ArrayList<GridSquare> affectedColorPanes;
    int discardIndex;

    public ReleaseTileAction(ReleaseTile tile, GridSquare pane, Color color) {

        this.tile = tile;
        this.pane = pane;
        bw = color;

    }

    @Override
    public boolean doAction() {
        affectedColor = tile.getColor();
        if (affectedColor != Color.WHITE ) {
            affected = true;
            affectedColorTiles = getColorNumTiles(affectedColor);
            affectedColorPanes = getColorNumPanes(affectedColor);

        }
        if(isValid()) {
            if(bw == Color.WHITE){
                tile.setExists(true);
            }
            if(bw == Color.BLACK) {
                if(!affected) {
                    tile.setExists(false);
                }else{
                    for(int i = 0 ; i< affectedColorTiles.size(); i++) {
                        if(affectedColorTiles.get(i)== tile){
                            discardIndex = i;
                            break;
                        }
                    }
                    if(tile == null){
                        System.out.println("!!! tile not found in affectedColorTiles");
                    }
                    affectedColorTiles.remove(discardIndex);
                    affectedColorPanes.remove(discardIndex);

                    tile.setColor(Color.WHITE);
                    pane.getNumLabel().setText("");
                    redrawPane();
                    updateColorNums(affectedColorTiles, affectedColorPanes);
                }
            }
            redrawPane();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public boolean undoAction() {
        if(bw == Color.WHITE){
            tile.setExists(false);
        }
        if(bw == Color.BLACK) {
            if(!affected) {
                tile.setExists(true);
            }else{

                affectedColorTiles.add(discardIndex, tile);
                affectedColorPanes.add(discardIndex, pane);
                tile.setColor(affectedColor);//!!!!!
//                pane.getNumLabel().setText("");
                updateColorNums(affectedColorTiles, affectedColorPanes);
            }
        }
        redrawPane();
        return true;
    }

    @Override
    public boolean redoAction() {
        if(bw == Color.WHITE){
            tile.setExists(true);
        }
        if(bw == Color.BLACK) {
            if(!affected) {
                tile.setExists(false);
            }else{
                if(tile == null){
                    System.out.println("!!! tile not found in affectedColorTiles");
                }
                affectedColorTiles.remove(discardIndex);
                affectedColorPanes.remove(discardIndex);

                tile.setColor(Color.WHITE);
                pane.getNumLabel().setText("");
                redrawPane();
                updateColorNums(affectedColorTiles, affectedColorPanes);
            }
        }
        redrawPane();
        return true;
    }

    @Override
    public boolean isValid() {

        boolean validation= true;
        if(bw == Color.WHITE && tile.getExists() ==true){
            validation = false;
        }
        if(bw == Color.BLACK && tile.getExists() ==false){
            validation = false;
        }

        return validation;
    }

    public void redrawPane() {
        if (tile.getExists() == true) {
            System.out.println("white");
            pane.setStyle("-fx-background-color: white");
            pane.setStyle("-fx-border-color: black");
        } else {
            System.out.println("black");
            pane.setStyle("-fx-background-color: black");
        }
    }

}
