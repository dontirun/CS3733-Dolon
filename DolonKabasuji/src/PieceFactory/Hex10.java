package PieceFactory;

import KabasujiModel.Piece;
import KabasujiModel.Square;
import javafx.scene.paint.Color;

/**
 * @author Arun Donti, andonti@wpi.edu
 * Representation of Unique Hexomino 10.
 */
public class Hex10 extends Piece{

    /**
     * Constructor for Hex10.
     */
    public Hex10(){
        super(10);
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
        Square  s5 = new Square(1,1);
        Square  s6 = new Square(2,1);

        this.anchor = s2;
        squares.add(s1);
        squares.add(s2);
        squares.add(s3);
        squares.add(s4);
        squares.add(s5);
        squares.add(s6);
        setColor(Color.DARKMAGENTA);
        // only print this if debug is on
        if (DEBUG == 1 ){
            System.out.println("Creating Piece with ID "+pieceID);
        }
        // add accessories
    }
}
