package mechsim.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// JavaFX application bootstrap that loads the main FXML view.
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        final Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Mech Sim Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
