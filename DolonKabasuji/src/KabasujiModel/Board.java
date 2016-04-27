package KabasujiModel;
import java.util.ArrayList;
/**
 * Created by Arthur on 4/10/2016.
 */
public class Board {
    ArrayList<Tile> tiles;
    ArrayList<Piece> piecesOnBoard;
    int numColumns, numRows;

    public Board() {
        //Initialize tiles
        piecesOnBoard = new ArrayList<Piece>();
    }
    //Takes in a piece to add to the board
    //Adds a piece to the board
    //Returns true if successfully added to the board, false otherwise
    public boolean addPiece(Piece p, int xLoc, int yLoc){
      //  Array
        return false;
    }

    //Takes in a piece to remove from the board
    //Removes a piece from the board
    //Returns true if successfully removed from the board, false otherwise
    public boolean removePiece(Piece p){
        return false;
    }

    public void setNumColumns(int numColumns){
        this.numColumns = numColumns;
    }

    public void setNumRows(int numColumns){
        this.numColumns = numColumns;
    }
}
