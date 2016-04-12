package Boundaries;

/**
 * Created by Arthur on 4/10/2016.
 */
import javafx.scene.control.*;
import javafx.scene.control.Button;
import Game.*;


public class LevelView {
    Button backLevel;
    Button forwardLevel;
    Button menu;
    Label levelType;
    Label levelNumber;
    Label starCount;
    Label timer;
    BoardView bv;
    BullpenView bp;
    Level l;

    public LevelView(Level l){
        this.l = l;
    }

}
