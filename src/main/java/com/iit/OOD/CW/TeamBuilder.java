package com.iit.OOD.CW;

import java.util.*;

public class TeamBuilder {

    private List<Participant> participants;
    private int teamSize;

    public TeamBuilder(List<Participant> participants, int teamSize) {
        this.participants = new ArrayList<>(participants);
        this.teamSize = teamSize;
    }

    public List<Team> formTeams() {
        List<Team> formedTeams = new ArrayList<>();
        Collections.shuffle(participants);

        int teamCounter = 1;
        Team currentTeam = new Team("Team_" + teamCounter);

        for (Participant p : participants) {
            if (p == null) continue;

            currentTeam.addMember(p);

            if (currentTeam.getMembers().size() >= teamSize) {
                formedTeams.add(currentTeam);
                teamCounter++;
                currentTeam = new Team("Team_" + teamCounter);
            }
        }

        // Add last partially filled team if any
        if (!currentTeam.getMembers().isEmpty()) {
            formedTeams.add(currentTeam);
        }

        return formedTeams;
    }
}
