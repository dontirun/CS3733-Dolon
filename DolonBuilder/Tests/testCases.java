import BuilderControllers.LevelBuilderController;
import BuilderModel.*;
import PieceFactory.PieceFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;


import java.awt.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import static com.sun.jmx.snmp.ThreadContext.contains;
import static java.awt.SystemColor.text;
import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChildren;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;


/**
 * Created by slafo on 4/30/2016.
 */
public class testCases extends ApplicationTest {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new BuilderMain().start(primaryStage);
        stage = primaryStage;
    }

    @Test
    public void startBuilderTest() throws Exception {
        // load into the level builder
        clickOn("#startButton");
        assertEquals("Kabasuji Builder", stage.getTitle());

        // check that everything is empty
        verifyThat(".levelTextField", NodeMatchers.isNull());
        verifyThat(".bullpenView", NodeMatchers.isNull());
        verifyThat("#levelNumber", hasText("#"));
        verifyThat("#greenButton", isInvisible());
        verifyThat("#yellowButton", isInvisible());
        verifyThat("#redButton", isInvisible());
        verifyThat("#rowsTextField", NodeMatchers.hasText("12"));
        verifyThat("#colsTextField", NodeMatchers.hasText("12"));

        GridPane board = lookup("#boardView").query();

        assertTrue(board.getChildren().size() == 144);
    }

    @Test
    public void loadLevel1() throws Exception {
        // load into the level builder
        clickOn("#startButton");

        clickOn("#levelTextField");
        write("1");
        clickOn("#loadButton");

        verifyThat("#levelNumber", hasText("1"));
    }

    //Piece Factory Class testCases
    //----------------------------------------------------------------------------------------------------------
    @Test
    public void testpieceFactoryTest() throws Exception {
        PieceFactory pf = new PieceFactory();
        for (int i = 1; i<=35; i++){
            Piece tempPiece = pf.getPiece(i);
            // makign sure all pieces are created correctly
            assertEquals(tempPiece.getPieceID(),i);
        }

    }

    //Builder Model Class testCases
    //----------------------------------------------------------------------------------------------------------
    @Test
    //making sure that all the Piece methods work properly
    public void testpieceTest() throws Exception {
        PieceFactory pf = new PieceFactory();
        Piece tempPiece = pf.getPiece(2);
        assertEquals(tempPiece.getPieceID(),2);
        // before flipping
        assertEquals(tempPiece.squares.get(5).getRelCol(), 1);
        assertEquals(tempPiece.squares.get(5).getRelRow(), 2);
        //rotating right
        tempPiece.rotatePieceRight();
        assertEquals(tempPiece.squares.get(5).getRelCol(), 2);
        assertEquals(tempPiece.squares.get(5).getRelRow(), -1);
        //rotating left
        tempPiece.rotatePieceLeft();
        assertEquals(tempPiece.squares.get(5).getRelCol(), 1);
        assertEquals(tempPiece.squares.get(5).getRelRow(), 2);
        //vertical flip
        tempPiece.flipPieceVert();
        assertEquals(tempPiece.squares.get(5).getRelCol(), 1);
        assertEquals(tempPiece.squares.get(5).getRelRow(), -2);
        //horizontal flip
        tempPiece.flipPieceHoriz();
        assertEquals(tempPiece.squares.get(5).getRelCol(), -1);
        assertEquals(tempPiece.squares.get(5).getRelRow(), -2);
        //changing color
        assertEquals(tempPiece.getColor(), javafx.scene.paint.Color.color(0,0,1));
        tempPiece.setColor(javafx.scene.paint.Color.LEMONCHIFFON);
        assertEquals(tempPiece.getColor(), javafx.scene.paint.Color.LEMONCHIFFON );
        //changing uniqueid
        tempPiece.setUniqueID(2);
        assertEquals(tempPiece.getUniqueID(),2);
        //changing pieceBoardNum
        tempPiece.setPieceBoardNum(2);
        assertEquals(tempPiece.getPieceBoardNum(),2);



    }
    @Test
    //making sure that all the Tile methods work properly
    public void testtileTest() throws Exception {
        ReleaseTile tile = new ReleaseTile();
        // testing tile number stuff
        tile.setNum(1);
        assertEquals(tile.getNum(),1);
        //testing  color stuff
        tile.setColor(javafx.scene.paint.Color.RED);
        assertEquals(tile.getColor(), javafx.scene.paint.Color.RED);

        //testing square covering stuff

        Square square= new Square(1,1);
        assertEquals(tile.getSquare(),null);
        assertEquals(tile.getCovered(),-1);
        tile.setSquare(square,1);
        assertEquals(tile.getSquare(),square);
        assertEquals(tile.getCovered(),1);
        tile.removeSquare();
        assertEquals(tile.getSquare(),null);


        Tile newTile = new Tile(false);
        assertEquals(newTile.getExists(), false);
        newTile.setExists(true);
        assertEquals(newTile.getExists(), true);
        newTile.flipExists();
        assertEquals(newTile.getExists(), false);



    }

    @Test
    //making sure bullpen methods work properly
    public void testBullpenTest() throws Exception {
        PieceFactory pf = new PieceFactory();
        Piece tempPiece = pf.getPiece(2);
        Piece tempPiece2 = pf.getPiece(2);
        tempPiece2.setUniqueID(10);
        Bullpen bp = new Bullpen();
        // adding piece to the bullpen
        bp.addPiece(tempPiece);
        // checking if that piece is in the bullpen
        assertEquals(bp.getPiece(tempPiece),tempPiece);
        // getting the arraylist of pieces and seeing if it returns properly
        assertTrue(bp.getPieces().contains(tempPiece));
        // should return true if piece was properly removed
        assertTrue(bp.removePiece(tempPiece.uniqueID));
        // should return false now if piece was properly removed
        assertFalse(bp.removePiece(tempPiece.uniqueID));

    }

    /*// test the bullpen piece not found exception
    @Test(expected=PieceNotFoundException.class)
    public void testPieceNotFoundException() {
        PieceFactory pf = new PieceFactory();
        Piece tempPiece = pf.getPiece(2);
        tempPiece.setUniqueID(10);
        Bullpen bp = new Bullpen();

        // test case not working under testfx
        *//*try {
            bp.getPiece(tempPiece);
        } catch (PieceNotFoundException e) {
            e.printStackTrace();
        }*//*

    }*/

    @Test
    //making sure level model and some board methods work properly
    public void testLevelTest() throws Exception {
        LevelModel lm = new LevelModel("release");
        LevelModel lvm = new LevelModel();
        // assertEquals(lm.getMode(),"release");
        assertEquals(lm.getTile(1,1).getExists(),true);
        assertEquals(lm.getBoardTiles().size(),12);
        assertEquals(lm.getField().getBoardTile(1,1).getExists(),true);



    }

    @Test
    //making sure rest of board methods work properly
    public void testBoardTest() throws Exception {
        Board board = new Board();
        PieceFactory pf = new PieceFactory();
        Piece tempPiece1 = pf.getPiece(2);
        Piece tempPiece2 = pf.getPiece(1);

        // shouldn't be able to add this piece to this spot
        assertFalse(board.addPiece(tempPiece1,1,1));

        // should be able to add this piece to this spot
        assertTrue(board.addPiece(tempPiece1,6,6));

        // shouldn't be able to add this piece to this spot since its already covered
        assertFalse(board.addPiece(tempPiece2,5,6));

        // shouldn't be remove this piece since its not there
        assertFalse(board.removePiece(tempPiece2.getPieceBoardNum()));

        // should be able to remove this piece since its there
        assertTrue(board.removePiece(tempPiece1.getPieceBoardNum()));

        board.setNumColumns(11);
        board.setNumRows(11);

        // checking if rows and columns updated properly
        assertTrue(board.getTiles().size()==11);
        assertTrue(board.getTiles().get(1).size()==11);
    }

}
