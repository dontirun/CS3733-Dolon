import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Shows Preloader screen with everyone's names
 * Created by slafo on 4/13/2016.
 */
public class BuilderPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;

    /** Creates the preloader scene
     *
     * @return preloader Scene object to be displayed
     */
    private Scene createPreloaderScene(){
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        p.setStyle("-fx-background-image: url('images/preloaderSplash.png')");
        return new Scene(p, 500, 300);
    }

    /** Sets title and scene and gets stage
     *
     * @param stage top level container
     * @throws Exception
     */
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        stage.setTitle("Kabasuji Preloader");
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    /**Handles progress notification
     * @param pn progress notification to be displayed
     */
    public void handleProgressNotification(ProgressNotification pn){
        bar.setProgress(pn.getProgress());
    }

    @Override
    /** Handles state change notification event for progress notification
     * @param evt stateChangeNotification event occuring
     */
    public void handleStateChangeNotification(StateChangeNotification evt){
        if(evt.getType() == StateChangeNotification.Type.BEFORE_START){
            stage.hide();
        }
    }
}
