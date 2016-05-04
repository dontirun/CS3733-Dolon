package UndoActionManager;

import BuilderModel.Bullpen;
import BuilderModel.Piece;
import BuilderModel.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by slafo on 4/28/2016.
 */
public class RotatePieceAction implements IAction{
    Piece piece;//piece being rotated
    Group pieceGroup;//contains a group and piece
    char direction; //either 'l' for left or 'r' for right
    double rectangleSize;//size of rectangle being drawn

    /**
     * Constructor
     *
     * @param piece piece being rotated
     * @param pieceGroup contains rotating piece and its group
     * @param direction either 'l' for left or 'r' for right
     * @param rectangleSize size of rectangle drawn when rendering
     */
    public RotatePieceAction(Piece piece, Group pieceGroup, char direction, double rectangleSize) {

        this.piece = piece;
        this.pieceGroup = pieceGroup;
        this.direction = direction;
        this.rectangleSize = rectangleSize;
    }

    /**
     * Rotate a piece
     *
     * @return return true if successful
     */
    @Override
    public boolean doAction() {
        // if rotate left button is pressed
        if (direction == 'l') {
            piece.rotatePieceLeft();
        }

        // if rotate right button is pressed
        if (direction== 'r') {
            piece.rotatePieceRight();
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

    /**
     * Undo rotating a piece
     *
     * @return true if successful
     */
    @Override
    public boolean undoAction() {
        // if rotate left button is pressed
        if (direction == 'l') {
            piece.rotatePieceRight();
        }

        // if rotate right button is pressed
        if (direction== 'r') {
            piece.rotatePieceLeft();
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

    /**
     * Redo rotating a piece
     * @return true if successful
     */
    @Override
    public boolean redoAction() {
        // if rotate left button is pressed
        if (direction == 'l') {
            piece.rotatePieceLeft();
        }

        // if rotate right button is pressed
        if (direction== 'r') {
            piece.rotatePieceRight();
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

    /**
     * Check if a rotation is valid
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
}