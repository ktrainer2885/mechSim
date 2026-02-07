package mechsim.ui;

import mechsim.model.Mech;
import mechsim.model.Team;

import java.util.ArrayList;
import java.util.List;

public final class TeamListFormatter {
    private TeamListFormatter() {
    }

    public static List<String> format(Team team) {
        final List<String> entries = new ArrayList<>();
        int index = 1;
        for (Mech mech : team.getMechs()) {
            entries.add(index + ") " + mech);
            index++;
        }
        return entries;
    }
}
