package com.iit.OOD.CW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // =========================================================
    // READ PARTICIPANTS FROM CSV
    // =========================================================
    public static List<Participant> readParticipantsFromCSV(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;  // skip header row
                    continue;
                }

                String[] data = line.split(",");

                if (data.length < 8) continue;

                String id = data[0].trim();
                String name = data[1].trim();
                String email = data[2].trim();
                String game = data[3].trim();
                int skillLevel = Integer.parseInt(data[4].trim());
                String role = data[5].trim();
                int personalityScore = Integer.parseInt(data[6].trim());
                String personalityType = data[7].trim();

                participants.add(new Participant(
                        id, name, email, game, skillLevel, role, personalityScore, personalityType
                ));
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid number format in CSV: " + e.getMessage());
        }

        System.out.println("📌 " + participants.size() + " participants loaded successfully!");
        return participants;
    }



    // =========================================================
    // SAVE TEAMS TO CSV (UPDATED AS YOU REQUESTED)
    // =========================================================
    public void saveTeamsToCSV(List<Team> teams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // CSV header
            writer.write("Team,ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType");
            writer.newLine();

            // Write each participant as a row
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
