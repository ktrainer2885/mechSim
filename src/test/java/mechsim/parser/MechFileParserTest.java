package mechsim.parser;

import mechsim.model.Mech;
import mechsim.model.Weapon;
import mechsim.util.util.TestResources;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MechFileParserTest {
    @Test
    void testParseHighlander() throws Exception {
        Path path = TestResources.mechFile("Highlander HGN-733.mtf");

        Mech mech = MechFileParser.parse(path);

        // Test basic mech info
        assertEquals("Highlander", mech.getChassis());
        assertEquals("HGN-733", mech.getModel());
        assertEquals(90, mech.getMass());
        assertEquals("Inner Sphere", mech.getTechBase());
        assertEquals(1517, mech.getMULid());
        assertEquals("Biped", mech.getConfig());
        assertEquals(2866, mech.getEra());
        assertEquals("1", mech.getRules());
        assertEquals("Juggernaut", mech.getRole());

        // Test engine and movement
        assertEquals(270, mech.getEngineRating());
        assertEquals("Fusion Engine", mech.getEngineType());
        assertEquals(3, mech.getWalkingMP());
        assertEquals(5, mech.getRunningMP());
        assertEquals(3, mech.getJumpingMP());

        // Test armor
        assertEquals("Standard(Inner Sphere)", mech.getArmorType());
        assertEquals(28, mech.getArmorByLocation().get("LT"));
        assertEquals(30, mech.getArmorByLocation().get("LA"));
        assertEquals(41, mech.getArmorByLocation().get("CT"));
        assertEquals(9, mech.getArmorByLocation().get("HD"));
        assertEquals(38, mech.getArmorByLocation().get("LL"));

        // Test heat sinks
        assertEquals(13, mech.getHeatSinks());
        assertEquals("Single", mech.getHeatSinkType());

        // Test quirks
        assertEquals(4, mech.getQuirks().size());
        assertTrue(mech.getQuirks().contains("command_mech"));
        assertTrue(mech.getQuirks().contains("difficult_eject"));

        // Test weapons
        List<Weapon> weapons = mech.getWeapons();
        assertEquals(5, weapons.size());

        // Check that all weapons from the MTF file are present
        assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("Medium Laser") && w.getLocation().equals("Right Torso")));
        assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("Medium Laser") && w.getLocation().equals("Right Torso")));
        assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("LRM 20") && w.getLocation().equals("Left Torso")));
        assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("SRM 6") && w.getLocation().equals("Left Arm")));
        assertTrue(weapons.stream().anyMatch(w -> w.getName().equals("Autocannon/10") && w.getLocation().equals("Right Arm")));
    }
}
