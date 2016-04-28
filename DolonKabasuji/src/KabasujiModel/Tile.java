package KabasujiModel;

/**
 * Created by Arthur on 4/10/2016.
 */
public class Tile {

    Square square;
    boolean exists;
    boolean covered;

    public Tile() {
        exists = true;
        covered = false;
    }
    public Tile(boolean exists){
        this.exists = exists;
        covered = false;
    }
    public boolean getExists(){
        return exists;
    }
    public boolean getCovered(){
        return covered;
    }
    public void flipCovered() {covered = !covered; }

    public void setSquare(Square square){
        covered = true;
        this.square = square;
    }

    public void removeSquare() {
        covered = false;
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
