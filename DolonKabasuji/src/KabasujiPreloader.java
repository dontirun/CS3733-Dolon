import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;

/**
 * Created by slafo on 4/13/2016.
 */
public class KabasujiPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene(){
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        p.setStyle("-fx-background-image: url('images/preloaderSplash.png')");
        return new Scene(p, 500, 300);
    }

    public void start(Stage stage) throws Exception{
        this.stage = stage;
        stage.setTitle("Kabasuji Preloader");
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn){
        bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt){
        if(evt.getType() == StateChangeNotification.Type.BEFORE_START){
            stage.hide();
        }
    }
}
