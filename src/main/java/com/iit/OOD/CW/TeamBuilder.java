package com.iit.OOD.CW;

import java.util.*;
import java.util.concurrent.*;

public class TeamBuilder {
    private List<Participant> participants;
    private int teamSize;
    private List<Team> formedTeams = Collections.synchronizedList(new ArrayList<>());
    private Participant user; // the single participant user

    public TeamBuilder(Participant user, List<Participant> participants, int teamSize) {
        this.user = user;
        this.participants = new ArrayList<>(participants);
        this.participants.add(user); // add user to the list
        this.teamSize = teamSize;
    }

    public List<Team> formTeams() {
        int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(totalTeams, 4));
        List<Future<Team>> futures = new ArrayList<>();

        for (int i = 0; i < totalTeams; i++) {
            futures.add(executor.submit(this::createTeam));
        }

        for (Future<Team> f : futures) {
            try {
                Team team = f.get();
                if (team != null) formedTeams.add(team);
            } catch (Exception e) {
                System.out.println("⚠ Error forming team: " + e.getMessage());
            }
        }

        executor.shutdown();

        // Display user's team clearly
        Team userTeam = formedTeams.stream()
                .filter(t -> t.getMembers().contains(user))
                .findFirst()
                .orElse(null);

        if (userTeam != null) {
            System.out.println("\n🎯 Your team: " + userTeam.getTeamName());
            userTeam.getMembers().forEach(p ->
                    System.out.println(p.getName() + " - Role: " + p.getRole() + " - Personality: " + p.getPersonalityType()));
        } else {
            System.out.println("⚠ Could not find your team!");
        }

        return formedTeams;
    }

    private Team createTeam() {
        List<Participant> teamMembers = new ArrayList<>();
        Random rnd = new Random();

        // Shuffle participants for random selection
        List<Participant> copy = new ArrayList<>(participants);
        Collections.shuffle(copy);

        Map<String, Integer> gameCount = new HashMap<>();
        Set<String> roles = new HashSet<>();
        int leaders = 0;
        int thinkers = 0;

        for (Participant p : copy) {
            if (teamMembers.size() >= teamSize) break;

            // Game cap: max 2 players per game
            gameCount.putIfAbsent(p.getGame(), 0);
            if (gameCount.get(p.getGame()) >= 2) continue;

            // Role diversity: at least 3 different roles
            if (roles.size() < 3 && roles.contains(p.getRole())) continue;

            // Personality mix
            if (p.getPersonalityType().equalsIgnoreCase("Leader") && leaders >= 1) continue;
            if (p.getPersonalityType().equalsIgnoreCase("Thinker") && thinkers >= 2) continue;

            // Add participant
            teamMembers.add(p);
            gameCount.put(p.getGame(), gameCount.get(p.getGame()) + 1);
            roles.add(p.getRole());
            if (p.getPersonalityType().equalsIgnoreCase("Leader")) leaders++;
            if (p.getPersonalityType().equalsIgnoreCase("Thinker")) thinkers++;
        }

        // Assign team name and create team
        String teamName = "Team_" + (formedTeams.size() + 1);
        Team team = new Team(teamName);
        for (Participant p : teamMembers) {
            team.addMember(p);
        }

        return team;
    }
}
