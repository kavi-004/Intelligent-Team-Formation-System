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

    public String getTeamName() {
        return teamName;
    }

    public List<Participant> getMembers() {
        return members;
    }

    public void addMember(Participant participant) {
        if (participant == null) {
            System.err.println("⚠ Tried to add null participant to " + teamName);
            return;
        }

        synchronized (members) {
            members.add(participant);
        }
    }

    public int getTeamSize() {
        synchronized (members) {
            return members.size();
        }
    }

    public boolean isFull(int teamSize) {
        synchronized (members) {
            return members.size() >= teamSize;
        }
    }

    public double getAverageSkillLevel() {
        synchronized (members) {
            if (members.isEmpty()) return 0;

            double sum = 0;
            for (Participant p : members) {
                sum += p.getSkillLevel();
            }
            return sum / members.size();
        }
    }

    public double getAveragePersonalityScore() {
        synchronized (members) {
            if (members.isEmpty()) return 0;

            double sum = 0;
            for (Participant p : members) {
                sum += p.getPersonalityScore();
            }
            return sum / members.size();
        }
    }

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
