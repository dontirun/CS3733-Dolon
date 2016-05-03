package KabasujiModel;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Arthur on 4/11/2016.
 */
public class GameMenu {

    int levelNumber;
    int unlocked;
    int[] maxStars;

    /**
     * Constructor for the game menu
     */
    public GameMenu() throws IOException {
        /*
        levelNumber = 1;
        // max level unlocked
        unlocked =1;
        maxStars = new int[15];
        */
        loadGameStats();
    }

    public void loadGameStats() throws IOException{
        try{
            // Load file
            FileReader input = new FileReader("../gameProgress.dat");
            BufferedReader buf = new BufferedReader(input);
            String dataLine;
            maxStars = new int[15];

            // Parse first line with level number and unlocked
            dataLine = buf.readLine();
            String lineSplit[] = dataLine.split(" ");
            levelNumber = Integer.parseInt(lineSplit[0]);
            unlocked = Integer.parseInt(lineSplit[1]);

            // Parse first line with star information
            dataLine = buf.readLine();
            String starsSplit[] = dataLine.split(" ");
            for(int i = 0; i < starsSplit.length; i++){
                maxStars[i] = Integer.parseInt(starsSplit[i]);
            }
        }
        catch(FileNotFoundException e){ // Use default
            levelNumber = 1;
            unlocked = 1;
            maxStars = new int[15];
        }
    }

    public void saveGameStats() throws FileNotFoundException {
        try{
            FileWriter out;

            out = new FileWriter("../gameProgress.dat");

            // Write first line
            out.write(Integer.toString(levelNumber) + " " + Integer.toString(unlocked));
            out.write("\r\n");

            // Write second line
            for(int i = 0; i < maxStars.length; i++){
                out.write(Integer.toString(maxStars[i]));

                // Add a space between values
                if(i < (maxStars.length - 1)){
                    out.write(" ");
                }
            }

            // Close file
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Getter for the level number
     *
     * @return returns the number of the level
     */
    public int getLevelNumber(){
        return levelNumber;
    }

    /**
     * Setter for the level number
     *
     * @param i the number level to be set
     */
    public void setLevelNumber(int i){
        this.levelNumber = i;
    }

    /**
     * Decrements the level number
     */
    public void decrementLevelNumber(){
        this.levelNumber --;
        if (this.levelNumber <1){
            this.levelNumber =15;
        }
    }

    /**
     * Increments the level number
     */
    public void incrementLevelNumber(){
        this.levelNumber ++;
        if (this.levelNumber >15){
            this.levelNumber =1;
        }
    }

    /**
     * Return whether the level is unlocked or not
     *
     * @return unlocked status
     */
    public int getUnlocked() {
        return unlocked;
    }

    /**
     * Sets whether the level is unlocked or not
     *
     * @param unlocked unlocked status
     */
    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }
    public void setMaxStars(int levelNumber, int numStars){
        maxStars[levelNumber-1]= numStars;
    }

    public int getMaxStars(int levelNumber) {
        return maxStars[levelNumber-1];
    }
}
