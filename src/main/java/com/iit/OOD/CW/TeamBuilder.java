package com.iit.OOD.CW;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TeamBuilder {

    private final List<Participant> participants;
    private final int teamSize;

    public TeamBuilder(List<Participant> participants, int teamSize) {

        // Validate participant list
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Participant list cannot be empty.");
        }

        // Validate team size
        if (teamSize <= 0) {
            throw new IllegalArgumentException("Team size must be greater than zero.");
        }

        // Thread-safe structure
        this.participants = new CopyOnWriteArrayList<>(participants);
        this.teamSize = teamSize;
    }


    // ============================================================
    //  MAIN TEAM FORMATION (THREAD-SAFE + ERROR-PROTECTED)
    // ============================================================
    public List<Team> formTeams() {

        try {
            Collections.shuffle(participants);

            List<Team> teams = new ArrayList<>();

            // Separate participants by personality type
            List<Participant> leaders = new ArrayList<>();
            List<Participant> thinkers = new ArrayList<>();
            List<Participant> balanced = new ArrayList<>();

            for (Participant p : participants) {
                if (p == null) continue; // safety check

                switch (p.getPersonalityType()) {
                    case "Leader" -> leaders.add(p);
                    case "Thinker" -> thinkers.add(p);
                    default -> balanced.add(p);
                }
            }

            int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);

            for (int i = 0; i < totalTeams; i++) {
                teams.add(new Team("Team " + (i + 1)));
            }

            // Assign based on type
            assignParticipantsToTeams(leaders, teams, 1);
            assignParticipantsToTeams(thinkers, teams, 2);
            assignParticipantsToTeams(balanced, teams, teamSize);

            // Post-processing adjustments
            fixTeamsConstraints(teams);

            return teams;

        } catch (Exception e) {
            System.err.println("⚠ Error while forming teams: " + e.getMessage());
            return Collections.emptyList();
        }
    }


    // ============================================================
    // ROUND-ROBIN ASSIGNMENT WITH SAFETY CHECKS
    // ============================================================
    private void assignParticipantsToTeams(List<Participant> list, List<Team> teams, int maxPerTeam) {

        if (list == null || list.isEmpty()) return;

        int teamIndex = 0;

        for (Participant p : list) {
            if (p == null) continue;

            Team t = teams.get(teamIndex % teams.size());

            if (!t.isFull(maxPerTeam)) {
                t.addMember(p);
            } else {
                // Try finding another team with space
                for (Team nextTeam : teams) {
                    if (!nextTeam.isFull(maxPerTeam)) {
                        nextTeam.addMember(p);
                        break;
                    }
                }
            }

            teamIndex++;
        }
    }


    // POST-PROCESSING TEAM FIXES (GAME LIMIT + ROLE DIVERSITY)
    private void fixTeamsConstraints(List<Team> teams) {

        List<Participant> overflow = new ArrayList<>();

        for (Team t : teams) {

            Map<String, Integer> gameCount = new HashMap<>();

            List<Participant> toRemove = new ArrayList<>();

            // Count games
            for (Participant p : t.getMembers()) {
                gameCount.put(p.getGame(),
                        gameCount.getOrDefault(p.getGame(), 0) + 1);
            }

            // Remove extras
            for (Participant p : t.getMembers()) {
                if (gameCount.get(p.getGame()) > 2) {
                    toRemove.add(p);
                    gameCount.put(p.getGame(), gameCount.get(p.getGame()) - 1);
                }
            }

            t.getMembers().removeAll(toRemove);
            overflow.addAll(toRemove);
        }

        // Reassign overflow
        for (Participant p : overflow) {
            for (Team t : teams) {
                long count = t.getMembers().stream()
                        .filter(mem -> mem.getGame().equals(p.getGame()))
                        .count();

                if (count < 2 && t.getTeamSize() < teamSize) {
                    t.addMember(p);
                    break;
                }
            }
        }
    }
}
