package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Tile {

    Square square;
    boolean exists;
    int covered;

    public Tile() {
        exists = true;
        covered = -1;
    }
    public Tile(boolean exists){
        this.exists = exists;
        covered = -1;
    }
    public boolean getExists(){
        return exists;
    }
    public int getCovered(){
        return covered;
    }
    public void setCovered(int covered) {
        this.covered = covered;
    }

    public void setSquare(Square square, int covered){
        this.covered = covered;
        this.square = square;
    }

    public void removeSquare() {
        covered = -1;
        this.square = null;
    }

    public Square getSquare() {
        return square;
    }
    public void setExists(boolean b){
        exists = b;
    }
    public void flipExists(){
        exists=!exists;
    }
}
