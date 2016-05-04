package BuilderModel;

import javafx.scene.paint.Color;

/** Handles tiles for release levels
 * Created by Walter on 4/23/2016.
 */
public class ReleaseTile extends Tile {
    int num;
    Color color;

    /**
     * Constructor for release tile
     */
    public ReleaseTile(){
        color = Color.WHITE;
    }

    /**
     * Getter for num
     *
     * @return num value of the release tile
     */
    public int getNum(){
        return num;
    }

    /**
     * Setter for num
     *
     * @param num number on the tile
     */
    public void setNum(int num){
        this.num=num;
    }

    /**
     * Returns the color of the num
     *
     * @return color of the num
     */
    public Color getColor(){
        return color;
    }

    /**
     * Sets the color of the num
     *
     * @param color color
     */
    public void setColor(Color color){
        this.color=color;
    }

}


