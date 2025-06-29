package mechsim.parser;

import mechsim.model.Mech;
import mechsim.model.Weapon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MechFileParser {
    public static Mech parse(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        Mech mech = new Mech();


        for (String rawLine : lines) {
            String currentMode = "";
            String line = rawLine.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            String[] parts = line.split(":", 2);
            if (parts.length < 2) continue;

            String key = parts[0].trim().toLowerCase();
            String value = parts[1].trim();

            switch (key) {
                case "chassis" -> mech.setChassis(value);
                case "model" -> mech.setModel(value);
                case "mass" -> mech.setMass(Integer.parseInt(value));
                case "techbase" -> mech.setTechBase(value);
                case "engine" -> {
                    String[] tokens = value.split(" ");
                    mech.setEngineRating(Integer.parseInt(tokens[0]));
                    mech.setEngineType(tokens[1]);
                }
                case "walk mp" -> {
                    mech.setWalkingMP(Integer.parseInt(value));
                    mech.setRunningMP((int) Math.ceil(Integer.parseInt(value) * 1.5));
                }
                case "jump mp" -> mech.setJumpingMP(Integer.parseInt(value));
                case "heat sinks" -> {
                    String[] tokens = value.split(" ", 2);
                    mech.setHeatSinks(Integer.parseInt(tokens[0]));
                    mech.setHeatSinkType(tokens[1]);
                }
                case "structure" -> mech.setInternalStructure(value);
                case "armor" -> {
                    mech.setArmorType(value);  // e.g., Standard(Inner Sphere)
                    // Prepare to read lines like "Location Armor: value"
                    currentMode = "armorBlock";
                }
                case "weapon" -> {
                    int lastSpace = value.lastIndexOf(' ');
                    if (lastSpace > 0) {
                        String weaponName = value.substring(0, lastSpace).trim();
                        String location = value.substring(lastSpace + 1).trim();
                        mech.getWeapons().add(new Weapon(weaponName, location));
                    } else {
                        mech.getWeapons().add(new Weapon(value, ""));
                    }
                }
            }
            if ("armorBlock".equals(currentMode) && line.contains(" Armor:")) {
                String[] partsArmor = line.split(" Armor:");
                if (partsArmor.length == 2) {
                    String location = partsArmor[0].trim();
                    try {
                        int armorValue = Integer.parseInt(partsArmor[1].trim());
                        mech.getArmorByLocation().put(location, armorValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Bad armor value: " + line);
                    }
                }
            }

        }

        return mech;
    }

    private static List<Integer> parseIntList(String value) {
        List<Integer> result = new ArrayList<>();
        for (String part : value.trim().split("\\s+")) {
            result.add(Integer.parseInt(part));
        }
        return result;
    }
}
