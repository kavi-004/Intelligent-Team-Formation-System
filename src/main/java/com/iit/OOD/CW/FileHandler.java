package com.iit.OOD.CW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // =========================================================
    // READ PARTICIPANTS FROM CSV (FULLY VALIDATED)
    // =========================================================
    public static List<Participant> readParticipantsFromCSV(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip header
                }

                String[] data = line.split(",");

                // MUST have all 8 fields
                if (data.length < 8) {
                    System.out.println("⚠ Skipped row: Not enough fields → " + line);
                    continue;
                }

                try {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String game = data[3].trim();
                    String skillStr = data[4].trim();
                    String role = data[5].trim();
                    String scoreStr = data[6].trim();
                    String personalityType = data[7].trim();

                    // BASIC EMPTY VALIDATION
                    if (id.isEmpty() || name.isEmpty() || email.isEmpty() || game.isEmpty() || role.isEmpty()) {
                        System.out.println("⚠ Skipped row: Missing required fields → " + line);
                        continue;
                    }

                    // NUMBER VALIDATION
                    int skillLevel;
                    int personalityScore;

                    try {
                        skillLevel = Integer.parseInt(skillStr);
                        personalityScore = Integer.parseInt(scoreStr);
                    } catch (NumberFormatException ex) {
                        System.out.println("⚠ Skipped row: Invalid number format → " + line);
                        continue;
                    }

                    // PERSONALITY SCORE VALIDATION
                    if (!PersonalityClassifier.isValidScore(personalityScore)) {
                        System.out.println("⚠ Skipped row: Invalid personality score → " + personalityScore);
                        continue;
                    }

                    // ROLE VALIDATION
                    if (!RoleValidator.isValidRole(role)) {
                        System.out.println("⚠ Skipped row: Invalid role → " + role);
                        continue;
                    }

                    // Final add
                    participants.add(new Participant(
                            id, name, email, game, skillLevel, role, personalityScore, personalityType
                    ));

                } catch (Exception e) {
                    System.out.println("⚠ Critical row error, skipping line: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading CSV file: " + e.getMessage());
        }

        System.out.println("📌 " + participants.size() + " valid participants loaded successfully!");
        return participants;
    }



    // =========================================================
    // SAVE TEAMS TO CSV
    // =========================================================
    public void saveTeamsToCSV(List<Team> teams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Team,ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType");
            writer.newLine();

            for (Team team : teams) {
                for (Participant p : team.getMembers()) {

                    writer.write(
                            team.getTeamName() + "," +
                                    p.getId() + "," +
                                    p.getName() + "," +
                                    p.getEmail() + "," +
                                    p.getGame() + "," +
                                    p.getSkillLevel() + "," +
                                    p.getRole() + "," +
                                    p.getPersonalityScore() + "," +
                                    p.getPersonalityType()
                    );
                    writer.newLine();
                }
            }

            System.out.println("✅ Formed teams CSV created successfully → " + filePath);

        } catch (IOException e) {
            System.out.println("❌ Error writing CSV: " + e.getMessage());
        }
    }
}
