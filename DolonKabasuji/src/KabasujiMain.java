import KabasujiModel.GameMenu;
import KabasujiModel.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}
