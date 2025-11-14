package com.iit.OOD.CW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // Load participants from CSV
    public List<Participant> loadParticipants(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {  // skip header
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 8) continue; // skip if missing fields

                String id = data[0];
                String name = data[1];
                String email = data[2];
                String game = data[3];
                int skillLevel = Integer.parseInt(data[4]);
                String role = data[5];
                int personalityScore = Integer.parseInt(data[6]);
                String personalityType = data[7];

                participants.add(new Participant(id, name, email, game, skillLevel, role, personalityScore, personalityType));
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid number format in CSV: " + e.getMessage());
        }

        System.out.println("✅ " + participants.size() + " participants loaded successfully!");
        return participants;
    }

    // Save formed teams to file
    public void saveTeamsToFile(List<Team> teams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Team team : teams) {
                writer.write("Team: " + team.getTeamName() + "\n");
                for (Participant p : team.getMembers()) {
                    writer.write(String.format(" - %s | %s | %s | %d | %s | %s\n",
                            p.getName(), p.getEmail(), p.getGame(), p.getSkillLevel(),
                            p.getRole(), p.getPersonalityType()));
                }
                writer.write("\n");
            }
            System.out.println("✅ Teams successfully written to " + filePath);
        } catch (IOException e) {
            System.out.println("❌ Error writing teams to file: " + e.getMessage());
        }
    }
}
