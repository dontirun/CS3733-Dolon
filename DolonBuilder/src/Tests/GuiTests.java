//package Tests;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import org.junit.Test;
//import org.loadui.testfx.GuiTest;
//
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * Created by slafo on 4/30/2016.
// */
//public class GuiTests extends GuiTest {
//
//    //----------------------------------------------------------------------------------------------------------
//    protected Parent getRootNode() {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        try (InputStream inputStream = getClass()
//                .getResourceAsStream("/views/levelbuilder.fxml")) {
//            return fxmlLoader.load(inputStream);
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Test
//    public void startButtonTest() throws Exception {
//
//        click(".startButton");
//
//        // Wait for success with a 10s timeout
//        // waitUntil("#l", is(visible()), 10);
//
//        //String text = ((Label) find("#showLabel")).getText();
//        // verifyThat(text, containsString("OK"));
//    }
//}
