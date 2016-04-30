//package Tests;
//
//import BuilderModel.*;
//import PieceFactory.PieceFactory;
//import junit.framework.TestCase;
//import org.junit.Assert;
//import org.junit.Test;
//import org.loadui.testfx.GuiTest;
//import org.loadui.testfx.controls.*;
//import org.loadui.testfx.utils.*;
//import org.loadui.testfx.MouseMotion;
//import org.loadui.testfx.categories.*;
//import static org.loadui.testfx.Assertions.*;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.geometry.HPos;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.geometry.VPos;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Text;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.*;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.ResourceBundle;
//import java.util.Stack;
//import java.util.regex.Pattern;
//
///**
// * Created by Arun on 4/29/2016.
// */
//
//
//
//public class BuilderTests extends TestCase {
//
//
//    //Piece Factory Class tests
//    //----------------------------------------------------------------------------------------------------------
//    @Test
//    public void testpieceFactoryTest() throws Exception {
//        PieceFactory pf = new PieceFactory();
//        for (int i = 1; i<=35; i++){
//            Piece tempPiece = pf.getPiece(i);
//            // makign sure all pieces are created correctly
//            assertEquals(tempPiece.getPieceID(),i);
//        }
//
//    }
//
//    //Builder Model Class tests
//    //----------------------------------------------------------------------------------------------------------
//    @Test
//    //making sure that all the Piece methods work properly
//    public void testpieceTest() throws Exception {
//        PieceFactory pf = new PieceFactory();
//        Piece tempPiece = pf.getPiece(2);
//        assertEquals(tempPiece.getPieceID(),2);
//        // before flipping
//        assertEquals(tempPiece.squares.get(5).getRelCol(), 1);
//        assertEquals(tempPiece.squares.get(5).getRelRow(), 2);
//        //rotating right
//        tempPiece.rotatePieceRight();
//        assertEquals(tempPiece.squares.get(5).getRelCol(), 2);
//        assertEquals(tempPiece.squares.get(5).getRelRow(), -1);
//        //rotating left
//        tempPiece.rotatePieceLeft();
//        assertEquals(tempPiece.squares.get(5).getRelCol(), 1);
//        assertEquals(tempPiece.squares.get(5).getRelRow(), 2);
//        //vertical flip
//        tempPiece.flipPieceVert();
//        assertEquals(tempPiece.squares.get(5).getRelCol(), 1);
//        assertEquals(tempPiece.squares.get(5).getRelRow(), -2);
//        //horizontal flip
//        tempPiece.flipPieceHoriz();
//        assertEquals(tempPiece.squares.get(5).getRelCol(), -1);
//        assertEquals(tempPiece.squares.get(5).getRelRow(), -2);
//        //changing color
//        assertEquals(tempPiece.getColor(), Color.color(0,0,1));
//        tempPiece.setColor(Color.LEMONCHIFFON);
//        assertEquals(tempPiece.getColor(),Color.LEMONCHIFFON );
//        //changing uniqueid
//        tempPiece.setUniqueID(2);
//        assertEquals(tempPiece.getUniqueID(),2);
//        //changing pieceBoardNum
//        tempPiece.setPieceBoardNum(2);
//        assertEquals(tempPiece.getPieceBoardNum(),2);
//
//
//
//    }
//    @Test
//    //making sure that all the Tile methods work properly
//    public void testtileTest() throws Exception {
//        ReleaseTile tile = new ReleaseTile();
//        // testing tile number stuff
//        tile.setNum(1);
//        assertEquals(tile.getNum(),1);
//        //testing  color stuff
//        tile.setColor(Color.RED);
//        assertEquals(tile.getColor(),Color.RED);
//
//        //testing square covering stuff
//
//        Square square= new Square(1,1);
//        assertEquals(tile.getSquare(),null);
//        assertEquals(tile.getCovered(),-1);
//        tile.setSquare(square,1);
//        assertEquals(tile.getSquare(),square);
//        assertEquals(tile.getCovered(),1);
//        tile.removeSquare();
//        assertEquals(tile.getSquare(),null);
//
//
//        Tile newTile = new Tile(false);
//        assertEquals(newTile.getExists(), false);
//        newTile.setExists(true);
//        assertEquals(newTile.getExists(), true);
//        newTile.flipExists();
//        assertEquals(newTile.getExists(), false);
//
//
//
//    }
//
//    @Test
//    //making sure bullpen methods work properly
//    public void testBullpenTest() throws Exception {
//        PieceFactory pf = new PieceFactory();
//        Piece tempPiece = pf.getPiece(2);
//        Piece tempPiece2 = pf.getPiece(2);
//        tempPiece2.setUniqueID(10);
//        Bullpen bp = new Bullpen();
//        // adding piece to the bullpen
//        bp.addPiece(tempPiece);
//        // checking if that piece is in the bullpen
//        assertEquals(bp.getPiece(tempPiece),tempPiece);
//        // getting the arraylist of pieces and seeing if it returns properly
//        assertTrue(bp.getPieces().contains(tempPiece));
//        // should return true if piece was properly removed
//        assertTrue(bp.removePiece(tempPiece.uniqueID));
//        // should return false now if piece was properly removed
//        assertFalse(bp.removePiece(tempPiece.uniqueID));
//
//    }
//
//    // test the bullpen piece not found exception
//    @Test(expected=PieceNotFoundException.class)
//    public void testPieceNotFoundException() {
//        PieceFactory pf = new PieceFactory();
//        Piece tempPiece = pf.getPiece(2);
//        tempPiece.setUniqueID(10);
//        Bullpen bp = new Bullpen();
//
//            try {
//                bp.getPiece(tempPiece);
//            } catch (PieceNotFoundException e) {
//                e.printStackTrace();
//            }
//
//    }
//
//    @Test
//    //making sure level model and some board methods work properly
//    public void testLevelTest() throws Exception {
//        LevelModel lm = new LevelModel("release");
//        LevelModel lvm = new LevelModel();
//        assertEquals(lm.getMode(),"release");
//        assertEquals(lm.getTile(1,1).getExists(),true);
//        assertEquals(lm.getBoardTiles().size(),12);
//        assertEquals(lm.getField().getBoardTile(1,1).getExists(),true);
//
//
//
//    }
//
//    @Test
//    //making sure rest of board methods work properly
//    public void testBoardTest() throws Exception {
//        Board board = new Board();
//        PieceFactory pf = new PieceFactory();
//        Piece tempPiece1 = pf.getPiece(2);
//        Piece tempPiece2 = pf.getPiece(1);
//
//        // shouldn't be able to add this piece to this spot
//        assertFalse(board.addPiece(tempPiece1,1,1));
//
//        // should be able to add this piece to this spot
//        assertTrue(board.addPiece(tempPiece1,6,6));
//
//        // shouldn't be able to add this piece to this spot since its already covered
//        assertFalse(board.addPiece(tempPiece2,5,6));
//
//        // shouldn't be remove this piece since its not there
//        assertFalse(board.removePiece(tempPiece2.getPieceBoardNum()));
//
//        // should be able to remove this piece since its there
//        assertTrue(board.removePiece(tempPiece1.getPieceBoardNum()));
//
//        board.setNumColumns(11);
//        board.setNumRows(11);
//
//        // checking if rows and columns updated properly
//        assertTrue(board.getTiles().size()==11);
//        assertTrue(board.getTiles().get(1).size()==11);
//    }
//
//}
//
//
//
