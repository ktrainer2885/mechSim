package mechsim.model;

import mechsim.parser.MechFileParser;
import mechsim.util.TestResources;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamAssignmentTest {
    @Test
    void testTeamAssignmentsFromHighlander() throws Exception {
        final Path path = TestResources.mechFile("Highlander HGN-733.mtf");
        final Mech mech = MechFileParser.parse(path);

        final Team team1 = new Team("Team 1");
        final Team team2 = new Team("Team 2");

        team1.addMech(mech);
        team2.addMech(mech);

        assertEquals(1, team1.getMechs().size());
        assertEquals(1, team2.getMechs().size());
        assertEquals("Highlander HGN-733", team1.getMechs().get(0).toString());
        assertEquals("Highlander HGN-733", team2.getMechs().get(0).toString());
    }
}
