import KabasujiModel.GameMenu;
import KabasujiModel.LevelModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class KabasujiMain extends Application {

    LevelModel currentLevelModel;
    GameMenu currentGameMenu;


    public KabasujiMain() throws IOException {
        this.currentGameMenu = new GameMenu();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.currentGameMenu = new GameMenu();
        Parent root = FXMLLoader.load(getClass().getResource("/views/startscreen.fxml"));
        primaryStage.setTitle("Dolon Kabasuji");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
