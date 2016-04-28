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
        tiles = new ArrayList<>();
        piecesOnBoard = new ArrayList<>();
        setNumRows(12); //Default initialization is to 12 rows
        setNumColumns(12); //Default initialization is to 12 columns
    }

    public Board(int numRows, int numColumns) {
        tiles = new ArrayList<>();
        piecesOnBoard = new ArrayList<>();
        setNumRows(numRows);
        setNumColumns(numColumns);
    }

    //Takes in a piece to add to the board
    //Adds a piece to the board
    //Returns true if successfully added to the board, false otherwise
    public boolean addPiece(Piece p, int tileRow, int tileColumn){ //In the format (y down the grid, x across)
        if (isValidMove(p, tileRow, tileColumn)) { //if we can make this move
            piecesOnBoard.add(p);
            for (Square s : p.squares){
                int squareColumnOffset = s.getRelCol();
                int squareRowOffset = s.getRelRow();
                Tile tempTile = new Tile();
                tempTile.setSquare(s);
                setBoardTile(tempTile, tileRow + squareRowOffset, tileColumn + squareColumnOffset);
            }
            return true;
        }
        return false;
    }

    //Takes in a piece to remove from the board
    //Removes a piece from the board
    //Returns true if successfully removed from the board, false otherwise
    public boolean removePiece(Piece p){
        return false;
    }

    public boolean isValidMove(Piece p, int tileRow, int tileColumn){ //remember, in the format (y down, x across)
        for (Square s: p.squares){
            int squareColumnOffset = s.getRelCol();
            int squareRowOffset = s.getRelRow();
            if (squareColumnOffset + tileColumn > (numColumns - 1) || squareColumnOffset + tileColumn < 0){ //We're out of bounds vertically
                return false;
            }
            //If it's out of bounds with rows
            if (squareRowOffset + tileRow > (numRows - 1) || squareRowOffset + tileRow < 0){ //We're out of bounds horizontally
                return false;
            }
            //If it's a black tile
            if (!getBoardTile(tileRow + squareRowOffset, tileColumn + squareColumnOffset).getExists()){ //Dang, this location is out of bounds. (Tile is black)
                return false;
            }
            //If it's a tile that's covered by another piece
            if (getBoardTile(tileRow, tileColumn).getCovered()) {
                return false;
            }
        }
        return true;
    }



    /**
     * @author Arthur Dooner ajdooner@wpi.edu
     * Sets the number of columns on a board, removing or adding columms as appropriate
     * @param numColumns Variable that is the number of columns we want
     */
    public boolean setNumColumns(int numColumns){
        int oldColumns = this.numColumns; //Number of columns that we currently have
        this.numColumns = numColumns; //Number of columns that we want
        if (numColumns < 1) { //We shouldn't do this, guys. Come on.
            throw(new IndexOutOfBoundsException("Numcolumns should not be less than 1"));
        }
        if (numRows < 1) { //We really shouldn't do this, either
            throw(new IndexOutOfBoundsException("Can't have no rows here"));
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
        return true;
    }

    /**
     * @author Arthur Dooner ajdooner@wpi.edu
     * Gets the specific tile on a board, pointed to by tileRow and tileColumn
     * @param tileRow Row of the Tile
     * @param tileColumn Column of the Tile
     * @return Tile specific board tile
     */
    public Tile getBoardTile(int tileRow, int tileColumn){
        if (tileRow > numRows - 1) {
            throw(new IndexOutOfBoundsException("Row out of bounds"));
        }
        else if (tileColumn > numColumns - 1) {
            throw(new IndexOutOfBoundsException("Column out of bounds"));
        }
        return tiles.get(tileRow).get(tileColumn);
    }

    /**
     * @author Arthur Dooner ajdooner@wpi.edu
     * Sets the specific tile on a board, pointed to by tileRow and tileColumn
     * @param tempTile Tile to be set in specified place
     * @param tileRow Row of the Tile
     * @param tileColumn Column of the Tile
     */
    public void setBoardTile(Tile tempTile, int tileRow, int tileColumn) {
        if (tileRow > numRows - 1) {
            throw(new IndexOutOfBoundsException("Row out of bounds"));
        }
        else if (tileColumn > numColumns - 1){
            throw (new IndexOutOfBoundsException("Column out of bounds"));
        }
        ArrayList<Tile> tempArrayList = tiles.get(tileRow);
        tempArrayList.set(tileColumn, tempTile);
        tiles.set(tileRow, tempArrayList);

    }

    /**
     * @author Arthur Dooner ajdooner@wpi.edu
     * Sets the number of columns on a board, removing or adding rows as appropriate
     * @param numRows Variable that is the number of rows we want
     */
    public void setNumRows(int numRows){
        int oldRows = this.numRows;
        this.numRows = oldRows;
        if (numRows < 1) {
            throw(new IndexOutOfBoundsException("The number of rows should not be less than 1"));
        }
        if (numRows > oldRows) { //Add more rows to the board
             for (int y = 0; y < (numRows - oldRows); y++) { //Add the number of rows in difference
                 //Make the ArrayList of Tiles to add
                 ArrayList<Tile> tempArrayList = new ArrayList<>();
                 for (int x = 0; x < numColumns; x++) {
                     tempArrayList.add(new Tile());
                 }
                 tiles.add(tempArrayList);
             }
        }
        else if (numRows < oldRows) { //Want to remove columns instead
            for (int y = 0; y < (oldRows - numRows); y++){
                tiles.remove(tiles.size() - 1);
            }
        }
    }
}
