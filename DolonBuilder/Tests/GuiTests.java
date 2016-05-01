import BuilderControllers.LevelBuilderController;
import BuilderModel.Bullpen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

import static com.sun.jmx.snmp.ThreadContext.contains;
import static java.awt.SystemColor.text;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChildren;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;


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
    public void startBuilderTest() throws Exception {
        // load into the level builder
        clickOn("#startButton");
        assertEquals("Kabasuji Builder", stage.getTitle());

        // check that everything is empty
        verifyThat(".levelTextField", NodeMatchers.isNull());
        verifyThat(".bullpenView", NodeMatchers.isNull());
        verifyThat("#levelNumber", hasText("#"));
        verifyThat("#greenButton", isInvisible());
        verifyThat("#yellowButton", isInvisible());
        verifyThat("#redButton", isInvisible());
        verifyThat("#rowsTextField", NodeMatchers.hasText("12"));
        verifyThat("#colsTextField", NodeMatchers.hasText("12"));

        GridPane board = lookup("#boardView").query();

        assertTrue(board.getChildren().size() == 144);
    }

    @Test
    public void loadLevel1() throws Exception {
        // load into the level builder
        clickOn("#startButton");

        clickOn("#levelTextField");
        write("1");
        clickOn("#loadButton");

        verifyThat("#levelNumber", hasText("1"));
    }


}
