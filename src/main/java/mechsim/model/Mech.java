package mechsim.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Data model for a BattleMech parsed from a .mtf file.
public class Mech {
    private String chassis;

    private String model;

    private int mulId;

    private String config;

    private String techBase;

    private int era;

    private String source;

    private String rules;

    private String role;

    private List<String> quirks = new ArrayList<>();

    private int mass;

    private int engineRating;

    private String engineType;

    private String internalStructure;

    private Map<String, Integer> internalStructureValues = new LinkedHashMap<>();

    private String mynomerType;

    private String heatSinkType;

    private int heatSinks;

    private int walkingMP;

    private int runningMP;

    private int jumpingMP;

    private String armorType;

    private Map<String, Integer> armorByLocation = new LinkedHashMap<>();

    private List<Weapon> weapons = new ArrayList<>();

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getTechBase() {
        return techBase;
    }

    public void setTechBase(String techBase) {
        this.techBase = techBase;
    }

    public int getEngineRating() {
        return engineRating;
    }

    public void setEngineRating(int engineRating) {
        this.engineRating = engineRating;
    }

    public int getWalkingMP() {
        return walkingMP;
    }

    public void setWalkingMP(int walkingMP) {
        this.walkingMP = walkingMP;
    }

    public int getRunningMP() {
        return runningMP;
    }

    public void setRunningMP(int runningMP) {
        this.runningMP = runningMP;
    }

    public int getJumpingMP() {
        return jumpingMP;
    }

    public void setJumpingMP(int jumpingMP) {
        this.jumpingMP = jumpingMP;
    }

    public int getHeatSinks() {
        return heatSinks;
    }

    public void setHeatSinks(int heatSinks) {
        this.heatSinks = heatSinks;
    }

    public String getArmorType() {
        return armorType;
    }

    public void setArmorType(String armorType) {
        this.armorType = armorType;
    }

    public String getInternalStructure() {
        return internalStructure;
    }

    public void setInternalStructure(String internalStructure) {
        this.internalStructure = internalStructure;
    }

    // Return a copy to prevent external mutation.
    public Map<String, Integer> getArmorByLocation() {
        return new LinkedHashMap<>(armorByLocation);
    }

    // Store a copy to keep internal state isolated.
    public void setArmorByLocation(Map<String, Integer> armorByLocation) {
        this.armorByLocation = new LinkedHashMap<>(armorByLocation);
    }

    public void putArmorByLocation(String location, int value) {
        this.armorByLocation.put(location, value);
    }

    // Return a copy to prevent external mutation.
    public List<Weapon> getWeapons() {
        return new ArrayList<>(weapons);
    }

    // Store a copy to keep internal state isolated.
    public void setWeapons(List<Weapon> weapons) {
        this.weapons = new ArrayList<>(weapons);
    }

    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    public String getHeatSinkType() {
        return heatSinkType;
    }

    public void setHeatSinkType(String heatSinkType) {
        this.heatSinkType = heatSinkType;
    }

    public int getMulId() {
        return mulId;
    }

    public void setMulId(int mulId) {
        this.mulId = mulId;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Map<String, Integer> getInternalStructureValues() {
        return new LinkedHashMap<>(internalStructureValues);
    }

    public void setInternalStructureValues(Map<String, Integer> internalStructureValues) {
        this.internalStructureValues = new LinkedHashMap<>(internalStructureValues);
    }

    public String getMynomerType() {
        return mynomerType;
    }

    public void setMynomerType(String mynomerType) {
        this.mynomerType = mynomerType;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public int getEra() {
        return era;
    }

    public void setEra(int era) {
        this.era = era;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Return a copy to prevent external mutation.
    public List<String> getQuirks() {
        return new ArrayList<>(quirks);
    }

    // Store a copy to keep internal state isolated.
    public void setQuirks(List<String> quirks) {
        this.quirks = new ArrayList<>(quirks);
    }

    public void addQuirk(String quirk) {
        this.quirks.add(quirk);
    }

    @Override
    public String toString() {
        return chassis + " " + model;
    }
}
