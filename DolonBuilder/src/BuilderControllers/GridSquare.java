package BuilderControllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Created by Walter on 4/23/2016.
 */
public class GridSquare extends Pane{

        Label numLabel;
        int num;
   //     static boolean[] redNums;

        public GridSquare() {
            numLabel = new Label();
            numLabel.setAlignment(Pos.CENTER);
            getChildren().add(numLabel);
           // redNums = new boolean[6];
        }



        public void setNumber(int num) {
            this.num = num;
            numLabel.setText("" + num);
            numLabel.isResizable();

        }

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
