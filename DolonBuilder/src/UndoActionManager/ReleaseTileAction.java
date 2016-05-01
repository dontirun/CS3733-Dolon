package UndoActionManager;

import BuilderControllers.GridSquare;
import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static BuilderControllers.BoardController.*;


/**Handles blackout (invalidate) and whiteout (validate) of tiles for release levels
 * Handles the removal of numbers on white tiles
 *
 * Created by Walter on 4/24/2016.
 */
public class ReleaseTileAction implements IAction {

    ReleaseTile tile;//tile which corresponds with pane
    GridSquare pane;//pane clicked
    Color bw;//either black or white, indication a blackout or whiteout action
    Color affectedColor;//color of the number
    boolean affected;//true if the pane clicked has a number on it
    ArrayList<ReleaseTile> affectedColorTiles; //the array containing tiles with numbers of the affected color
    ArrayList<GridSquare> affectedColorPanes; //the array containing panes with numbers of the affected color
    int discardIndex;
    boolean startHint;

    /**Creates a ReleaseTileAction     *
     * @param tile the tile corresponding with the pane clicked
     * @param pane the pane clicked
     * @param color either black or white, determining a blackout or whiteout action
     */
    public ReleaseTileAction(ReleaseTile tile, GridSquare pane, Color color) {
        this.tile = tile;
        this.pane = pane;
        bw = color;

    }

    /**Does the release tile action.
     * Whites out a black tile, blacks out a white tile, or removes the number from a tile with a number on it
     *
     * @return true if the move is successful, false otherwise
     */
    @Override
    public boolean doAction() {
        if (tile.getHint() == true) {
            tile.setHint(false);
            startHint = true;
        }
        affectedColor = tile.getColor(); //get the color state of the tile
        if (affectedColor != Color.WHITE ) {//if the tile has a colored number on it
            affected = true;
            //get the correct tileset and paneset to modify upon removal
            affectedColorTiles = getColorNumTiles(affectedColor);
            affectedColorPanes = getColorNumPanes(affectedColor);

        }
        if(isValid()) {
            if(bw == Color.WHITE){//whiting out a balck tile
                tile.setExists(true);
            }
            if(bw == Color.BLACK) {
                if(!affected) {//blacking out a white tile without number
                    tile.setExists(false);
                }else{//blacking out a white tile with a number on it
                    for(int i = 0 ; i< affectedColorTiles.size(); i++) {
                        if(affectedColorTiles.get(i)== tile){
                            discardIndex = i; //find the index of the tile and pane in the colored tileset and paneset
                            break;
                        }
                    }
                    if(tile == null){
                        System.out.println("!!! tile not found in affectedColorTiles");
                    }

                    affectedColorTiles.remove(discardIndex); //remove the tile and pane from the arraylists keeping track of the colors
                    affectedColorPanes.remove(discardIndex);

                    tile.setColor(Color.WHITE);//set the tile to a white tile
                    pane.getNumLabel().setText(""); //remove the text from the pane
                    updateColorNums(affectedColorTiles, affectedColorPanes);//redraw the numbering of the colored tile and pane sets
                }
            }
            redrawPane();// redraw the pane color
        }else{
            return false;
        }
        return true;
    }

    /** Undoes the ReleaseTileAction
     *
     * @return true if successful, false otherwise
     */
    @Override
    public boolean undoAction() {
        tile.setHint(startHint);
        if(bw == Color.WHITE){ //if a whiteout action was done, make the tile invalid
            tile.setExists(false);
        }
        if(bw == Color.BLACK) {
            if(!affected) {//if a blackout action was done, make the tile valid
                tile.setExists(true);
            }else{//if a number was removed, add it back at the correct index
                affectedColorTiles.add(discardIndex, tile);
                affectedColorPanes.add(discardIndex, pane);
                tile.setColor(affectedColor);// set color to correct color for redrawing
                updateColorNums(affectedColorTiles, affectedColorPanes);//redraw the numbering of the colored tile and pane sets
            }
        }
        redrawPane();//redraw the pane
        return true;
    }

    @Override
    public boolean redoAction() {
        if (tile.getHint() == true) {
            tile.setHint(false);
        }
        if(bw == Color.WHITE){ //if a whiteout action was done, make the tile valid
            tile.setExists(true);
        }
        if(bw == Color.BLACK) {
            if(!affected) {//if a blackout action was done, make the tile invalid
                tile.setExists(false);
            }else{//if a number was removed, remove it at the correct index
                if(tile == null){
                    System.out.println("!!! tile not found in affectedColorTiles");
                }
                affectedColorTiles.remove(discardIndex);
                affectedColorPanes.remove(discardIndex);

                tile.setColor(Color.WHITE); // set color to correct color for redrawing
                pane.getNumLabel().setText(""); //remove text
                updateColorNums(affectedColorTiles, affectedColorPanes); //redraw the numbering of the colored tile and pane sets
            }
        }
        redrawPane();
        return true;
    }

    /** returns valid if a whiteout(validate) on a black tile or a blackout(invalidate) on white tile is attempted
     *  actions that dont change the tile are not considered valid
     */
    @Override
    public boolean isValid() {

        boolean validation= true;
        if(bw == Color.WHITE && tile.getExists() ==true){//validating an already valid tile
            validation = false;
        }
        if(bw == Color.BLACK && tile.getExists() ==false){//invalidating an already invalid tile
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
                pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }else {
                pane.setStyle("-fx-background-color: white");
                pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        } else {
            pane.setStyle("-fx-background-color: black");
            pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

}
