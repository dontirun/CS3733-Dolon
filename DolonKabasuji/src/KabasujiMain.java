import KabasujiModel.GameMenu;
import KabasujiModel.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KabasujiMain extends javafx.application.Application {

    Level currentLevel;
    GameMenu currentGameMenu;

    public KabasujiMain(){
        this.currentGameMenu = new GameMenu();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
        primaryStage.setTitle("Dolon Kabasuji");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    // Level parsing function
    // TODO: Use some set of globals or way to pass values into a level
    public void loadLevel(int levelNum) throws IOException {

        // Variables for level information
        // NOTE: will most likely be moved to more global variables
        int lvNum;
        int rows;
        int columns;
        int metric;
        ArrayList<Integer> pieces = new ArrayList<Integer>();
        ArrayList<Integer> tiles = new ArrayList<Integer>();

        // Starts at 0 because file begins with ### and will automatically increment
        int readCount = 0; // Determines what part of the files is being parsed

        try {
            // Parsing objects
            FileReader input = new FileReader(levelNum+".bdsm"); // Read in file
            BufferedReader buf = new BufferedReader(input);
            String dataLine;

            // Parse file
            while((dataLine = buf.readLine()) != null){
                if(dataLine.contains("###")){ // Go to next section
                    readCount++;
                }
                else{ // Information to parse
                    switch(readCount){
                        case 1: // Level Number
                            lvNum = Integer.parseInt(dataLine);
                            break;
                        case 2: // Rows
                            rows = Integer.parseInt(dataLine);
                            break;
                        case 3: // Columns
                            columns = Integer.parseInt(dataLine);
                            break;
                        case 4: // Metric
                            metric = Integer.parseInt(dataLine);
                            break;
                        case 5: // Pieces
                            pieces.add(Integer.parseInt(dataLine));
                            break;
                        case 6: // Tiles
                            tiles.add(Integer.parseInt(dataLine));
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
