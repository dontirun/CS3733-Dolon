package PieceFactory;

import BuilderModel.Piece;

/**
 * Created by Arun on 4/16/2016.
 */
public class PieceFactory {

    public Piece getPiece(int pieceNum){
        switch (pieceNum) {
            case 1:
                return new Hex1();
            case 2:
                return new Hex2();
            case 3:
                return new Hex3();
            case 4:
                return new Hex4();
            case 5:
                return new Hex5();
            case 6:
                return new Hex6();
            case 7:
                return new Hex7();
            case 8:
                return new Hex8();
            case 9:
                return new Hex9();
            case 10:
                return new Hex10();
            case 11:
                return new Hex11();
            case 12:
                return new Hex12();
            case 13:
                return new Hex13();
            case 14:
                return new Hex14();
            case 15:
                return new Hex15();
            case 16:
                return new Hex16();
            case 17:
                return new Hex17();
            case 18:
                return new Hex18();
            case 19:
                return new Hex19();
            case 20:
                return new Hex20();
            case 21:
                return new Hex21();
            case 22:
                return new Hex22();
            case 23:
                return new Hex23();
            case 24:
                return new Hex24();
            case 25:
                return new Hex25();
            case 26:
                return new Hex26();
            case 27:
                return new Hex27();
            case 28:
                return new Hex28();
            case 29:
                return new Hex29();
            case 30:
                return new Hex30();
            case 31:
                return new Hex31();
            case 32:
                return new Hex32();
            case 33:
                return new Hex33();
            case 34:
                return new Hex34();
            case 35:
                return new Hex35();
            default:
                return null;
        }
    }
}
