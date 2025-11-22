package com.iit.OOD.CW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team {
    private final String teamName;
    private final List<Participant> members;

    public Team(String teamName) {
        this.teamName = teamName;
        this.members = Collections.synchronizedList(new ArrayList<>());
    }

    // Getters
    public String getTeamName() {
        return teamName;
    }

    public List<Participant> getMembers() {
        return members;
    }

    // Add a participant
    public void addMember(Participant p) {
        if (p != null) members.add(p);
    }

    // Check if team is full
    public boolean isFull(int teamSize) {
        return members.size() >= teamSize;
    }

    // Current team size
    public int getTeamSize() {
        return members.size();
    }

    // Display full team details
    public void displayTeamDetails() {
        System.out.println("\nTeam: " + teamName);
        for (Participant p : members) {
            System.out.println("- " + p.getName() + " | Role: " + p.getRole() + " | Game: " + p.getGame()
                    + " | Personality: " + p.getPersonalityType() + " | Skill: " + p.getSkillLevel());
        }
    }
}
