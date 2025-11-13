package com.iit.OOD.CW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // Reads participant data from CSV
    public static List<Participant> readParticipantsFromCSV(String filePath) {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) { // skip header row
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 5) continue;

                String name = data[0].trim();
                String role = data[1].trim();
                int skillLevel = Integer.parseInt(data[2].trim());
                String game = data[3].trim();
                int personalityScore = Integer.parseInt(data[4].trim());

                participants.add(new Participant(name, role, skillLevel, game, personalityScore));
            }

        } catch (FileNotFoundException e) {
            System.out.println("⚠️ File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("⚠️ Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid number format in CSV: " + e.getMessage());
        }

        return participants;
    }

    // Writes teams to an output file
    public static void writeTeamsToFile(String filePath, List<Team> teams) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Team team : teams) {
                writer.write(team.toString());
                writer.write("\n-----------------------\n");
            }
            System.out.println("✅ Teams successfully written to " + filePath);
        } catch (IOException e) {
            System.out.println("⚠️ Error writing to file: " + e.getMessage());
        }
    }
}

