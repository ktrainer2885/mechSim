package mechsim.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final List<Mech> mechs = new ArrayList<>();
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMech(Mech mech) {
        mechs.add(mech);
    }

    public List<Mech> getMechs() {
        return new ArrayList<>(mechs);
    }
}
