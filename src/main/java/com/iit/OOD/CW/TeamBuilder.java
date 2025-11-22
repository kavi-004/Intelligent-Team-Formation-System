package com.iit.OOD.CW;

import java.util.*;
import java.util.concurrent.*;

public class TeamBuilder {
    private List<Participant> participants;
    private int teamSize;
    private List<Team> formedTeams = Collections.synchronizedList(new ArrayList<>());
    private Participant user; // the single participant user
    // Lock object for synchronized selection
    private final Object selectionLock = new Object();

    public TeamBuilder(Participant user, List<Participant> participants, int teamSize) {
        this.user = user;
        this.participants = new ArrayList<>(participants);
        if (user != null) this.participants.add(user); // add user to the list only if present
        this.teamSize = teamSize;
    }

    public List<Team> formTeams() {
        if (participants == null || participants.isEmpty()) {
            System.out.println("No participants to form teams.");
            return Collections.emptyList();
        }

        int totalTeams = (int) Math.ceil((double) participants.size() / teamSize);

        // Use a fixed pool to create teams in parallel (selection is synchronized)
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(totalTeams, 4));
        List<Future<Team>> futures = new ArrayList<>();

        for (int i = 0; i < totalTeams; i++) {
            final int teamNumber = i + 1;
            futures.add(executor.submit(() -> {
                List<Participant> members = selectMembersForTeam();
                Team team = new Team("Team_" + teamNumber);
                for (Participant p : members) team.addMember(p);
                return team;
            }));
        }

        for (Future<Team> f : futures) {
            try {
                Team team = f.get();
                if (team != null) formedTeams.add(team);
            } catch (Exception e) {
                System.out.println("Error forming team: " + e.getMessage());
            }
        }

        executor.shutdown();

        // Display user's team clearly
        if (user != null) {
            Team userTeam = formedTeams.stream()
                    .filter(t -> t.getMembers().contains(user))
                    .findFirst()
                    .orElse(null);

            if (userTeam != null) {
                System.out.println("\n Your team: " + userTeam.getTeamName());
                userTeam.getMembers().forEach(p ->
                        System.out.println(p.getName() + " - Role: " + p.getRole() + " - Personality: " + p.getPersonalityType()));
            } else {
                System.out.println("Could not find your team!");
            }
        }

        return formedTeams;
    }

    /**
     * Synchronized selection of members for one team.
     * scan the shared participants list and remove selected members so multiple threads don't pick same person.
     */
    private List<Participant> selectMembersForTeam() {
        synchronized (selectionLock) {
            List<Participant> chosen = new ArrayList<>();
            Map<String, Integer> gameCount = new HashMap<>();
            Set<String> roles = new HashSet<>();
            int leaders = 0;
            int thinkers = 0;

            Iterator<Participant> it = participants.iterator();
            while (it.hasNext() && chosen.size() < teamSize) {
                Participant p = it.next();
                if (p == null) {
                    it.remove();
                    continue;
                }

                // Game cap: max 2 players per game per team
                String g = p.getGame();
                gameCount.putIfAbsent(g, 0);
                if (gameCount.get(g) >= 2) continue;

                // Role diversity: encourage at least 3 roles
                if (roles.size() < 3 && roles.contains(p.getRole())) continue;

                // Personality mix caps
                String t = p.getPersonalityType();
                if (t != null && t.equalsIgnoreCase("Leader") && leaders >= 1) continue;
                if (t != null && t.equalsIgnoreCase("Thinker") && thinkers >= 2) continue;

                // Accept participant
                chosen.add(p);
                gameCount.put(g, gameCount.get(g) + 1);
                roles.add(p.getRole());
                if (t != null && t.equalsIgnoreCase("Leader")) leaders++;
                if (t != null && t.equalsIgnoreCase("Thinker")) thinkers++;

                // remove from pool so others won't pick the same participant
                it.remove();
            }

            // If team is still short , fill greedily with remaining participants
            if (chosen.size() < teamSize) {
                Iterator<Participant> it2 = participants.iterator();
                while (it2.hasNext() && chosen.size() < teamSize) {
                    Participant p2 = it2.next();
                    if (p2 == null) {
                        it2.remove();
                        continue;
                    }
                    chosen.add(p2);
                    it2.remove();
                }
            }

            return chosen;
        }
    }
}
