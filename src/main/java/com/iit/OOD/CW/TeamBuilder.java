package com.iit.OOD.CW;

import java.util.*;

public class TeamBuilder {

    private List<Participant> participants;
    private List<Team> formedTeams;
    private int teamSize;

    public TeamBuilder(List<Participant> participants, int teamSize) {
        this.participants = participants;
        this.teamSize = teamSize;
        this.formedTeams = new ArrayList<>();
    }

    // 🧠 Form balanced teams based on skill + personality
    public List<Team> formTeams() {
        if (participants == null || participants.isEmpty()) {
            System.out.println("No participants available!");
            return formedTeams;
        }

        // Sort participants by skill level (descending)
        participants.sort(Comparator.comparingInt(Participant::getSkillLevel).reversed());

        int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);

        // Initialize empty teams
        for (int i = 0; i < totalTeams; i++) {
            formedTeams.add(new Team("Team " + (i + 1)));
        }

        // 🌀 Distribute participants in a round-robin pattern to balance skills
        int index = 0;
        for (Participant p : participants) {
            formedTeams.get(index).addMember(p);
            index = (index + 1) % totalTeams;
        }

        return formedTeams;
    }

    // 📊 Display teams neatly
    public void displayTeams() {
        for (Team team : formedTeams) {
            System.out.println("\n" + team.getTeamName() + ":");
            int totalSkill = 0;
            int totalPersonality = 0;

            for (Participant p : team.getMembers()) {
                System.out.println(" - " + p.getName() + " | Role: " + p.getRole() +
                        " | Game: " + p.getGame() +
                        " | Skill: " + p.getSkillLevel() +
                        " | Personality: " + p.getPersonalityScore());
                totalSkill += p.getSkillLevel();
                totalPersonality += p.getPersonalityScore();
            }

            System.out.println("   → Avg Skill: " + (totalSkill / team.getMembers().size()));
            System.out.println("   → Avg Personality: " + (totalPersonality / team.getMembers().size()));
        }
    }
}
