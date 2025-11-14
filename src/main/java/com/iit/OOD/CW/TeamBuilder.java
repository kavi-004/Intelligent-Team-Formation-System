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
        List<Team> teams = new ArrayList<>();
        Collections.shuffle(participants); // randomize participants

        // Separate participants by personality type
        List<Participant> leaders = new ArrayList<>();
        List<Participant> thinkers = new ArrayList<>();
        List<Participant> balanced = new ArrayList<>();

        for (Participant p : participants) {
            switch (p.getPersonalityType()) {
                case "Leader" -> leaders.add(p);
                case "Thinker" -> thinkers.add(p);
                case "Balanced" -> balanced.add(p);
            }
        }

        int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) {
            teams.add(new Team("Team " + (i + 1)));
        }

        // Helper method to assign participants in round-robin
        assignParticipantsToTeams(leaders, teams, 1);
        assignParticipantsToTeams(thinkers, teams, 2);
        assignParticipantsToTeams(balanced, teams, teamSize); // fill remaining slots

        // Step: Enforce game cap and role diversity
        fixTeamsConstraints(teams);

        return teams;
    }

    // Assign participants round-robin
    private void assignParticipantsToTeams(List<Participant> list, List<Team> teams, int maxPerTeam) {
        int teamIndex = 0;
        for (Participant p : list) {
            Team t = teams.get(teamIndex % teams.size());
            if (t.getTeamSize() < maxPerTeam) {
                t.addMember(p);
            } else {
                // Try next team if current is full
                for (Team nextTeam : teams) {
                    if (nextTeam.getTeamSize() < maxPerTeam) {
                        nextTeam.addMember(p);
                        break;
                    }
                }
            }
            teamIndex++;
        }
    }

    // Enforce max 2 per game and at least 3 roles per team
    private void fixTeamsConstraints(List<Team> teams) {
        List<Participant> overflow = new ArrayList<>();

        for (Team t : teams) {
            Map<String, Integer> gameCount = new HashMap<>();
            Map<String, Integer> roleCount = new HashMap<>();
            for (Participant p : t.getMembers()) {
                gameCount.put(p.getGame(), gameCount.getOrDefault(p.getGame(), 0) + 1);
                roleCount.put(p.getRole(), roleCount.getOrDefault(p.getRole(), 0) + 1);
            }

            List<Participant> toRemove = new ArrayList<>();
            for (Participant p : t.getMembers()) {
                if (gameCount.get(p.getGame()) > 2) {
                    toRemove.add(p);
                    gameCount.put(p.getGame(), gameCount.get(p.getGame()) - 1);
                }
            }

            t.getMembers().removeAll(toRemove);
            overflow.addAll(toRemove);

            // Optional: add logic to ensure at least 3 different roles
            // (can be refined further if team has <3 roles)
        }

        // Reassign overflow participants to teams with space
        for (Participant p : overflow) {
            for (Team t : teams) {
                long count = t.getMembers().stream().filter(mem -> mem.getGame().equals(p.getGame())).count();
                if (count < 2 && t.getTeamSize() < teamSize) {
                    t.addMember(p);
                    break;
                }
            }
        }
    }
}
