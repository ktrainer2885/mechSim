package mechsim.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mechsim.model.Mech;
import mechsim.model.Team;
import mechsim.parser.MechFileParser;
import mechsim.ui.TeamListFormatter;

import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainController {

    @FXML
    private TextArea logArea;

    private final Map<String, String> mechResources = new LinkedHashMap<>();
    @FXML
    private TextField team1NameField;
    @FXML
    private TextField team2NameField;
    @FXML
    private ComboBox<String> team1MechChoice;
    @FXML
    private ComboBox<String> team2MechChoice;
    @FXML
    private ListView<String> team1List;
    @FXML
    private ListView<String> team2List;
    private Team team1;
    private Team team2;

    @FXML
    private void initialize() {
        mechResources.put("Highlander HGN-733", "data/mechs/Highlander HGN-733.mtf");
        final var mechNames = FXCollections.observableArrayList(mechResources.keySet());
        team1MechChoice.setItems(mechNames);
        team2MechChoice.setItems(mechNames);
        team1MechChoice.getSelectionModel().selectFirst();
        team2MechChoice.getSelectionModel().selectFirst();
    }

    @FXML
    private void onLoadMechs() {
        team1 = new Team(getTeamName(team1NameField, "Team 1"));
        team2 = new Team(getTeamName(team2NameField, "Team 2"));

        final String team1MechName = team1MechChoice.getSelectionModel().getSelectedItem();
        final String team2MechName = team2MechChoice.getSelectionModel().getSelectedItem();
        final Mech team1Mech = loadMechFromResource(getResourcePath(team1MechName));
        final Mech team2Mech = loadMechFromResource(getResourcePath(team2MechName));
        if (team1Mech == null || team2Mech == null) {
            log("Failed to load selected mechs.");
            return;
        }

        team1.addMech(team1Mech);
        team2.addMech(team2Mech);

        refreshTeamLists();
        log("Loaded " + team1Mech + " into " + team1.getName() + ".");
        log("Loaded " + team2Mech + " into " + team2.getName() + ".");
    }

    @FXML
    private void onStartBattle() {
        log("Start Battle clicked.");
    }

    @FXML
    private void onClearTeams() {
        team1 = new Team(getTeamName(team1NameField, "Team 1"));
        team2 = new Team(getTeamName(team2NameField, "Team 2"));
        refreshTeamLists();
        log("Cleared team assignments.");
    }

    private void refreshTeamLists() {
        team1List.setItems(FXCollections.observableArrayList(
                TeamListFormatter.format(team1)));
        team2List.setItems(FXCollections.observableArrayList(
                TeamListFormatter.format(team2)));
    }

    private Mech loadMechFromResource(String resourcePath) {
        try {
            final URL resource = getClass().getClassLoader().getResource(resourcePath);
            if (resource == null) {
                return null;
            }
            return MechFileParser.parse(Path.of(resource.toURI()));
        } catch (Exception e) {
            log("Error loading mech: " + e.getMessage());
            return null;
        }
    }

    private String getResourcePath(String mechName) {
        if (mechName == null) {
            return null;
        }
        return mechResources.get(mechName);
    }

    private String getTeamName(TextField field, String fallback) {
        final String value = field != null ? field.getText() : null;
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value.trim();
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }
}
