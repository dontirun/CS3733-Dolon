package BuilderControllers;

import BuilderModel.ReleaseTile;
import UndoActionManager.ColorAction;
import UndoActionManager.ReleaseTileAction;
import UndoActionManager.TileAction;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        if (lbc.color == Color.BLACK || lbc.color == Color.WHITE) {
            if (lbc.level.getMode() == "release") {
                ReleaseTileAction rta = new ReleaseTileAction(clickedTile, clickedPane, lbc.color);
                if (rta.doAction()) {
                    System.out.println("tile action performed");
                    lbc.undoHistory.push(rta);
                    lbc.redoHistory.clear();
                }
            } else {
                TileAction ta = new TileAction(clickedTile, clickedPane, lbc.color);
                if (ta.doAction()) {
                    System.out.println("tile action performed");
                    lbc.undoHistory.push(ta);
                    lbc.redoHistory.clear();
                }
            }
        } else {

            ColorAction ca = new ColorAction(clickedTile, clickedPane, lbc.color);
            if (ca.doAction()) {
                System.out.println("color action performed");
                lbc.undoHistory.push(ca);
                lbc.redoHistory.clear();
            }
        }

        // do nothing

    }

    public static void updateColorNums(ArrayList<ReleaseTile> affectedColorTiles, ArrayList<GridSquare>affectedColorPanes){
        for(int i = 0; i< affectedColorPanes.size(); i++){
            affectedColorPanes.get(i).getNumLabel().setText(Integer.toString(i+1));
            Color color = affectedColorTiles.get(i).getColor();
            affectedColorPanes.get(i).getNumLabel().setTextFill(color);
            if(color == Color.YELLOW){
                affectedColorPanes.get(i).getNumLabel().setTextFill(Color.GOLD);
            }
            affectedColorPanes.get(i).getNumLabel().autosize();
            affectedColorPanes.get(i).getNumLabel().setStyle("-fx-font: 40 arial;");
            //affectedColorPanes.get(i).getNumLabel().setStyle("-fx-stroke: black;");
            // affectedColorPanes.get(i).getNumLabel().setStyle("-fx-stroke-width: 2px;");
            // affectedColorPanes.get(i).getNumLabel().setAlignment(Pos.CENTER_RIGHT);
        }
    }
    public static ArrayList<ReleaseTile> getColorNumTiles(Color color){
        ArrayList<ReleaseTile> result = null;
        if(color == Color.RED){
            result = redNumTiles;
        }
        if(color == Color.GREEN){
            result = greenNumTiles;
        }
        if(color == Color.YELLOW){
            result = yellowNumTiles;
        }
        if(result== null){
            System.out.println("couldnt find corresponding arraylist");
        }
        return result;
    }
    public static ArrayList<GridSquare> getColorNumPanes(Color color){
        ArrayList<GridSquare> result = null;
        if(color == Color.RED){
            result = redNumPanes;
        }
        if(color == Color.GREEN){
            result = greenNumPanes;
        }
        if(color == Color.YELLOW){
            result = yellowNumPanes;
        }
        if(result== null){
            System.out.println("couldnt find corresponding arraylist");
        }
        return result;
    }
    public static void clearColorNumPanes(){
        redNumTiles.clear();
        redNumPanes.clear();
        greenNumTiles.clear();
        greenNumPanes.clear();
        yellowNumTiles.clear();
        yellowNumPanes.clear();
    }


}


