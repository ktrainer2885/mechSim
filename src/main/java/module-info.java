module mechsim {
    requires javafx.controls;
    requires javafx.fxml;

    exports mechsim;
    exports mechsim.model;
    exports mechsim.parser;
    exports mechsim.ui;

    opens mechsim.ui.controller to javafx.fxml;
}
