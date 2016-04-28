package KabasujiModel;

import javafx.scene.paint.Color;

/**
 * Created by Arthur on 4/10/2016.
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

    /** returns the square covering it if it has one, null otherwise
     *
     * @return
     */
    public Square isCovered(){
        return super.square;
    }
}

