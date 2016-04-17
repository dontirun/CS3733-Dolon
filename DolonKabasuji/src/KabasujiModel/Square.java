package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Square {
    int relCol;
    int relRow;

    public Square(int relCol, int relRow){
        this.relCol = relCol;
        this.relRow = relRow;
    }

    public int getRelCol() {
        return relCol;
    }

    public void setRelCol(int relCol) {
        this.relCol = relCol;
    }

    public int getRelRow() {
        return relRow;
    }

    public void setRelRow(int relRow) {
        this.relRow = relRow;
    }
}
