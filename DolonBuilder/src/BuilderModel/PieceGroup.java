package BuilderModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static BuilderControllers.LevelBuilderController.pieceShape;


/**
 * Created by Arthur on 4/25/2016.
 */
public class PieceGroup {
    private Piece piece;
    private static final double RectangleSize = 45.83333333;
    private final BooleanProperty dragModeActiveProperty = new SimpleBooleanProperty(this, "dragModeActive", true);
    private Group pieceGroup;
    public PieceGroup(Piece piece){
        super();
        this.piece = piece;
        generateShapeFromPiece(this.piece);
    }

    /**
     * Generates group of squares from the piece
     *
     * @param pieceToDraw
     */
    public void generateShapeFromPiece(final Piece pieceToDraw) {
        pieceGroup = new Group();
        for (Square selectedSquare : pieceToDraw.squares) { //Iterate over all of the squares in the piece
            Rectangle selectedRectangle = new Rectangle();
            selectedRectangle.setX((selectedSquare.getRelCol()) * RectangleSize); //Set Y position based on the Relative Column
            selectedRectangle.setY((-selectedSquare.getRelRow()) * RectangleSize); //Set Y position based on the Relative Row
            selectedRectangle.setWidth(RectangleSize); //Set the width of each rectangle
            selectedRectangle.setHeight(RectangleSize); //Set the height of each rectangle
            selectedRectangle.setFill(pieceToDraw.getColor()); //Color the fill with the preset color
            selectedRectangle.setStroke(Color.BLACK); //Color the outline
            pieceGroup.getChildren().add(selectedRectangle);
        }
        //pieceGroup = makeDraggable(pieceGroup);

        pieceGroup.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = pieceGroup.startDragAndDrop(TransferMode.MOVE);
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.put(pieceShape, piece); //CHANGED: NOW HANDS OVER CLIPBOARD CONTENT
                db.setContent(content);
                System.out.println("Drag Detected");
                event.consume();
            }
        });

        pieceGroup.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    pieceGroup.setVisible(false);
                }
                System.out.println("Drag Done");
                event.consume();
            }
        });
    }

    /**
     * getter for piece
     *
     * @return piece
     */
    public Piece getPiece(){
        return piece;
    }


    /**
     * Makes a group draggable, may or may not be implemented
     *
     * @param node
     * @return Group
     */
    private Group makeDraggable(final Group node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = node;

        wrapGroup.addEventFilter(
                MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // disable mouse events for all children
                            mouseEvent.consume();
                        }
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // remember initial mouse cursor coordinates
                            // and node position
                            dragContext.mouseAnchorX = mouseEvent.getX();
                            dragContext.mouseAnchorY = mouseEvent.getY();
                            dragContext.initialTranslateX =
                                    node.getTranslateX();
                            dragContext.initialTranslateY =
                                    node.getTranslateY();
                        }
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        if (dragModeActiveProperty.get()) {
                            // shift node from its initial position by delta
                            // calculated from mouse cursor movement
                            node.setTranslateX(
                                    dragContext.initialTranslateX
                                            + mouseEvent.getX()
                                            - dragContext.mouseAnchorX);
                            node.setTranslateY(
                                    dragContext.initialTranslateY
                                            + mouseEvent.getY()
                                            - dragContext.mouseAnchorY);
                        }
                    }
                });

        return wrapGroup;
    }

    /**
     * Returns the view of the piece
     *
     * @return group of squares representing the piece
     */
    public Group getGroup(){
        return pieceGroup;
    }
    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
