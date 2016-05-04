import KabasujiModel.*;
import PieceFactory.PieceFactory;
import javafx.scene.input.KeyCode;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
//import org.loadui.testfx.GuiTest;
//import org.loadui.testfx.controls.*;
//import org.loadui.testfx.utils.*;
//import org.loadui.testfx.MouseMotion;
//import org.loadui.testfx.categories.*;
//import static org.loadui.testfx.Assertions.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChildren;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;
import javafx.application.Application;
/**
 * Created by Arun on 4/29/2016.
 */
public class GameTests extends ApplicationTest {


    Stage stage;

    //Gui/Controller testCases
    //----------------------------------------------------------------------------------------------------------
    @Override
    public void start(Stage primaryStage) throws Exception {
        new KabasujiMain().start(primaryStage);
        stage = primaryStage;
    }

    // GUI tests

    //----------------------------------------------------------------------------------------------------------
    protected Parent getRootNode() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try (InputStream inputStream = getClass()
                .getResourceAsStream("/views/levelbuilder.fxml")) {
            return fxmlLoader.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }



    @Test
    public void testaboutButtonTest() throws Exception {
        clickOn("#aboutKButton");

        // Wait for success with a 10s timeout
        // waitUntil("#l", is(visible()), 10);
        // verifyThat(text, containsString("OK"));

        push(KeyCode.ESCAPE);

    }


    @Test
    public void teststartButtonTest() throws Exception {
        clickOn("#decrementLevel");
        clickOn("#incrementLevel");
        clickOn("#startKButton");

        // Wait for success with a 10s timeout
        // waitUntil("#l", is(visible()), 10);

        verifyThat("#levelNumber", hasText("1"));
        // verifyThat(text, containsString("OK"));
    }

    @Test
    public void testLevelsTest() throws Exception {
        clickOn("#decrementLevel");
        clickOn("#incrementLevel");
        clickOn("#startKButton");


        // Testing a puzzle level

        verifyThat("#levelNumber", hasText("1"));
        // verifyThat(text, containsString("OK"));
        GridPane bullpen = lookup("#bullpenView").query();
        GridPane board = lookup("#boardView").query();
        assertEquals(bullpen.getChildren().size(),5);
        clickOn(bullpen.getChildren().get(0));
        // test all the rotation stuff
        clickOn("#flipHoriz");
        clickOn("#flipHoriz");
        clickOn("#flipVert");
        clickOn("#flipVert");
        clickOn("#rotateLeft");
        clickOn("#rotateRight");

        // should be able to make this drop
        drag(bullpen.getChildren().get(1)).dropTo(board.getChildren().get(64));
        assertEquals(bullpen.getChildren().size(),4);
        // Piece in the way, can't make this drop
        drag(bullpen.getChildren().get(1)).dropTo(board.getChildren().get(64));
        assertEquals(bullpen.getChildren().size(),4);
        // should be able to make these ones
        drag(bullpen.getChildren().get(1)).dropTo(board.getChildren().get(65));
        drag(bullpen.getChildren().get(1)).dropTo(board.getChildren().get(66));
        drag(bullpen.getChildren().get(1)).dropTo(board.getChildren().get(67));
        //remove a piece from the board
        rightClickOn(board.getChildren().get(67));
        //move a different piece to that spot
        drag(board.getChildren().get(66)).dropTo(board.getChildren().get(67));
        // replace that piece from the bullpen
        drag(bullpen.getChildren().get(1)).dropTo(board.getChildren().get(66));

        assertEquals(bullpen.getChildren().size(),1);
       // wait(5000);


       //testing a lightning level------------------------------------------------------------------------------
        clickOn("#forwardLevel");
        clickOn("#backLevel");
        clickOn("#forwardLevel");

        int size = bullpen.getChildren().size();
        //make all the moves
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(63));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(64));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(63));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(64));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(65));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(66));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(67));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(68));

        // stars are updated, bullpen should have same size
        assertEquals(bullpen.getChildren().size(), size);

        //hit reset and do everything again
        clickOn("#resetButton");

        Label limit= lookup("#limitLabel").query();
        // store the timer value in the beginning
        int timer = Integer.parseInt(limit.getText());
        //make all the moves
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(63));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(64));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(63));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(64));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(65));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(66));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(67));
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(68));

        // timer should be less than in beginning
        assertTrue(Integer.parseInt(limit.getText()) < timer);



       //testing a release level------------------------------------------------------------------------
        clickOn("#forwardLevel");

        // rotate and drag the first piece
        clickOn(bullpen.getChildren().get(0));
        clickOn("#rotateRight");
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(66));

        //rotate and drag the next piece
        clickOn(bullpen.getChildren().get(0));
        clickOn("#rotateRight");
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(54));


        //rotate and drag the next piece
        clickOn(bullpen.getChildren().get(0));
        clickOn("#rotateRight");
        drag(bullpen.getChildren().get(0)).dropTo(board.getChildren().get(78));

        assertEquals(bullpen.getChildren().size(), 0);

        // go home

        clickOn("#homeButton");
        Label name= lookup("#name").query();
        assertEquals(name.getText(),"Kabasuji");



    }






    //Piece Factory Class tests
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

    //Builder Model Class tests
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
        assertEquals(tempPiece.getColor(), Color.color(0,0,1));
        tempPiece.setColor(Color.LEMONCHIFFON);
        assertEquals(tempPiece.getColor(),Color.LEMONCHIFFON );
        //changing uniqueid
        tempPiece.setUniqueID(2);
        assertEquals(tempPiece.getUniqueID(),2);



    }

    @Test
    //making sure that all the Piece methods work properly
    public void testpiecegroupTest() throws Exception {
        PieceFactory pf = new PieceFactory();
        Piece tempPiece = pf.getPiece(2);
        PieceGroup pg = new PieceGroup(tempPiece);
        pg.getGroup();
    }
        @Test
    //making sure that all the Tile methods work properly
    public void testtileTest() throws Exception {
        ReleaseTile tile = new ReleaseTile();
        // testing tile number stuff
        tile.setNum(1);
        assertEquals(tile.getNum(),1);
        //testing  color stuff
        tile.setColor(Color.RED);
        assertEquals(tile.getColor(),Color.RED);

        //testing square covering stuff

        Square square= new Square(1,1);
        assertEquals(tile.getSquare(),null);
        assertEquals(tile.getCovered(),-1);
        tile.setSquare(square,1);
        assertEquals(tile.getSquare(),square);
        assertEquals(tile.getCovered(),1);
        tile.removeSquare();
        assertEquals(tile.getSquare(),null);

        //testing exetential stuff
        Tile newTile = new Tile(false);
        assertEquals(newTile.getExists(), false);
        newTile.setExists(true);
        assertEquals(newTile.getExists(), true);
        newTile.flipExists();
        assertEquals(newTile.getExists(), false);
        newTile.setCovered(1);

        //testing hint stuff
        assertFalse(newTile.getHint());
        newTile.flipHint();
        assertTrue(newTile.getHint());
        newTile.setHint(false);
        assertFalse(newTile.getHint());


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
        assertTrue(bp.removePiece(tempPiece.getUniqueID()));
        // should return false now if piece was properly removed
        assertFalse(bp.removePiece(tempPiece.getUniqueID()));

    }


    @Test
    //making sure level model and some board methods work properly
    public void testLevelTest() throws Exception {
        LevelModel rlm = new ReleaseLevelModel(3);
        PuzzleLevelModel plm = new PuzzleLevelModel(1);
        LightningLevelModel llm = new LightningLevelModel(2);
        assertEquals(rlm.getTile(1,1).getExists(),true);
        assertEquals(rlm.getBoardTiles().size(),12);
        assertEquals(rlm.getBoard().getBoardTile(1,1).getExists(),true);
        // shouldn't be able to load this baord
        //assertEquals(plm.loadBoard("lvlasdasd1.bdsm"),false);
        // these will need to be changed
        plm.updateStars();
        rlm.updateStars();
        llm.updateStars();
        assertFalse(plm.hasPassed());
        // checking total move stuff
        plm.setTotalMoves(5);
        assertEquals(plm.getTotalMoves(),5);
        //checking allowed time stuff
        llm.setAllowedTime(5);
        assertEquals(llm.getAllowedTime(),5);



    }

    @Test
    //making sure rest of board methods work properly
    public void testBoardTest() throws Exception {
        Board board = new Board();
        Board board1 = new Board(11,11);
        PieceFactory pf = new PieceFactory();
        Piece tempPiece1 = pf.getPiece(2);
        tempPiece1.setUniqueID(2);
        Piece tempPiece2 = pf.getPiece(1);

        // shouldn't be able to add this piece to this spot
        assertFalse(board.addPiece(tempPiece1,1,1));

        // should be able to add this piece to this spot
        assertTrue(board.addPiece(tempPiece1,6,6));

        // shouldn't be able to add this piece to this spot since its already covered
        assertFalse(board.addPiece(tempPiece2,5,6));

        // should be able to add this piece to this spot since its already covered and lightning
        assertTrue(board.addPieceLightning(tempPiece2,5,6));
        // should return false since out of bounds
        assertFalse(board.isOutOfBounds(tempPiece1, 13,13));
        // should return true since in bounds
        //assertTrue(board.isInBounds(5,5));
        Piece tp1= board.getPieceFromID(2);
        // should be equal
        assertEquals(tempPiece1,tp1);
        // should be able to remove this piece since its there
        assertTrue(board.removePiece(tempPiece1.getUniqueID()));
        // should be the number of tiles on the board
        assertEquals(board.tilesOnBoard(), 144);
        board.printBoardAsDebug();
        board.clearBoard();
        assertEquals(board.tilesOnBoard(), 0);
        // shouldn't be remove this piece since its not there
        assertFalse(board.removePiece(tempPiece2.getUniqueID()));


        // checking if rows and columns updated properly
        //assertTrue(board.getTiles().size()==11);
       // assertTrue(board.getTiles().get(1).size()==11);
    }

    @Test
    //making sure game menu methods work properly
    public void testGameMenuTest() throws Exception {
       GameMenu gm = new GameMenu();
        //checking levelnumber and unlocked functionalities
        assertEquals(gm.getLevelNumber(),1);
        gm.incrementLevelNumber();
        assertEquals(gm.getLevelNumber(),2);
        gm.decrementLevelNumber();
        assertEquals(gm.getLevelNumber(),1);
        gm.setLevelNumber(3);
        assertEquals(gm.getLevelNumber(),3);
        gm.setUnlocked(3);
        assertEquals(gm.getUnlocked(),3);
    }


    @Test
    //making sure piece group methods work properly
    // I'm gonna leave this part to Arthur
    public void testPieceGroupTest() throws Exception {
        PieceFactory pf = new PieceFactory();
        Piece piece1  = pf.getPiece(3);

    }



}



