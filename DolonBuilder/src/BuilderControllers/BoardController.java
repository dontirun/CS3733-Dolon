package BuilderControllers;

import BuilderModel.ReleaseTile;
import BuilderModel.Tile;
import UndoActionManager.ColorAction;
import UndoActionManager.HintAction;
import UndoActionManager.ReleaseTileAction;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

    /**
     * Creates a new BoardController
     *
     * @param lbc the controller for the level builder
     */
    public BoardController(LevelBuilderController lbc) {
        this.lbc = lbc;
        redNumTiles = new ArrayList<ReleaseTile>();
        redNumPanes = new ArrayList<GridSquare>();
        greenNumTiles = new ArrayList<ReleaseTile>();
        greenNumPanes = new ArrayList<GridSquare>();
        yellowNumTiles = new ArrayList<ReleaseTile>();
        yellowNumPanes = new ArrayList<GridSquare>();
    }

    /**
     * Handles when a specific tile in the board is clicked
     * Needs to be changed so that the tiles handle it instead of the board
     *
     * @param clickedTile the clicked tile
     * @param clickedPane the clicked pane
     */
    public void handleBoardClicked(Tile clickedTile, Pane clickedPane) {
        try {
            // if its not an int don't change the board
            Integer.parseInt(lbc.levelNumber.getText());
        } catch (Exception e) {
            return;
        }


        if (lbc.color == Color.BLACK || lbc.color == Color.WHITE) {

                ReleaseTileAction rta = new ReleaseTileAction((ReleaseTile) clickedTile, (GridSquare) clickedPane, lbc.color);
                if (rta.doAction()) {
                    System.out.println("tile action performed");
                    lbc.undoHistory.push(rta);
                    lbc.redoHistory.clear();
                }

        } else if (lbc.color == Color.ORANGE) {
            HintAction ha = new HintAction(clickedTile, clickedPane);
            if (ha.doAction()) {
                System.out.println("hint action performed");
                lbc.undoHistory.push(ha);
                lbc.redoHistory.clear();
            }
        } else {
            ColorAction ca = new ColorAction((ReleaseTile) clickedTile, (GridSquare) clickedPane, lbc.color);
            if (ca.doAction()) {
                System.out.println("color action performed");
                lbc.undoHistory.push(ca);
                lbc.redoHistory.clear();
            }
        }

        // do nothing

    }

//    /**
//     * Handles when the board is clicked and gets the tile located at the click
//     *
//     * @param event mouseevent that occurs
//     */
//    public void handleBoardClicked(MouseEvent event) {
//        try {
//            // if its not an int don't change the board
//            Integer.parseInt(lbc.levelNumber.getText());
//        } catch (Exception e) {
//            return;
//        }
//        //get x and y mouse coordinates
//        double x = event.getX();
//        double y = event.getY();
//        //find column and row of tile clicked
//        int col = (int) (x / 45.8333333);
//        int row = (int) (y / 45.8333333);
//
//        ReleaseTile clickedTile = (ReleaseTile) lbc.level.getTile(row, col);
//        GridSquare clickedPane = (GridSquare) lbc.tilePanes.get(row).get(col);
//
//        if (lbc.color == Color.BLACK || lbc.color == Color.WHITE) {
//            if (lbc.level.getMode() == "release") {
//                ReleaseTileAction rta = new ReleaseTileAction(clickedTile, clickedPane, lbc.color);
//                if (rta.doAction()) {
//                    System.out.println("tile action performed");
//                    lbc.undoHistory.push(rta);
//                    lbc.redoHistory.clear();
//                }
//            } else {
//                TileAction ta = new TileAction(clickedTile, clickedPane, lbc.color);
//                if (ta.doAction()) {
//                    System.out.println("tile action performed");
//                    lbc.undoHistory.push(ta);
//                    lbc.redoHistory.clear();
//                }
//            }
//        } else {
//
//            ColorAction ca = new ColorAction(clickedTile, clickedPane, lbc.color);
//            if (ca.doAction()) {
//                System.out.println("color action performed");
//                lbc.undoHistory.push(ca);
//                lbc.redoHistory.clear();
//            }
//        }
//
//        // do nothing
//
//    }

    /**
     * Updates the numbers on the tiles when setting release tile numbers
     *
     * @param affectedColorTiles all the tiles affected by the update
     * @param affectedColorPanes all the panes affected by the update
     */
    public static void updateColorNums(ArrayList<ReleaseTile> affectedColorTiles, ArrayList<GridSquare> affectedColorPanes) {
        for (int i = 0; i < affectedColorPanes.size(); i++) {
            affectedColorPanes.get(i).getNumLabel().setText(Integer.toString(i + 1));
            affectedColorTiles.get(i).setNum(i + 1);
            Color color = affectedColorTiles.get(i).getColor();
            affectedColorPanes.get(i).getNumLabel().setTextFill(color);
            if (color == Color.YELLOW) {
                affectedColorPanes.get(i).getNumLabel().setTextFill(Color.web("#d5ae27"));
            }
            affectedColorPanes.get(i).getNumLabel().autosize();
            affectedColorPanes.get(i).getNumLabel().setStyle("-fx-font: 40 arial;");
            affectedColorPanes.get(i).setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            // affectedColorPanes.get(i).getNumLabel().setStyle("-fx-stroke-width: 2px;");
            // affectedColorPanes.get(i).getNumLabel().setAlignment(Pos.CENTER_RIGHT);
        }
    }


    /**
     * Finds all the tiles of a certain color?
     *
     * @param color color tile nums to be found
     * @return arraylist of specific color tiles
     */
    public static ArrayList<ReleaseTile> getColorNumTiles(Color color) {
        ArrayList<ReleaseTile> result = null;
        if (color == Color.RED) {
            result = redNumTiles;
        }
        if (color == Color.GREEN) {
            result = greenNumTiles;
        }
        if (color == Color.YELLOW) {
            result = yellowNumTiles;
        }
        if (result == null) {
            System.out.println("couldnt find corresponding arraylist");
        }
        return result;
    }

    /**
     * Finds all the panes of a certain color?
     *
     * @param color color pane nums to be found
     * @return arraylist of specific color panes
     */
    public static ArrayList<GridSquare> getColorNumPanes(Color color) {
        ArrayList<GridSquare> result = null;
        if (color == Color.RED) {
            result = redNumPanes;
        }
        if (color == Color.GREEN) {
            result = greenNumPanes;
        }
        if (color == Color.YELLOW) {
            result = yellowNumPanes;
        }
        if (result == null) {
            System.out.println("couldnt find corresponding arraylist");
        }
        return result;
    }

    /**
     * clears the numbers from the panes and the tiles
     */
    /*
    public static void clearColorNumPanes() {
        redNumTiles.clear();
        redNumPanes.clear();
        greenNumTiles.clear();
        greenNumPanes.clear();
        yellowNumTiles.clear();
        yellowNumPanes.clear();
    }
    */


}


