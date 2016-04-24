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

import static BuilderControllers.BoardController.redNumPanes;
import static BuilderControllers.BoardController.redNumTiles;


/**
 * Created by Walter on 4/16/2016.
 */
public class ColorAction implements IAction {

    ReleaseTile tile;
    GridSquare pane;
    Color startColor;
    Color endColor;
    ReleaseTile discardTile;
    GridSquare discardPane;

    public ColorAction(ReleaseTile tile, GridSquare pane, Color color) {
        this.tile = tile;
        this.pane = pane;
        this.endColor = color;
    }

    @Override
    public boolean doAction() {
        if (isValid()) {
            //record starting style
            startColor=tile.getColor();


            tile.setColor(endColor);
//            pane.getNumLabel().setText("1");
//            pane.getNumLabel().setTextFill(endColor);

            System.out.println(endColor);


            redNumTiles.add(tile);
            redNumPanes.add(pane);
            System.out.println("array size : "+ redNumTiles.size());
            if(redNumTiles.size()>6){

                discardTile = redNumTiles.remove(0);
                discardPane  = redNumPanes.remove(0);

                discardTile.setColor(Color.WHITE);
                discardPane.getNumLabel().setText("");

                for(int i = 0; i< redNumPanes.size(); i++){
                    redNumPanes.get(i).getNumLabel().setText(Integer.toString(i));
                    redNumPanes.get(i).getNumLabel().setTextFill(Color.RED);
                }

            }

           // pane.setNum(endColor);
            return true;
        }
        return false;
    }

    @Override
    public boolean undoAction() {
        tile.setColor(startColor);
        pane.getNumLabel().setTextFill(startColor);

        return true;
    }

    @Override
    public boolean redoAction() {
        tile.setColor(endColor);
        pane.getNumLabel().setTextFill(endColor);

        return true;
    }

    @Override
    public boolean isValid() {
        //only allows giving a color num to valid tiles
        return tile.getExists();
    }


}
