import KabasujiModel.GameMenu;
import KabasujiModel.LevelModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * @author Walter Ho, who@wpi.edu
 * Starts the game and loads the opening scene
 */
public class KabasujiMain extends Application {

    GameMenu currentGameMenu;

    @Override
    /**
     * Initializes the stage to start playing Kabasuji, bringing us to the startscreen.fxml
     */
    public void start(Stage primaryStage) throws Exception{
        this.currentGameMenu = new GameMenu();
        Parent root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
        primaryStage.setTitle("Dolon Kabasuji");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    /**
     * Launches Kabasuji.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
