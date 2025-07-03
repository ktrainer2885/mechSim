package mechsim.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController {

    @FXML
    private TextArea logArea;

    @FXML
    private void onLoadMechs() {
        log("Load Mechs clicked.");
    }

    @FXML
    private void onStartBattle() {
        log("Start Battle clicked.");
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }
}
