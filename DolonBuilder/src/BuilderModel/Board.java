package BuilderModel;

import java.util.ArrayList;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Board {
    ArrayList<ArrayList<Tile>> tiles; //Rows holding columns
    ArrayList<Piece> piecesOnBoard;
    int numColumns, numRows;
    static int counter = 0;

    /**
     * Constructor method
     */
    public Board() {
        //Initialize tiles
        tiles = new ArrayList<>();
        piecesOnBoard = new ArrayList<>();
        setNumRows(12); //Default initialization is to 12 rows
        setNumColumns(12); //Default initialization is to 12 columns
    }

    /**
     * Constructor method
     */
    public Board(int numRows, int numColumns) {
        tiles = new ArrayList<>();
        piecesOnBoard = new ArrayList<>();
        setNumRows(numRows);
        setNumColumns(numColumns);
    }

    /**
     * Prints the board for debugging pieces
     */
    public void printBoardAsDebug(){
        for(int x = 0; x < numRows; x++){
            String tempString = "";
            for (int y = 0; y < numColumns; y++) {
                if (!getBoardTile(x, y).getExists()) {
                    tempString += "0 ";
                }
                else if (getBoardTile(x, y).getCovered() > -1){
                    tempString += getBoardTile(x, y).getCovered() + " ";
                }
                else if (getBoardTile(x, y).getExists()) {
                    tempString += "_ ";
                }
            }
            System.out.println(tempString);
        }
    }


    /**
     * @author Arthur Dooner ajdooner@wpi.edu
     * Adds a piece to the board
     * @param p Piece used to take device results
     * @param tileRow specific row to base the anchor around
     * @param tileColumn specific column to base the anchor around
     * @return true if successfully added, false otherwise
     */
    public boolean addPiece(Piece p, int tileRow, int tileColumn){ //In the format (y down the grid, x across)
        if (isValidMove(p, tileRow, tileColumn)) { //if we can make this move
            p.flipPieceVert();
            piecesOnBoard.add(p);
            for (Square s : p.squares){
                int squareColumnOffset = s.getRelCol();
                int squareRowOffset = s.getRelRow();
                // changed to release tile for now
                ReleaseTile tempTile = (ReleaseTile)tiles.get(tileRow + squareRowOffset).get(tileColumn + squareColumnOffset);
                tempTile.setSquare(s, p.getUniqueID());
                setBoardTile(tempTile, tileRow + squareRowOffset, tileColumn + squareColumnOffset);
            }
            counter++;
            return true;
        }
        return false;
    }

    /**
     * Takes in a unique pieceOnBoardNum to remove a piece from the board
     * @param pieceOnBoardNum the unique identifier for the piece
     * @return true if successfully removed from the board, false otherwise
     */
    public boolean removePiece(int pieceOnBoardNum){
        for (ArrayList<Tile> a : tiles){ //Iterate over all the rows
            for (Tile t : a) { //Iterate over all the columns
                if (t.getCovered() == pieceOnBoardNum){  //If pieceNums are the same
                    t.removeSquare(); //Clear the square
                }
            }
        }
        for (int x = 0; x < piecesOnBoard.size(); x++){ //Iterate over all the pieces
            if (piecesOnBoard.get(x).getUniqueID() == pieceOnBoardNum){ //If the piece board numbers are the same
                piecesOnBoard.remove(x);
                return true;
            }
        }
        return false;
    }

    /**
     * @author Arthur Dooner ajdooner@wpi.edu
     * Checks if a move is valid
     * @param p Piece used to take device results
     * @param tileRow specific row to base the anchor around
     * @param tileColumn specific column to base the anchor around
     */
    public boolean isValidMove(Piece p, int tileRow, int tileColumn){ //remember, in the format (y down, x across)
        for (Square s: p.squares){
            int squareColumnOffset = s.getRelCol();
            int squareRowOffset = (s.getRelRow()*-1);
            if (squareColumnOffset + tileColumn > (numColumns - 1) || squareColumnOffset + tileColumn < 0){ //We're out of bounds vertically
                System.out.println("rip its out of bounds vertically");
                return false;
            }
            //If it's out of bounds with rows
            if (squareRowOffset + tileRow > (numRows - 1) || squareRowOffset + tileRow < 0){ //We're out of bounds horizontally
                System.out.println("rip its out of bounds horizontally");
                return false;
            }
            //If it's a black tile
            if (!getBoardTile(tileRow + squareRowOffset, tileColumn + squareColumnOffset).getExists()){ //Dang, this location is out of bounds. (Tile is black)
                System.out.println("rip the tile doesn't exist");
                return false;
            }
            //If it's a tile that's covered by another piece
            if (getBoardTile(tileRow + squareRowOffset, tileColumn + squareColumnOffset).getCovered() > 0) {
                System.out.println("rip its covered");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the piece is out of bounds
     *
     * @param p piece used to take device results
     * @param tileRow specific row to base the anchor around
     * @param tileColumn specific column to base the anchor around
     * @return
     */
    public boolean isOutOfBounds(Piece p, int tileRow, int tileColumn) {
        for (Square s: p.squares) {
            int squareColumnOffset = s.getRelCol();
            int squareRowOffset = (s.getRelRow()*-1);
            if (squareColumnOffset + tileColumn > (numColumns - 1) || squareColumnOffset + tileColumn < 0) { //We're out of bounds vertically
                return false;
            }
            //If it's out of bounds with rows
            if (squareRowOffset + tileRow > (numRows - 1) || squareRowOffset + tileRow < 0) { //We're out of bounds horizontally
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
                    // changed to release tile for now
                    tempArrayList.add(new ReleaseTile()); //Creates a new, valid tile in our grid
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

    public ArrayList<ArrayList<Tile>> getTiles(){
        return tiles;
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
        this.numRows = numRows;
        if (numRows < 1) {
            throw(new IndexOutOfBoundsException("The number of rows should not be less than 1"));
        }
        if (numRows > oldRows) { //Add more rows to the board
             for (int y = 0; y < (numRows - oldRows); y++) { //Add the number of rows in difference
                 //Make the ArrayList of Tiles to add
                 ArrayList<Tile> tempArrayList = new ArrayList<>();
                 for (int x = 0; x < numColumns; x++) {
                     // changed to release tile for now
                     tempArrayList.add(new ReleaseTile());
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

    /**
     * @param id of piece to be searched for
     * @return piece that was desired
     * @throws PieceNotFoundException
     */
    public Piece getPieceFromID(int id) throws PieceNotFoundException {
        for (Piece p : piecesOnBoard) {
            if (p.getUniqueID() == id) {
                return p;
            }
        }
        //We didn't get anything; uh oh!
        throw new PieceNotFoundException("Could not find piece with unique ID: " + id);
    }

    /**
     * Gets all the piece IDs- primarily for saving purposes
     * @return ArrayList<Integer> of pieces
     */
    public ArrayList<Integer> getPieceIDs(){
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for(Piece p: piecesOnBoard){
            ids.add(p.getPieceID());
        }

        return ids;
    }

    /**
     * Clears the pieces on the board
     */
    public void clearBoard() {
        tiles.clear();
        piecesOnBoard.clear();
    }
}
