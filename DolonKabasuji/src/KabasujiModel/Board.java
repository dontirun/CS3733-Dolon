package KabasujiModel;
import java.util.ArrayList;
/**
 * Created by Arthur on 4/10/2016.
 */
public class Board {
    ArrayList<ArrayList<Tile>> tiles; //Rows holding columns
    ArrayList<Piece> piecesOnBoard;
    int numColumns, numRows;

    public Board() {
        //Initialize tiles
        piecesOnBoard = new ArrayList<Piece>();
        numColumns = 0;
        numRows = 0;
    }
    //Takes in a piece to add to the board
    //Adds a piece to the board
    //Returns true if successfully added to the board, false otherwise
    public boolean addPiece(Piece p, int xLoc, int yLoc){

        return false;
    }

    //Takes in a piece to remove from the board
    //Removes a piece from the board
    //Returns true if successfully removed from the board, false otherwise
    public boolean removePiece(Piece p){
        return false;
    }

    //Sets the number of columns for the board
    public void setNumColumns(int numColumns){
        int oldColumns = this.numColumns; //Number of columns that we currently have
        this.numColumns = numColumns; //Number of columns that we want
        if (numColumns < 1){ //We shouldn't do this, guys. Come on.
            throw(new IndexOutOfBoundsException("Numcolumns should not be less than 1"));
        }
        //Iterate down the rows
        for (int y = 0; y < numRows; y++){
            ArrayList<Tile> tempArrayList = tiles.get(y); //Get the temporary arraylist

            if (numColumns > oldColumns){ //If the number of columns we want is larger
                for (int x = 0; x < (numColumns - oldColumns); x++){ //Add the difference
                    tempArrayList.add(new Tile()); //Creates a new, valid tile in our grid
                }
            }
            else if (numColumns < oldColumns){ //If we want fewer columns
                for (int x = 0; x < (oldColumns - numColumns); x++){ //Remove the difference
                    tempArrayList.remove(tempArrayList.size() - 1); //Remove the last element on the array as many times as possible
                }
            }
            tiles.set(y, tempArrayList); //Sets the new tiles to have this arraylist info
        }
    }

    public void setNumRows(int numColumns){
        this.numColumns = numColumns;
    }
}
