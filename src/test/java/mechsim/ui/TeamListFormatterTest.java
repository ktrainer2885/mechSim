package mechsim.ui;

import mechsim.model.Mech;
import mechsim.model.Team;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamListFormatterTest {
    @Test
    void testDuplicateMechsAreNumbered() {
        final Mech mech = new Mech();
        mech.setChassis("Highlander");
        mech.setModel("HGN-733");

        final Team team = new Team("Team 1");
        team.addMech(mech);
        team.addMech(mech);

        final List<String> entries = TeamListFormatter.format(team);

        assertEquals(2, entries.size());
        assertEquals("1) Highlander HGN-733", entries.get(0));
        assertEquals("2) Highlander HGN-733", entries.get(1));
    }
}
