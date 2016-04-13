import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuilderMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/startscreen.fxml")); // Load scene
        primaryStage.setTitle("(Dolon) Kabasuji Level Builder"); // Title
        primaryStage.setScene(new Scene(root, 1280, 720)); // Set stage size
        primaryStage.show(); // Show scene
    }


    public static void main(String[] args) {
        launch(args);
    }
}