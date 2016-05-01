import BuilderControllers.LevelBuilderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.SystemColor.text;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


/**
 * Created by slafo on 4/30/2016.
 */
public class GuiTests extends ApplicationTest {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new BuilderMain().start(primaryStage);
        stage = primaryStage;
    }

    @Test
    public void startButtonTest() throws Exception {
        clickOn("#startButton");

        assertEquals("Kabasuji Builder", stage.getTitle());
        verifyThat(".levelTextField", NodeMatchers.isNull());
        verifyThat(".bullpenView", NodeMatchers.isNull());
        verifyThat("#levelNumber", hasText("#"));
}

}
