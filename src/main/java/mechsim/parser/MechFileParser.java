package mechsim.parser;

import mechsim.model.Mech;
import mechsim.model.Weapon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Parser for BattleTech MTF (Mech Type Format) files.
 * This parser reads .mtf files and converts them into Mech objects.
 */
public class MechFileParser {
    // Constants for parsing
    private static final String SECTION_SEPARATOR = ":";
    private static final String EMPTY_SLOT = "-Empty-";

    // We may add weapon standardization in a separate component later

    /**
     * Parses an MTF file into a Mech object
     *
     * @param path Path to the MTF file
     * @return Fully populated Mech object
     * @throws IOException If file cannot be read
     */
    public static Mech parse(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        Mech mech = new Mech();

        // Track the current section being parsed
        String currentSection = null;

        // First pass to parse basic info, armor, and movement
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            // Skip empty lines and comments
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            // Handle section headers (e.g., "Left Arm:")
            if (line.endsWith(":") && !line.startsWith("Armor:")) {
                currentSection = line.substring(0, line.length() - 1).trim();
                continue;
            }

            // Parse key-value pairs
            if (line.contains(SECTION_SEPARATOR)) {
                String[] parts = line.split(SECTION_SEPARATOR, 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    parseKeyValuePair(mech, key, value, currentSection);
                }
            }
        }

        // Second pass to specifically find the weapons section and parse weapons
        parseWeaponsSection(lines, mech);

        return mech;
    }

    /**
     * Parses a key-value pair from the MTF file
     */
    private static void parseKeyValuePair(Mech mech, String key, String value, String currentSection) {
        try {
            String keyLower = key.toLowerCase();

            // Handling based on the key
            switch (keyLower) {
                // Basic mech info
                case "chassis" -> mech.setChassis(value);
                case "model" -> mech.setModel(value);
                case "mul id" -> mech.setMULid(Integer.parseInt(value));
                case "config" -> mech.setConfig(value);
                case "techbase" -> mech.setTechBase(value);
                case "era" -> mech.setEra(Integer.parseInt(value));
                case "source" -> mech.setSource(value);
                case "rules level" -> mech.setRules(value);
                case "role" -> mech.setRole(value);
                case "mass" -> mech.setMass(Integer.parseInt(value));

                // Structure and myomer
                case "structure" -> mech.setInternalStructure(value);
                case "myomer" -> mech.setMynomerType(value);

                // Engine
                case "engine" -> parseEngine(mech, value);

                // Movement
                case "walk mp" -> {
                    int walkMP = Integer.parseInt(value);
                    mech.setWalkingMP(walkMP);
                    mech.setRunningMP((int) Math.ceil(walkMP * 1.5));
                }
                case "jump mp" -> mech.setJumpingMP(Integer.parseInt(value));

                // Heat sinks
                case "heat sinks" -> parseHeatSinks(mech, value);

                // Armor
                case "armor" -> mech.setArmorType(value);

                // Quirks
                case "quirk" -> mech.getQuirks().add(value);

                // Weapons section indicator
                case "weapons" -> {
                    // This just indicates the weapons count, actual weapons are parsed differently
                }

                // Default case handles special entries
                default -> handleSpecialEntries(mech, key, value, keyLower, currentSection);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing value for " + key + ": " + value);
        }
    }

    /**
     * Handles special entries like armor values
     */
    private static void handleSpecialEntries(Mech mech, String key, String value, String keyLower, String currentSection) {
        // Handle armor values (e.g., "LA Armor:30")
        if (key.endsWith("Armor") && key.length() > 6) {
            String location = key.substring(0, key.length() - 6).toUpperCase();
            try {
                int armorValue = Integer.parseInt(value);
                mech.getArmorByLocation().put(location, armorValue);
            } catch (NumberFormatException e) {
                System.err.println("Bad armor value: " + key + SECTION_SEPARATOR + value);
            }
        }
        // Other special entries could be handled here
    }

    /**
     * Parses engine information
     */
    private static void parseEngine(Mech mech, String value) {
        String[] parts = value.split(" ", 2);
        try {
            mech.setEngineRating(Integer.parseInt(parts[0]));
            if (parts.length > 1) {
                mech.setEngineType(parts[1]);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid engine rating: " + parts[0]);
        }
    }

    /**
     * Parses heat sink information
     */
    private static void parseHeatSinks(Mech mech, String value) {
        String[] parts = value.split(" ", 2);
        try {
            mech.setHeatSinks(Integer.parseInt(parts[0]));
            if (parts.length > 1) {
                mech.setHeatSinkType(parts[1]);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid heat sink count: " + parts[0]);
        }
    }


    /**
     * Specifically parses the Weapons section of the MTF file
     */
    private static void parseWeaponsSection(List<String> lines, Mech mech) {
        boolean inWeaponsSection = false;
        boolean weaponSectionFound = false;
        int weaponCount = 0;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            // Check for the Weapons section header and count
            if (line.startsWith("Weapons:")) {
                inWeaponsSection = true;
                weaponSectionFound = true;
                try {
                    weaponCount = Integer.parseInt(line.substring(8).trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid weapon count: " + line);
                }
                continue;
            }

            // Process weapons list until we hit a section header
            if (inWeaponsSection) {
                if (line.endsWith(":")) {
                    // We've hit the next section header, exit weapons section
                    inWeaponsSection = false;
                    continue;
                }

                // Parse weapon line (format: "Weapon Name, Location")
                if (line.contains(",")) {
                    String[] weaponParts = line.split(",", 2);
                    if (weaponParts.length == 2) {
                        String weaponName = weaponParts[0].trim();
                        String location = weaponParts[1].trim();
                        mech.getWeapons().add(new Weapon(weaponName, location));
                    }
                }
            }
        }

        // Validate if we found the expected number of weapons
        if (weaponSectionFound && mech.getWeapons().size() != weaponCount) {
            System.err.println("Warning: Found " + mech.getWeapons().size() +
                    " weapons but expected " + weaponCount);
        }
    }

    // Weapon standardization removed as it's better handled in a separate component
}