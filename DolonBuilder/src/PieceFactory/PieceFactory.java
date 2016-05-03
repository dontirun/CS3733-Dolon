package PieceFactory;

import BuilderModel.Piece;

/**
 * Created by Arun on 4/16/2016.
 */
public class PieceFactory {

    static int counter = 0;

    /**
     * Gets a piece of the desired number
     *
     * @param pieceNum desired piece number
     * @return the piece
     */
    public Piece getPiece(int pieceNum){
        counter++;
        Piece tempPiece;
        switch (pieceNum) {
            case 1:
                tempPiece = new Hex1();
                break;
            case 2:
                tempPiece = new Hex2();
                break;
            case 3:
                tempPiece = new Hex3();
                break;
            case 4:
                tempPiece = new Hex4();
                break;
            case 5:
                tempPiece = new Hex5();
                break;
            case 6:
                tempPiece = new Hex6();
                break;
            case 7:
                tempPiece = new Hex7();
                break;
            case 8:
                tempPiece = new Hex8();
                break;
            case 9:
                tempPiece = new Hex9();
                break;
            case 10:
                tempPiece = new Hex10();
                break;
            case 11:
                tempPiece = new Hex11();
                break;
            case 12:
                tempPiece = new Hex12();
                break;
            case 13:
                tempPiece = new Hex13();
                break;
            case 14:
                tempPiece = new Hex14();
                break;
            case 15:
                tempPiece = new Hex15();
                break;
            case 16:
                tempPiece = new Hex16();
                break;
            case 17:
                tempPiece = new Hex17();
                break;
            case 18:
                tempPiece = new Hex18();
                break;
            case 19:
                tempPiece = new Hex19();
                break;
            case 20:
                tempPiece = new Hex20();
                break;
            case 21:
                tempPiece = new Hex21();
                break;
            case 22:
                tempPiece = new Hex22();
                break;
            case 23:
                tempPiece = new Hex23();
                break;
            case 24:
                tempPiece = new Hex24();
                break;
            case 25:
                tempPiece = new Hex25();
                break;
            case 26:
                tempPiece = new Hex26();
                break;
            case 27:
                tempPiece = new Hex27();
                break;
            case 28:
                tempPiece = new Hex28();
                break;
            case 29:
                tempPiece = new Hex29();
                break;
            case 30:
                tempPiece = new Hex30();
                break;
            case 31:
                tempPiece = new Hex31();
                break;
            case 32:
                tempPiece = new Hex32();
                break;
            case 33:
                tempPiece = new Hex33();
                break;
            case 34:
                tempPiece = new Hex34();
                break;
            case 35:
                tempPiece = new Hex35();
                break;
            default:
                return null;
        }
        tempPiece.setUniqueID(counter);
        return tempPiece;
    }
}
