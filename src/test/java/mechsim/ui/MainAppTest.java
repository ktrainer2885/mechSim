package mechsim.ui;

import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainAppTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new MainApp().start(stage);
    }

    @Test
    void testWindowOpens() {
        Stage primaryStage = null;
        // Look through all JavaFX windows and grab the first visible Stage.
        for (Window window : Window.getWindows()) {
            if (window.isShowing() && window instanceof Stage) {
                primaryStage = (Stage) window;
                break;
            }
        }
        if (primaryStage == null) {
            throw new IllegalStateException("No showing Stage found");
        }
        // Check the window opens with the expected title
        assertEquals("Mech Sim Prototype", primaryStage.getTitle());
    }

}
