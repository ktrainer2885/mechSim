package mechsim.parser;

import mechsim.model.Mech;
import mechsim.model.Weapon;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MechFileParserTest {
    private static final String TEST_DIRECTORY = "data/mechs";

    @Test
    void testParseHighlander() throws Exception {
        String test_file = "Highlander HGN-733.mtf";
        String test_path = TEST_DIRECTORY + "/" + test_file;

        URL resource = getClass().getClassLoader().getResource(test_path);
        assertNotNull(resource, "Test MTF file not found");
        Path path = Path.of(resource.toURI());

        Mech mech = MechFileParser.parse(path);

        assertEquals("Highlander", mech.getChassis());
        assertEquals("HGN-733", mech.getModel());
        assertEquals(90, mech.getMass());
        assertEquals("Inner Sphere", mech.getTechBase());

        assertEquals(270, mech.getEngineRating());
        assertEquals(3, mech.getWalkingMP());
        assertEquals(5, mech.getRunningMP());
        assertEquals(3, mech.getJumpingMP());

        // assertEquals(80, mech.getArmorByLocation().get("LT"));
        //assertTrue(mech.getArmorByLocation().containsKey("HE"));

        assertEquals(13, mech.getHeatSinks());

        List<Weapon> weapons = mech.getWeapons();
        //assertEquals(4, weapons.size());

        //assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("LRM-20") && w.getLocation().equals("LA")));
        // assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("AC/10") && w.getLocation().equals("RA")));
    }
}
