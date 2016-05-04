package UndoActionManager;

import BuilderModel.Bullpen;
import BuilderModel.Piece;
import BuilderModel.PieceGroup;
import BuilderModel.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Handles flipping of a piece
 * Created by slafo on 4/28/2016.
 */
public class FlipPieceAction implements IAction {
    Piece piece;
    Group pieceGroup;
    char direction; //either 'h' for horizontal or 'v' for vertical
    double rectangleSize;

    /**
     * Constructor
     *
     * @param piece the piece being flipped
     * @param pieceGroup piece group associated with piece
     * @param direction the direction to flip, either 'h' for horizontal or 'v' for vertical
     */
    public FlipPieceAction(Piece piece, Group pieceGroup, char direction, double rectangleSize) {

        this.piece = piece;
        this.pieceGroup = pieceGroup;
        this.direction = direction;
        this.rectangleSize = rectangleSize;
    }

    /**
     * Flip the piece
     *
     * @return true if successful
     */
    @Override
    public boolean doAction() {
        if (isValid()) {
//flip the piece
            return flipIt();
        }
        return false;
    }

    /**
     * Undo the flip
     *
     * @return true if successful
     */
    @Override
    public boolean undoAction() {
        //flip the piece again
        return flipIt();
    }

    /**
     * Redo the flip
     * @return true if successful
     */
    @Override
    public boolean redoAction() {
        //flip the piece again
        return flipIt();
    }

    /**
     * Check if the flip action is valid
     *
     * @return true if valid
     */
    @Override
    public boolean isValid() {
        if (piece == null) {
            return false;
        }
        return true;
    }
    /**
     * Draws an individual rectangle for a given square (used for GUI elements)
     *
     * @param selectedSquare the square that makes up a group that is to be drawn
     * @return individual square used in a group
     */
    public Rectangle drawPieceRectangle(Square selectedSquare) {
        Rectangle selectedRectangle = new Rectangle();
        selectedRectangle.setX((selectedSquare.getRelCol()) * rectangleSize); //Set X position based on the Relative Column
        selectedRectangle.setY((-selectedSquare.getRelRow()) * rectangleSize); //Set Y position based on the Relative Row
        selectedRectangle.setWidth(rectangleSize); //Set the width of each rectangle
        selectedRectangle.setHeight(rectangleSize); //Set the height of each rectangle
        selectedRectangle.setFill(Color.RED); //Color the fill
        selectedRectangle.setStroke(Color.BLACK); //Color the outline

        return selectedRectangle;
    }

    /** Flips the piece
     *
     * @return true on success
     */
    public boolean flipIt() {
        // if flip horizontal button is pressed
        if (direction == 'h') {
            piece.flipPieceHoriz();
        }

        // if flip vertical button is pressed
        if (direction == 'v') {
            // highlighting the border of the selected button
            piece.flipPieceVert();
        }

        // clears the squares in the group and adds in the repositioned ones
        pieceGroup.getChildren().clear();
        for (Square selectedSquare : piece.squares) {
            Rectangle selectedRectangle = drawPieceRectangle(selectedSquare);
            selectedRectangle.setFill(piece.getColor());
            pieceGroup.getChildren().add(selectedRectangle);
        }
        return true;
    }
}
