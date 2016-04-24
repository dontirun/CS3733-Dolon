package BuilderModel;

import javafx.scene.paint.Color;

/**
 * Created by Walter on 4/23/2016.
 */
public class ReleaseTile extends Tile {
    int num;
    Color color;

    public ReleaseTile(){
        color = Color.WHITE;
    }
    public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num=num;
    }
    public Color getColor(){
        return color;
    }
    public void setColor(Color color){
        this.color=color;
    }

}
