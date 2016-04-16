package PieceFactory;

import KabasujiModel.Piece;

/**
 * Created by Arun on 4/16/2016.
 */
public class PieceFactory {

    public Piece getPiece(int pieceNum){
        if(pieceNum == 1){
            return new Hex1();
        }
        else if(pieceNum == 2){
            return new Hex2();
        }
        else if(pieceNum == 3){
            return new Hex3();
        }
        else if(pieceNum == 4){
            return new Hex4();
        }
        else if(pieceNum == 5){
            return new Hex5();
        }
        else if(pieceNum == 6){
            return new Hex6();
        }
        else if(pieceNum == 7){
            return new Hex7();
        }
        else if(pieceNum == 8){
            return new Hex8();
        }
        else if(pieceNum == 9){
            return new Hex9();
        }
        else if(pieceNum == 10){
            return new Hex10();
        }
        else if(pieceNum == 11){
            return new Hex11();
        }
        else if(pieceNum == 12){
            return new Hex12();
        }
        else if(pieceNum == 13){
            return new Hex13();
        }
        else if(pieceNum == 14){
            return new Hex14();
        }
        else if(pieceNum == 15){
            return new Hex15();
        }
        else if(pieceNum == 16){
            return new Hex16();
        }
        else if(pieceNum == 17){
            return new Hex17();
        }
        else if(pieceNum == 18){
            return new Hex18();
        }
        else if(pieceNum == 19){
            return new Hex19();
        }
        else if(pieceNum == 20){
            return new Hex20();
        }
        else if(pieceNum == 21){
            return new Hex21();
        }
        else if(pieceNum == 22){
            return new Hex22();
        }
        else if(pieceNum == 23){
            return new Hex23();
        }
        else if(pieceNum == 24){
            return new Hex24();
        }
        else if(pieceNum == 25){
            return new Hex25();
        }
        else if(pieceNum == 26){
            return new Hex26();
        }
        else if(pieceNum == 27){
            return new Hex27();
        }
        else if(pieceNum == 28){
            return new Hex28();
        }
        else if(pieceNum == 29){
            return new Hex29();
        }
        else if(pieceNum == 30){
            return new Hex30();
        }
        else if(pieceNum == 31){
            return new Hex31();
        }
        else if(pieceNum == 32){
            return new Hex32();
        }
        else if(pieceNum == 33){
            return new Hex33();
        }
        else if(pieceNum == 34){
            return new Hex34();
        }
        else if(pieceNum == 35){
            return new Hex35();
        }
        else {
            return null;
        }
    }
}
