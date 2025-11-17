package com.iit.OOD.CW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team {
    private String teamName;
    private final List<Participant> members;

    public Team(String teamName) {
        this.teamName = teamName;
        this.members = Collections.synchronizedList(new ArrayList<>());
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Participant> getMembers() {
        return members;
    }

    // Safe add method (null-check + synchronized)
    public synchronized void addMember(Participant participant) {
        if (participant == null) {
            System.err.println("⚠ Attempted to add a null participant to " + teamName);
            return;
        }
        members.add(participant);
    }

    public int getTeamSize() {
        return members.size();
    }

    public boolean isFull(int teamSize) {
        return members.size() >= teamSize;
    }

    public double getAverageSkillLevel() {
        if (members.isEmpty()) return 0.0;
        double total = 0;
        synchronized (members) {
            for (Participant p : members) {
                total += p.getSkillLevel();
            }
        }
        return total / members.size();
    }

    public double getAveragePersonalityScore() {
        if (members.isEmpty()) return 0.0;
        double total = 0;
        synchronized (members) {
            for (Participant p : members) {
                total += p.getPersonalityScore();
            }
        }
        return total / members.size();
    }

    // Helper for CSV export
    public String getSummaryRow() {
        return teamName + "," + getTeamSize() + "," +
                getAverageSkillLevel() + "," +
                getAveragePersonalityScore();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Team: " + teamName + "\nMembers:\n");
        synchronized (members) {
            for (Participant p : members) {
                sb.append(" - ").append(p.getName())
                        .append(" (Role: ").append(p.getRole())
                        .append(", Game: ").append(p.getGame())
                        .append(", Skill: ").append(p.getSkillLevel())
                        .append(", Personality: ").append(p.getPersonalityScore())
                        .append(")\n");
            }
        }
        return sb.toString();
    }
}
