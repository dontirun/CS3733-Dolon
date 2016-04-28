package PieceFactory;

import BuilderModel.Piece;
import BuilderModel.Square;

/**
 * Created by Arun on 4/16/2016.
 */
public class Hex34 extends Piece{

    public Hex34(){
        super(34);
        construct();
    }

    protected void construct() {
        Square  s1 = new Square(0,0);
        Square  s2 = new Square(0,-1);
        Square  s3 = new Square(-1,0);
        Square  s4 = new Square(-1,-1);
        Square  s5 = new Square(1,0);
        Square  s6 = new Square(1,1);

        this.anchor = s1;
        squares.add(s1);
        squares.add(s2);
        squares.add(s3);
        squares.add(s4);
        squares.add(s5);
        squares.add(s6);

        // only print this if debug is on
        if (DEBUG == 1 ){
            System.out.println("Creating Piece with ID "+pieceID);
        }
        // add accessories
    }
}