package KabasujiModel;

import javafx.scene.paint.Color;

/**
 * @author Walter Ho, who@wpi.edu
 * Models a specific release Tile
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
     * Gets the color of the tile
     * @return color
     */
    public Color getColor(){
        return color;
    }

    /**
     * Getter for the Tile Number
     * @return number on the release tile
     */
    public int getNum(){
        return num;
    }

    /**
     * Sets the color of the Tile
     * @param color color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Setter for the tile num
     *
     * @param num number on the release Tile
     */
    public void setNum(int num){
        this.num = num;
    }

}

