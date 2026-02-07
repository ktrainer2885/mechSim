package mechsim.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import mechsim.model.Mech;
import mechsim.model.Team;
import mechsim.parser.MechFileParser;
import mechsim.ui.MechTreeBuilder;
import mechsim.ui.MechTreeNode;
import mechsim.ui.TeamListFormatter;

import java.io.File;
import java.nio.file.Path;

// JavaFX controller that wires the mech browser and team assignments.
public class MainController {

    @FXML
    private TextArea logArea;

    private final MechTreeBuilder treeBuilder = new MechTreeBuilder();

    @FXML
    private TextField team1NameField;

    @FXML
    private TextField team2NameField;
    @FXML
    private TextField mechRootField;
    @FXML
    private TreeView<MechTreeNode> mechTreeView;
    @FXML
    private ListView<String> team1AssignedList;

    private Team team1;

    private Team team2;
    @FXML
    private ListView<String> team2AssignedList;
    private Path mechRootPath;

    @FXML
    private void initialize() {
        team1 = new Team(getTeamName(team1NameField, "Team 1"));
        team2 = new Team(getTeamName(team2NameField, "Team 2"));
        refreshTeamLists();
    }

    @FXML
    private void onAddTeam1() {
        addSelectedMech(team1, team1NameField);
    }

    @FXML
    private void onAddTeam2() {
        addSelectedMech(team2, team2NameField);
    }

    @FXML
    private void onStartBattle() {
        log("Start Battle clicked.");
    }

    @FXML
    private void onBrowseMechRoot() {
        // Let the user choose a root folder that contains mech files.
        final DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Mech Root Folder");
        if (mechRootPath != null) {
            final File current = mechRootPath.toFile();
            if (current.isDirectory()) {
                chooser.setInitialDirectory(current);
            }
        }
        final File selection = chooser.showDialog(logArea.getScene().getWindow());
        if (selection == null) {
            return;
        }
        loadMechRoot(selection.toPath());
    }

    @FXML
    private void onClearTeams() {
        // Reset team assignments while keeping any typed names.
        team1 = new Team(getTeamName(team1NameField, "Team 1"));
        team2 = new Team(getTeamName(team2NameField, "Team 2"));
        refreshTeamLists();
        log("Cleared team assignments.");
    }

    private void refreshTeamLists() {
        // Update the UI lists from the current team state.
        team1AssignedList.setItems(FXCollections.observableArrayList(
                TeamListFormatter.format(team1)));
        team2AssignedList.setItems(FXCollections.observableArrayList(
                TeamListFormatter.format(team2)));
    }

    private String getTeamName(TextField field, String fallback) {
        final String value = field != null ? field.getText() : null;
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value.trim();
    }

    private void addSelectedMech(Team team, TextField nameField) {
        // Load the selected mech file and assign it to the given team.
        team.setName(getTeamName(nameField, team.getName()));
        final Path selectedPath = getSelectedMechPath();
        if (selectedPath == null) {
            log("Select a mech file before adding.");
            return;
        }
        try {
            final Mech mech = MechFileParser.parse(selectedPath);
            team.addMech(mech);
            refreshTeamLists();
            log("Loaded " + mech + " into " + team.getName() + ".");
        } catch (Exception e) {
            log("Failed to load mech: " + e.getMessage());
        }
    }

    private Path getSelectedMechPath() {
        // Only file nodes (not directories) are valid mech selections.
        if (mechTreeView == null) {
            return null;
        }
        final TreeItem<MechTreeNode> selected = mechTreeView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return null;
        }
        final MechTreeNode node = selected.getValue();
        if (node == null || node.isDirectory()) {
            return null;
        }
        return node.getPath();
    }

    public void loadMechRoot(Path root) {
        // Build the tree model from the chosen root directory.
        try {
            mechRootPath = root;
            mechRootField.setText(root.toString());
            final TreeItem<MechTreeNode> rootItem = treeBuilder.build(root);
            mechTreeView.setRoot(rootItem);
            mechTreeView.setShowRoot(false);
            rootItem.setExpanded(true);
        } catch (Exception e) {
            log("Failed to load mech root: " + e.getMessage());
        }
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }
}
