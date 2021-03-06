package KabasujiControllers;

import KabasujiModel.GameMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.IOException;


/**
 * @author Arthur Dooner, ajdooner@wpi.edu
 * @author Arun Donti, andonti@wpi.edu
 * @author Robyn Domanico, rdomanico@wpi.edu
 * Connects the starting menu and settings to the controller and the LevelView
 */
public class StartScreenController {
    @FXML
    Button startKButton, decrementLevel, incrementLevel, aboutKButton;
    @FXML
    Label name, levelNumberLabel;
    @FXML
    ImageView leftArrow, rightArrow, lockIcon;

    // this might need to change
    static GameMenu menu;

    /**
     * Constructor
     */
    public StartScreenController() {
    }

    /**
     * Initializes the StartScreenController.
     */
    @FXML
    public void initialize() throws IOException {
        if (menu == null) {
            menu = new GameMenu();
        }
        // Initializes level accessibility on load
        if (menu.getLevelNumber() > menu.getUnlocked()) {
            lockIcon.setVisible(true);
        } else {
            lockIcon.setVisible(false);
        }
        levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));
    }


    /**
     * Handles the actions of different buttons on the start screen
     * @param event action event
     * @throws IOException
     */
    // checks the event sent out by a certain button
    public void handleButtonAction(ActionEvent event) throws IOException {
        // Start selected level
        if (event.getSource() == startKButton) {
            handleStartKButton();
        }
        // Select previous level (or loop to max level)
        else if (event.getSource() == decrementLevel) {
            handleDecrementLevel();
        }
        // Select next level (or loop to first level)
        else if (event.getSource() == incrementLevel) {
            handleIncrementLevel();
        }
        // Open about window
        else if (event.getSource() == aboutKButton) {
            handleAboutKButton();
        } else {
            //
        }
        //create a new scene with root and set the stage

    }


    /**
     * Handles the start button being pressed.
     * @throws IOException
     */
    public void handleStartKButton() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage = (Stage) startKButton.getScene().getWindow();

        //load up OTHER FXML document
        if (!lockIcon.isVisible()) {
            root = null;
            LevelViewController lvController;
            switch (menu.getLevelNumber() % 3) {
                case 1:
                    //System.out.println("Hi there!");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/puzzleLevel.fxml"));
                    root = (Parent)fxmlLoader.load();
                    // Pass a value to the levelview and load level
                    lvController = fxmlLoader.<LevelViewController>getController();
                    lvController.setMenu(menu);
                    lvController.loadLevel(menu.getLevelNumber());
                    break;
                case 2:
                    // change to lightning later
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/puzzleLevel.fxml"));
                    root = (Parent)fxmlLoader.load();

                    // Pass a value to the levelview and load level
                    lvController = fxmlLoader.<LevelViewController>getController();
                    lvController.setMenu(menu);
                    lvController.loadLevel(menu.getLevelNumber());
                    break;
                case 0:
                    // change to release later
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/puzzleLevel.fxml"));
                    root = (Parent)fxmlLoader.load();

                    // Pass a value to the levelview and load level
                    lvController = fxmlLoader.<LevelViewController>getController();
                    lvController.setMenu(menu);
                    lvController.loadLevel(menu.getLevelNumber());
                    break;

            }

            // Create new scene with root and set stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Handles the level number being decremented.
     */
    public void handleDecrementLevel() {
        menu.decrementLevelNumber();
        //stage = (Stage) decrementLevel.getScene().getWindow();
        levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));

        if (menu.getLevelNumber() > menu.getUnlocked()) {
            lockIcon.setVisible(true);
        } else {
            lockIcon.setVisible(false);
        }
    }

    /**
     * Handles the level number being incremented.
     */
    public void handleIncrementLevel() {
        menu.incrementLevelNumber();
        //stage = (Stage) incrementLevel.getScene().getWindow();
        levelNumberLabel.setText(Integer.toString(menu.getLevelNumber()));

        if (menu.getLevelNumber() > menu.getUnlocked()) {
            lockIcon.setVisible(true);
        } else {
            lockIcon.setVisible(false);
        }
    }

    /**
     * Handles the about button being pressed.
     * @throws IOException
     */
    public void handleAboutKButton() throws IOException {
        final Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/views/about.fxml")); // Get other FXML document
        Scene temp =new Scene(root);

        temp.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
                () {

            @Override
            public void handle(KeyEvent t) {
                if(t.getCode()== KeyCode.ESCAPE)
                {
                    stage.close();
                }
            }
        });

        stage.setScene(temp);
        stage.setTitle("About Kabasuji");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(aboutKButton.getScene().getWindow());
        stage.showAndWait();
    }

    /**
     * Gets the game menu
     * @return the controller's associated game menu
     */
    public GameMenu getMenu(){
        return menu;
    }

}

