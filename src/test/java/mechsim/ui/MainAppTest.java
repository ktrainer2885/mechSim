package mechsim.ui;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainAppTest extends ApplicationTest {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        new MainApp().start(stage);
    }

    @Test
    void testWindowOpens() {
        // Check the window opens with the expected title
        assertEquals("Mech Sim Prototype", primaryStage.getTitle());
    }

}