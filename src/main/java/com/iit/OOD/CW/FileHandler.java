package com.iit.OOD.CW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // -------------------------------------------
    // READ PARTICIPANTS FROM CSV
    // -------------------------------------------
    public List<Participant> readParticipantsFromCSV(String filePath) {
        List<Participant> participants = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("❌ File not found: " + filePath);
            return participants;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (firstLine) {
                    firstLine = false; // skip header
                    continue;
                }

                String[] data = line.split(",", -1);
                if (data.length < 8) continue;

                for (int i = 0; i < data.length; i++)
                    data[i] = data[i].trim().replaceAll("\"", "");

                try {
                    String id = data[0];
                    String name = data[1];
                    String email = data[2];
                    String game = data[3];
                    int skillLevel = Integer.parseInt(data[4]);
                    String role = data[5];
                    int personalityScore = Integer.parseInt(data[6]);
                    String personalityType = data[7];

                    participants.add(new Participant(id, name, email, game, skillLevel, role, personalityScore, personalityType));
                } catch (Exception e) {
                    System.out.println("⚠ Skipped row: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading CSV: " + e.getMessage());
        }

        System.out.println("📌 Participants loaded: " + participants.size());
        return participants;
    }

    // -------------------------------------------
    // APPEND A SINGLE PARTICIPANT TO CSV
    // -------------------------------------------
    public void appendParticipantToCSV(Participant p, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.join(",",
                    p.getId(),
                    p.getName(),
                    p.getEmail(),
                    p.getGame(),
                    String.valueOf(p.getSkillLevel()),
                    p.getRole(),
                    String.valueOf(p.getPersonalityScore()),
                    p.getPersonalityType()
            ));
            writer.newLine();
            System.out.println("➕ Appended participant: " + p.getName());
        } catch (IOException e) {
            System.out.println("❌ Error appending participant: " + e.getMessage());
        }
    }

    // -------------------------------------------
    // SAVE TEAMS TO CSV
    // -------------------------------------------
    public void saveTeamsToCSV(List<Team> teams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Team,ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType");
            writer.newLine();

            for (Team team : teams) {
                for (Participant p : team.getMembers()) {
                    writer.write(String.join(",",
                            team.getTeamName(),
                            p.getId(),
                            p.getName(),
                            p.getEmail(),
                            p.getGame(),
                            String.valueOf(p.getSkillLevel()),
                            p.getRole(),
                            String.valueOf(p.getPersonalityScore()),
                            p.getPersonalityType()
                    ));
                    writer.newLine();
                }
            }
            System.out.println("✅ Teams saved successfully → " + filePath);
        } catch (IOException e) {
            System.out.println("❌ Error writing CSV: " + e.getMessage());
        }
    }

    // -------------------------------------------
    // READ TEAMS BACK FROM CSV
    // -------------------------------------------
    public List<Team> readTeamsFromCSV(String filePath) {
        List<Team> teams = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("⚠ Team CSV not found: " + filePath);
            return teams;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            Team currentTeam = null;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }

                String[] d = line.split(",", -1);
                if (d.length < 9) continue;

                String teamName = d[0];
                if (currentTeam == null || !currentTeam.getTeamName().equals(teamName)) {
                    currentTeam = new Team(teamName);
                    teams.add(currentTeam);
                }

                Participant p = new Participant(
                        d[1], d[2], d[3], d[4],
                        Integer.parseInt(d[5]),
                        d[6],
                        Integer.parseInt(d[7]),
                        d[8]
                );

                currentTeam.addMember(p);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading teams CSV: " + e.getMessage());
        }

        return teams;
    }
}
