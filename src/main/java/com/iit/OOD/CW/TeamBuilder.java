package com.iit.OOD.CW;

import java.util.*;

public class TeamBuilder {
    private List<Participant> participants;
    private int teamSize;
    private List<Team> formedTeams = new ArrayList<>();
    private Participant user; // optional single participant

    public TeamBuilder(Participant user, List<Participant> participants, int teamSize) {
        this.user = user;
        this.participants = new ArrayList<>(participants);
        if (user != null) this.participants.add(user);
        this.teamSize = teamSize;
    }

    public List<Team> formTeams() {
        int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);
        List<Participant> copy = new ArrayList<>(participants);
        Collections.shuffle(copy);

        for (int i = 0; i < totalTeams; i++) {
            Team team = createTeam(copy, i + 1);
            formedTeams.add(team);
        }

        // show user team
        if (user != null) {
            formedTeams.stream()
                    .filter(t -> t.getMembers().contains(user))
                    .findFirst()
                    .ifPresent(t -> {
                        System.out.println("\n🎯 Your team: " + t.getTeamName());
                        t.getMembers().forEach(p ->
                                System.out.println(p.getName() + " - Role: " + p.getRole() + " - Personality: " + p.getPersonalityType())
                        );
                    });
        }
        return formedTeams;
    }

    private Team createTeam(List<Participant> copy, int teamNumber) {
        Team team = new Team("Team_" + teamNumber);
        Set<Participant> teamMembers = new HashSet<>();

        Map<String, Integer> gameCount = new HashMap<>();
        Set<String> roles = new HashSet<>();
        int leaders = 0, thinkers = 0;

        Iterator<Participant> it = copy.iterator();
        while (it.hasNext() && teamMembers.size() < teamSize) {
            Participant p = it.next();

            gameCount.putIfAbsent(p.getGame(), 0);
            if (gameCount.get(p.getGame()) >= 2) continue;
            if (roles.size() < 3 && roles.contains(p.getRole())) continue;
            if (p.getPersonalityType().equalsIgnoreCase("Leader") && leaders >= 1) continue;
            if (p.getPersonalityType().equalsIgnoreCase("Thinker") && thinkers >= 2) continue;

            teamMembers.add(p);
            gameCount.put(p.getGame(), gameCount.get(p.getGame()) + 1);
            roles.add(p.getRole());
            if (p.getPersonalityType().equalsIgnoreCase("Leader")) leaders++;
            if (p.getPersonalityType().equalsIgnoreCase("Thinker")) thinkers++;
            it.remove();
        }

        teamMembers.forEach(team::addMember);
        return team;
    }
}
