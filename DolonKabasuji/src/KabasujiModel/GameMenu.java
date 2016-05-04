package KabasujiModel;

import java.io.*;

/**
 * @author Arun Donti, andonti@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * Represents the Menu for the game, allowing for persistence of game states from playthrough to playthrough
 */
public class GameMenu {

    private int levelNumber;
    private int unlocked;
    private int[] maxStars;

    /**
     * Constructor for the game menu
     */
    public GameMenu() throws IOException {
        loadGameStats();
    }

    /**
     * Decrements the level number in GameMenu
     */
    public void decrementLevelNumber(){
        levelNumber--;
        if (levelNumber < 1){
            levelNumber = 15;
        }
    }

    /**
     * Getter for the level number
     * @return returns the number of the level
     */
    public int getLevelNumber(){
        return levelNumber;
    }

    /**
     * Gets the MaxStars of a specified level
     * @param levelNumber Level Number to access the selected stars
     * @return Integer value for the number of stars that has been earned
     */
    public int getMaxStars(int levelNumber) {
        return maxStars[levelNumber-1];
    }

    /**
     * Returns up to what level is unlocked
     * @return unlocked status
     */
    public int getUnlocked() {
        return unlocked;
    }

    /**
     * Increments the level number
     */
    public void incrementLevelNumber(){
        levelNumber++;
        if (levelNumber > 15){
            levelNumber = 1;
        }
    }

    /**
     * Loads the game progress file or uses the default settings if one can't be found
     * @throws IOException if a file is not found
     */
    public void loadGameStats() throws IOException{
        try{
            // Load file
            FileReader input = new FileReader("../gameProgress.dat"); //Go up a level and find the save game
            BufferedReader buf = new BufferedReader(input);
            String dataLine;
            maxStars = new int[15]; //Initialize the number of stars to an int of 15

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

    /**
     * Saves the game progress when the game exits to gameProgress.dat
     * @throws FileNotFoundException if the file
     */
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
     * Sets the levelNumber.
     * @param i the number level to be set
     */
    public void setLevelNumber(int levelNumber){
        this.levelNumber = levelNumber;
    }

    /**
     * Sets the maximum stars earned for a specific level
     * @param levelNumber Level that MaxStars is being set for
     * @param numStars Number of stars unlocked on that level
     */
    public void setMaxStars(int levelNumber, int numStars){
        maxStars[levelNumber - 1] = numStars;
    }

    /**
     * Sets whether the level is unlocked or not.
     * @param unlocked unlocked status
     */
    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }
}
