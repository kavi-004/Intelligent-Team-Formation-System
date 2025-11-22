package com.iit.OOD.CW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final String[] validGames = {"Chess", "CS:GO", "Valorant", "Basketball", "FIFA", "DOTA 2"};
    private final String[] validRoles = {"Strategist", "Attacker", "Defender", "Supporter", "Coordinator"};

    // READ PARTICIPANTS FROM CSV
    public List<Participant> readParticipantsFromCSV(String filePath) {
        List<Participant> participants = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
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
                if (data.length < 8) {
                    System.out.println("Skipped row: not enough columns -> " + line);
                    continue;
                }

                for (int i = 0; i < data.length; i++) data[i] = data[i].trim().replaceAll("\"", "");

                try {
                    String id = data[0];
                    String name = data[1];
                    String email = data[2];
                    String game = data[3].equalsIgnoreCase("DOTA") ? "DOTA 2" : data[3];

                    int skillLevel = Integer.parseInt(data[4]);
                    if (skillLevel < 1 || skillLevel > 10) continue;

                    String role = data[5];
                    if (!isValidRole(role)) continue;

                    int personalityScore = Integer.parseInt(data[6]);
                    if (personalityScore < 0 || personalityScore > 100) continue;

                    String personalityType = data[7];

                    if (!isValidGame(game)) continue;

                    participants.add(new Participant(id, name, email, game, skillLevel, role, personalityScore, personalityType));
                } catch (NumberFormatException nfe) {
                    System.out.println("Skipped row (invalid number): " + line);
                } catch (Exception e) {
                    System.out.println("Skipped row (error): " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }

        System.out.println("Participants loaded: " + participants.size());
        return participants;
    }

    // APPEND A SINGLE PARTICIPANT TO CSV
    public void appendParticipantToCSV(Participant p, String filePath) {
        // Email validation
        if (!p.getEmail().matches(".+@.+\\..+")) {
            System.out.println("Invalid email, participant not appended: " + p.getName());
            return;
        }

        File file = new File(filePath);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            if (!fileExists || file.length() == 0) {
                writer.write("ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType");
                writer.newLine();
            }

            writer.write(String.join(",",
                    safe(p.getId()),
                    safe(p.getName()),
                    safe(p.getEmail()),
                    p.getGame().equalsIgnoreCase("DOTA") ? "DOTA 2" : safe(p.getGame()),
                    String.valueOf(p.getSkillLevel()),
                    safe(p.getRole()),
                    String.valueOf(p.getPersonalityScore()),
                    safe(p.getPersonalityType())
            ));
            writer.newLine();
            writer.flush();

            System.out.println("Appended participant to CSV: " + p.getName());

        } catch (IOException e) {
            System.out.println("Error appending participant: " + e.getMessage());
        }
    }

    // SAVE TEAMS TO CSV
    public void saveTeamsToCSV(List<Team> teams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Team,ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType");
            writer.newLine();

            for (Team team : teams) {
                for (Participant p : team.getMembers()) {
                    writer.write(String.join(",",
                            team.getTeamName(),
                            safe(p.getId()),
                            safe(p.getName()),
                            safe(p.getEmail()),
                            p.getGame().equalsIgnoreCase("DOTA") ? "DOTA 2" : safe(p.getGame()),
                            String.valueOf(p.getSkillLevel()),
                            safe(p.getRole()),
                            String.valueOf(p.getPersonalityScore()),
                            safe(p.getPersonalityType())
                    ));
                    writer.newLine();
                }
            }
            writer.flush();
            System.out.println("Teams saved successfully → " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    // READ TEAMS BACK FROM CSV
    public List<Team> readTeamsFromCSV(String filePath) {
        List<Team> teams = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Team CSV not found: " + filePath);
            return teams;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean first = true;
            Team currentTeam = null;

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue; // skip header
                }

                String[] d = line.split(",", -1);
                if (d.length < 9) continue;

                String teamName = d[0];

                if (currentTeam == null || !currentTeam.getTeamName().equals(teamName)) {
                    currentTeam = new Team(teamName);
                    teams.add(currentTeam);
                }

                //  add participant to the current team
                try {
                    String id = d[1];
                    String name = d[2];
                    String email = d[3];
                    String game = d[4].equalsIgnoreCase("DOTA") ? "DOTA 2" : d[4];
                    int skillLevel = Integer.parseInt(d[5]);
                    String role = d[6];
                    int personalityScore = Integer.parseInt(d[7]);
                    String personalityType = d[8];

                    Participant p = new Participant(id, name, email, game, skillLevel, role, personalityScore, personalityType);
                    currentTeam.addMember(p);
                } catch (NumberFormatException nfe) {
                    System.out.println("Skipped team row (invalid number): " + line);
                } catch (Exception e) {
                    System.out.println("Skipped team row (error): " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading teams file: " + e.getMessage());
        }

        return teams;
}

    // Helpers
    private boolean isValidGame(String game) {
        if (game == null) return false;
        for (String g : validGames) if (g.equalsIgnoreCase(game)) return true;
        return false;
    }

    private boolean isValidRole(String role) {
        if (role == null) return false;
        for (String r : validRoles) if (r.equalsIgnoreCase(role)) return true;
        return false;
    }

    private String safe(String s) {
        return s == null ? "" : s.replaceAll("\"", "").trim();
    }
}
