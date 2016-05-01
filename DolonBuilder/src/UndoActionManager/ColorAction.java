package UndoActionManager;

import BuilderControllers.BoardController;
import BuilderControllers.GridSquare;
import BuilderModel.LevelModel;
import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Queue;

import static BuilderControllers.BoardController.*;



/**
 * Created by Walter on 4/16/2016.
 */
public class ColorAction implements IAction {

    ReleaseTile tile;
    GridSquare pane;
    String startText;
    String endText;
    Color startColor;
    Color endColor; //determines which arraylist to change
    ReleaseTile discardTile;
    GridSquare discardPane;
    boolean discarded;
    ArrayList<ReleaseTile> affectedColorTiles;
    ArrayList<GridSquare> affectedColorPanes;

    /**
     * Constructor for the action
     *
     * @param tile
     * @param pane
     * @param color
     */
    public ColorAction(ReleaseTile tile, GridSquare pane, Color color) {
        this.tile = tile;
        this.pane = pane;
        startColor = tile.getColor();
        this.endColor = color;
        affectedColorTiles = getColorNumTiles(color);
        affectedColorPanes = getColorNumPanes(color);
    }

    /**
     * Complete a color action and change the color of the tile
     * @return
     */
    @Override
    public boolean doAction() {
        if (isValid()) {
            //record starting color of tile clicked
            startColor=tile.getColor();
            //record start number of tile clicked
            this.startText = pane.getNumLabel().getText();
            tile.setColor(endColor);
            affectedColorTiles.add(tile);
            affectedColorPanes.add(pane);
            if(affectedColorTiles.size()>6){
                discarded = true;
                discardTile = affectedColorTiles.remove(0);
                discardPane  = affectedColorPanes.remove(0);

                discardTile.setColor(Color.WHITE);
                discardPane.getNumLabel().setText("");

            }
            updateColorNums(affectedColorTiles, affectedColorPanes);

            return true;
        }
        return false;
    }

    /**
     * Undo changing the color of a tile
     *
     * @return
     */
    @Override
    public boolean undoAction() {
        System.out.println("undoing color action...");
        if(discarded) {
            affectedColorTiles.add(0, discardTile);
            affectedColorPanes.add(0, discardPane);
            discardTile.setColor(endColor);

            affectedColorTiles.remove(affectedColorTiles.size()-1);
            affectedColorPanes.remove(affectedColorPanes.size()-1);

            tile.setColor(startColor);
            pane.getNumLabel().setText(startText);
            pane.getNumLabel().setTextFill(startColor);
        }
        else{
            affectedColorTiles.remove(affectedColorTiles.size()-1);
            affectedColorPanes.remove(affectedColorPanes.size()-1);

            tile.setColor(startColor);
            pane.getNumLabel().setText(startText);
            pane.getNumLabel().setTextFill(startColor);
        }

        updateColorNums(affectedColorTiles, affectedColorPanes);

        return true;
    }

    /**
     * Redo changing the color of a tile
     *
     * @return
     */
    @Override
    public boolean redoAction() {
        System.out.println("redoing color action...");

        tile.setColor(endColor);
        affectedColorTiles.add(tile);
        affectedColorPanes.add(pane);
        System.out.println("array size : "+ affectedColorTiles.size());
        if(affectedColorTiles.size()>6){

            discardTile = affectedColorTiles.remove(0);
            discardPane  = affectedColorPanes.remove(0);

            discardTile.setColor(Color.WHITE);
            discardPane.getNumLabel().setText("");

        }

        updateColorNums(affectedColorTiles, affectedColorPanes);

        return true;
    }

    /**
     * Checks if it is valid for a tile to change colors
     * @return
     */
    @Override
    public boolean isValid() {
        //only allows giving a color num to valid tiles
        return tile.getExists() && tile.getColor()==Color.WHITE;
    }





}
