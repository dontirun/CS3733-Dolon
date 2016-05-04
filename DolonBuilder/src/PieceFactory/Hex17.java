package PieceFactory;

import BuilderModel.Piece;
import BuilderModel.Square;
import javafx.scene.paint.Color;

/**
 * @author Arun Donti, andonti@wpi.edu
 * Representation of Unique Hexomino 17.
 */
public class Hex17 extends Piece{

    /**
     * Constructor for Hex17.
     */
    public Hex17(){
        super(17);
        construct();
    }

    /**
     * Construct the unique hexonimo type and set each of the six squares
     * to a position relative to the anchor
     */
    protected void construct() {
        Square  s1 = new Square(0,1);
        Square  s2 = new Square(0,0);
        Square  s3 = new Square(0,-1);
        Square  s4 = new Square(0,-2);
        Square  s5 = new Square(-1,0);
        Square  s6 = new Square(1,0);

        this.anchor = s2;
        squares.add(s1);
        squares.add(s2);
        squares.add(s3);
        squares.add(s4);
        squares.add(s5);
        squares.add(s6);
        setColor(Color.SIENNA);
        // only print this if debug is on
        if (DEBUG == 1 ){
            System.out.println("Creating Piece with ID "+pieceID);
        }
        // add accessories
    }
}
