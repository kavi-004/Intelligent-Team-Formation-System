package com.iit.OOD.CW;

import java.util.Scanner;
import java.util.concurrent.*;

public class SurveyProcessor {

    private final String[] validGames = {"Chess", "CS:GO", "Valorant", "Basketball", "FIFA", "DOTA"};
    private final String[] validRoles = {"Strategist", "Attacker", "Defender", "Supporter", "Coordinator"};

    /**
     * Collect survey input from single participant with validations
     */
    public Participant collectSurveyInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Welcome to the Player Survey ---");

        String id = "";
        while (id.isBlank()) {
            System.out.print("ID: ");
            id = sc.nextLine().trim();
            if (id.isBlank()) System.out.println("⚠ ID cannot be empty!");
        }

        String name = "";
        while (name.isBlank()) {
            System.out.print("Name: ");
            name = sc.nextLine().trim();
            if (name.isBlank()) System.out.println("⚠ Name cannot be empty!");
        }

        String email = "";
        while (email.isBlank() || !isValidEmail(email)) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();
            if (email.isBlank() || !isValidEmail(email)) System.out.println("⚠ Enter a valid email!");
        }

        String game = "";
        while (game.isBlank() || !isValidGame(game)) {
            System.out.print("Preferred Game (Chess, CS:GO, Valorant, Basketball, FIFA, DOTA): ");
            game = sc.nextLine().trim();
            if (!isValidGame(game)) System.out.println("⚠ Choose a valid game from the list!");
        }

        String role = "";
        while (role.isBlank() || !isValidRole(role)) {
            System.out.print("Preferred Role (Strategist/Attacker/Defender/Supporter/Coordinator): ");
            role = sc.nextLine().trim();
            if (!isValidRole(role)) System.out.println("⚠ Choose a valid role from the list!");
        }

        int totalScore = 0;
        System.out.println("Rate 1 (Strongly Disagree) to 5 (Strongly Agree) for the following:");

        String[] questions = {
                "Q1: I enjoy taking the lead and guiding others during group activities.",
                "Q2: I prefer analyzing situations and coming up with strategic solutions.",
                "Q3: I work well with others and enjoy collaborative teamwork.",
                "Q4: I am calm under pressure and can help maintain team morale.",
                "Q5: I like making quick decisions and adapting in dynamic situations."
        };

        for (String q : questions) {
            int ans = 0;
            while (true) {
                System.out.print(q + " ");
                try {
                    ans = Integer.parseInt(sc.nextLine().trim());
                    if (ans < 1 || ans > 5) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Enter a number 1–5!");
                }
            }
            totalScore += ans;
        }

        int personalityScore = totalScore * 4;
        String personalityType = classifyPersonality(personalityScore);

        int skillLevel = 0;
        System.out.print("Skill Level (1-10): ");
        while (true) {
            try {
                skillLevel = Integer.parseInt(sc.nextLine().trim());
                if (skillLevel < 1 || skillLevel > 10) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Enter a number 1–10!");
            }
        }

        return new Participant(id, name, email, game, skillLevel, role, personalityScore, personalityType);
    }

    /**
     * Validate participant using a single thread
     */
    public Participant validateParticipant(Participant p) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Participant> future = executor.submit(() -> {
            if (p.getName() == null || p.getName().isBlank()) throw new IllegalArgumentException("Missing name.");
            if (p.getRole() == null || p.getRole().isBlank()) throw new IllegalArgumentException("Missing role.");
            if (p.getSkillLevel() < 1 || p.getSkillLevel() > 10) throw new IllegalArgumentException("Invalid skill level.");
            if (p.getPersonalityScore() < 0 || p.getPersonalityScore() > 100) throw new IllegalArgumentException("Invalid personality score.");
            return p;
        });

        Participant validated = null;
        try {
            validated = future.get();
        } catch (Exception e) {
            System.out.println("⚠ Participant validation failed: " + e.getMessage());
        }

        executor.shutdown();
        return validated;
    }

    private String classifyPersonality(int score) {
        if (score >= 90) return "Leader";
        if (score >= 70) return "Balanced";
        return "Thinker";
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidGame(String game) {
        for (String g : validGames) if (g.equalsIgnoreCase(game)) return true;
        return false;
    }

    private boolean isValidRole(String role) {
        for (String r : validRoles) if (r.equalsIgnoreCase(role)) return true;
        return false;
    }
}
