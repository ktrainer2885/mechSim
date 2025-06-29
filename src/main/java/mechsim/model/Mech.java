package mechsim.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Mech {
    private String chassis;
    private String model;
    private int MULid;


    private String config;
    private String techBase;
    private int Era;
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

    public Map<String, Integer> getArmorByLocation() {
        return armorByLocation;
    }

    public void setArmorByLocation(Map<String, Integer> armorByLocation) {
        this.armorByLocation = armorByLocation;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public String getHeatSinkType() {
        return heatSinkType;
    }

    public void setHeatSinkType(String heatSinkType) {
        this.heatSinkType = heatSinkType;
    }

    public int getMULid() {
        return MULid;
    }

    public void setMULid(int MULid) {
        this.MULid = MULid;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Map<String, Integer> getInternalStructureValues() {
        return internalStructureValues;
    }

    public void setInternalStructureValues(Map<String, Integer> internalStructureValues) {
        this.internalStructureValues = internalStructureValues;
    }

    public String getMynomerType() {
        return mynomerType;
    }

    public void setMynomerType(String mynomerType) {
        this.mynomerType = mynomerType;
    }

}
