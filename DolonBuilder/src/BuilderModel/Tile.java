package BuilderModel;

/**
 * Created by Walter on 4/16/2016.
 */
public class Tile {


    boolean exists;

    public Tile() {
exists=true;
    }
    public boolean getExists(){
        return exists;
    }
    public void setExists(boolean b){
        exists=b;
    }
    public void flipExists(){
        exists=!exists;
    }
}
