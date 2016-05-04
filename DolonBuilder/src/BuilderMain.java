import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuilderMain extends Application {

    @Override
    /** Loads startscreen and its title, scene
     * @param primaryStage top level container
     */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/startscreen.fxml")); // Load scene
        primaryStage.setTitle("Kabasuji Level Builder Start Screen"); // Title
        primaryStage.setScene(new Scene(root, 1280, 720)); // Set stage size
        primaryStage.show(); // Show scene
    }


    public static void main(String[] args) {
        launch(args);
    }
}
