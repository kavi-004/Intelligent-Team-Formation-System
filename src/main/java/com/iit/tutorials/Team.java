package com.iit.tutorials;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private List<Participant> members;

    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Participant> getMembers() {
        return members;
    }

    public void addMember(Participant participant) {
        members.add(participant);
    }

    public int getTeamSize() {
        return members.size();
    }

    public double getAverageSkillLevel() {
        if (members.isEmpty()) return 0.0;
        double total = 0;
        for (Participant p : members) {
            total += p.getSkillLevel();
        }
        return total / members.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Team: " + teamName + "\nMembers:\n");
        for (Participant p : members) {
            sb.append(" - ").append(p.getName()).append(" (").append(p.getRole()).append(")\n");
        }
        return sb.toString();
    }
}
