package mechsim.parser;

import mechsim.model.Mech;
import mechsim.model.Weapon;
import mechsim.util.TestResources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for parsing .mtf files into Mech objects.
public class MechFileParserTest {
    @Test
    void testParseHighlander() throws Exception {
        final Path path = TestResources.mechFile("Highlander HGN-733.mtf");

        final Mech mech = MechFileParser.parse(path);

        // Test basic mech info
        assertEquals("Highlander", mech.getChassis());
        assertEquals("HGN-733", mech.getModel());
        assertEquals(90, mech.getMass());
        assertEquals("Inner Sphere", mech.getTechBase());
        assertEquals(1517, mech.getMulId());
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
        final List<Weapon> weapons = mech.getWeapons();
        assertEquals(5, weapons.size());

        // Check that all weapons from the MTF file are present
        assertEquals(2, weapons.stream()
                .filter(w -> w.getName().equals("Medium Laser")
                        && w.getLocation().equals("Right Torso"))
                .count());
        assertTrue(
                weapons.stream().anyMatch(w -> w.getName().equals("LRM 20")
                        && w.getLocation().equals("Left Torso"))
        );
        assertTrue(
                weapons.stream().anyMatch(w -> w.getName().equals("SRM 6")
                        && w.getLocation().equals("Left Arm"))
        );
        assertTrue(
                weapons.stream().anyMatch(w -> w.getName().equals("Autocannon/10")
                        && w.getLocation().equals("Right Arm"))
        );
    }

    @Test
    void testParseIgnoresBadNumbers(@TempDir Path tempDir) throws Exception {
        final String content = String.join("\n",
                "Chassis: Highlander",
                "Model: HGN-733",
                "Mass: not-a-number",
                "Engine: bad",
                "Walk MP: bad",
                "Jump MP: bad",
                "Heat Sinks: bad",
                "Weapons: 0");

        final Path path = tempDir.resolve("BadNumbers.mtf");
        Files.writeString(path, content);

        final Mech mech = MechFileParser.parse(path);

        assertNotNull(mech);
        assertEquals("Highlander", mech.getChassis());
        assertEquals("HGN-733", mech.getModel());
    }

    @Test
    void testParseWithMissingOptionalSections(@TempDir Path tempDir) throws Exception {
        final String content = String.join("\n",
                "Chassis: TestMech",
                "Model: TST-1",
                "Mass: 35",
                "Engine: 210 Fusion Engine",
                "Walk MP: 6",
                "Jump MP: 3");

        final Path path = tempDir.resolve("MissingSections.mtf");
        Files.writeString(path, content);

        final Mech mech = MechFileParser.parse(path);

        assertNotNull(mech);
        assertEquals("TestMech", mech.getChassis());
        assertEquals("TST-1", mech.getModel());
        assertEquals(0, mech.getWeapons().size());
        assertEquals(0, mech.getQuirks().size());
    }
}
