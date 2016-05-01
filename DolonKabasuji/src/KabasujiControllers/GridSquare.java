package KabasujiControllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Created by Walter on 4/23/2016.
 */
public class GridSquare extends Pane{

        Label numLabel;
        int num;
   //     static boolean[] redNums;

    /**
     * Contructor for GridSquare
     */
        public GridSquare() {
            numLabel = new Label();
            numLabel.setAlignment(Pos.CENTER);
            getChildren().add(numLabel);
           // redNums = new boolean[6];
        }


    /**
     * Sets the number that shows up on the pane
     *
     * @param num desired number to show on the pane
     */
        public void setNumber(int num) {
            this.num = num;
            numLabel.setText("" + num);
            numLabel.isResizable();

        }

    /**
     * Gets the number label, unsure why needed
     *
     * @return label
     */
        public Label getNumLabel() {
            return numLabel;
        }
//    public void setNum(Color color){
//        if(color==Color.RED){
//            numLabel.setText("1");
//            numLabel.setTextFill(Color.RED);
//        }
//    }

}
