package mechsim.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import mechsim.ui.controller.MainController;
import mechsim.util.TestResources;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// TestFX coverage for selecting a mech in the tree and assigning to teams.
public class TeamSelectionUiTest extends ApplicationTest {
    private MainController controller;

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        final Scene scene = new Scene(loader.load());
        controller = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testAddMechToTeam1FromTree() throws Exception {
        final Path mechPath = TestResources.mechFile("Highlander HGN-733.mtf");
        final Path root = mechPath.getParent();

        interact(() -> controller.loadMechRoot(root));
        WaitForAsyncUtils.waitForFxEvents();

        final TreeView<MechTreeNode> treeView = lookup("#mechTreeView").query();
        assertNotNull(treeView.getRoot());
        final TreeItem<MechTreeNode> mechNode = findNode(treeView.getRoot(), "Highlander HGN-733.mtf");
        assertNotNull(mechNode);

        interact(() -> treeView.getSelectionModel().select(mechNode));
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(mechNode, treeView.getSelectionModel().getSelectedItem());

        final Button addButton = lookup("Add to Team 1").queryButton();
        interact(addButton::fire);
        WaitForAsyncUtils.waitForFxEvents();

        final ListView<String> assigned = lookup("#team1AssignedList").query();
        assertEquals(1, assigned.getItems().size());
        assertEquals("1) Highlander HGN-733", assigned.getItems().get(0));
    }

    @Test
    void testAddMechToTeam2FromTree() throws Exception {
        final Path mechPath = TestResources.mechFile("Highlander HGN-733.mtf");
        final Path root = mechPath.getParent();

        interact(() -> controller.loadMechRoot(root));
        WaitForAsyncUtils.waitForFxEvents();

        final TreeView<MechTreeNode> treeView = lookup("#mechTreeView").query();
        final TreeItem<MechTreeNode> mechNode = findNode(treeView.getRoot(), "Highlander HGN-733.mtf");
        assertNotNull(mechNode);

        interact(() -> treeView.getSelectionModel().select(mechNode));
        WaitForAsyncUtils.waitForFxEvents();

        final Button addButton = lookup("Add to Team 2").queryButton();
        interact(addButton::fire);
        WaitForAsyncUtils.waitForFxEvents();

        final ListView<String> assigned = lookup("#team2AssignedList").query();
        assertEquals(1, assigned.getItems().size());
        assertEquals("1) Highlander HGN-733", assigned.getItems().get(0));
    }

    @Test
    void testClearTeamsEmptiesAssignments() throws Exception {
        final Path mechPath = TestResources.mechFile("Highlander HGN-733.mtf");
        final Path root = mechPath.getParent();

        interact(() -> controller.loadMechRoot(root));
        WaitForAsyncUtils.waitForFxEvents();

        final TreeView<MechTreeNode> treeView = lookup("#mechTreeView").query();
        final TreeItem<MechTreeNode> mechNode = findNode(treeView.getRoot(), "Highlander HGN-733.mtf");
        assertNotNull(mechNode);

        interact(() -> treeView.getSelectionModel().select(mechNode));
        WaitForAsyncUtils.waitForFxEvents();

        final Button addTeam1 = lookup("Add to Team 1").queryButton();
        final Button addTeam2 = lookup("Add to Team 2").queryButton();
        interact(addTeam1::fire);
        interact(addTeam2::fire);
        WaitForAsyncUtils.waitForFxEvents();

        final Button clearButton = lookup("Clear Teams").queryButton();
        interact(clearButton::fire);
        WaitForAsyncUtils.waitForFxEvents();

        final ListView<String> team1Assigned = lookup("#team1AssignedList").query();
        final ListView<String> team2Assigned = lookup("#team2AssignedList").query();

        assertEquals(0, team1Assigned.getItems().size());
        assertEquals(0, team2Assigned.getItems().size());
    }

    private TreeItem<MechTreeNode> findNode(TreeItem<MechTreeNode> root, String label) {
        if (root == null) {
            return null;
        }
        final MechTreeNode node = root.getValue();
        if (node != null && label.equals(node.getLabel())) {
            return root;
        }
        for (TreeItem<MechTreeNode> child : root.getChildren()) {
            final TreeItem<MechTreeNode> match = findNode(child, label);
            if (match != null) {
                return match;
            }
        }
        return null;
    }
}
