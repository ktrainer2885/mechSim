package mechsim.ui;

import mechsim.model.Mech;
import mechsim.model.Team;

import java.util.ArrayList;
import java.util.List;

// Formats a team's mech list with stable numbering for duplicates.
public final class TeamListFormatter {
    private TeamListFormatter() {
    }

    // Prefix each mech with an index so duplicates are distinguishable.
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
