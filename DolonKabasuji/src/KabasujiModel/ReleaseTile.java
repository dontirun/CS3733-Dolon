package KabasujiModel;

import javafx.scene.paint.Color;

/**
 * Created by Arthur on 4/10/2016.
 */
public class ReleaseTile extends Tile {
    int num;
    Color color;

    /**
     * Constructor for a release tile
     */
    public ReleaseTile(){
        color = Color.WHITE;
    }

    /**
     * Getter for the tile num
     *
     * @return number on the release tile
     */
    public int getNum(){
        return num;
    }

    /**
     * Setter for the tile num
     *
     * @param num number on the release tile
     */
    public void setNum(int num){
        this.num=num;
    }

    /**
     * Gets the color of the tile
     *
     * @return color
     */
    public Color getColor(){
        return color;
    }

    /**
     * Sets the color of the tile
     *
     * @param color color
     */
    public void setColor(Color color){
        this.color=color;
    }

}

