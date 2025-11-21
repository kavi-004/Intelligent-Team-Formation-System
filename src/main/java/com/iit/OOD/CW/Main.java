package com.iit.OOD.CW;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String csvFile = "E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\OOD.CW\\data\\participants_sample.csv";
        String outputFile = "E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\OOD.CW\\data\\formed_teams.csv";

        Scanner sc = new Scanner(System.in);
        SurveyProcessor surveyProcessor = new SurveyProcessor();
        FileHandler fileHandler = new FileHandler();

        while (true) {
            System.out.println("\n===== TEAM FORMATION SYSTEM =====");
            System.out.println("1. Participant");
            System.out.println("2. Organizer");
            System.out.println("3. View formed teams");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1":
                    // PARTICIPANT MODE
                    System.out.println("\n--- Participant Registration ---");
                    Participant p = surveyProcessor.collectSurveyInput();
                    p = surveyProcessor.validateParticipant(p);

                    if (p == null) {
                        System.out.println("❌ Invalid submission. Try again.");
                        break;
                    }

                    fileHandler.appendParticipantToCSV(p, csvFile);
                    System.out.println("✔ Participant successfully added!");
                    break;

                case "2":
                    // ORGANIZER MODE
                    System.out.println("\n--- Organizer Mode Activated ---");

                    // Load participants from CSV
                    List<Participant> participants = fileHandler.readParticipantsFromCSV(csvFile);
                    if (participants.isEmpty()) {
                        System.out.println("⚠ No participants loaded. Cannot form teams.");
                        break;
                    }

                    // Input team size
                    int teamSize = 0;
                    while (true) {
                        System.out.print("Enter number of players per team: ");
                        try {
                            teamSize = Integer.parseInt(sc.nextLine());
                            if (teamSize < 2) throw new NumberFormatException();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("⚠ Enter a valid number (>=2).");
                        }
                    }

                    // Build teams using TeamBuilder (pass null as user for organizer)
                    TeamBuilder builder = new TeamBuilder(null, participants, teamSize);
                    List<Team> formedTeams = builder.formTeams();

                    fileHandler.saveTeamsToCSV(formedTeams, outputFile);
                    System.out.println("✅ Teams created: " + formedTeams.size());
                    System.out.println("✅ CSV saved to: " + outputFile);
                    break;

                case "3":
                    // VIEW TEAMS
                    System.out.println("\n--- Viewing Formed Teams ---");
                    List<Team> teams = fileHandler.readTeamsFromCSV(outputFile);
                    if (teams.isEmpty()) {
                        System.out.println("⚠ No teams found!");
                    } else {
                        for (Team t : teams) {
                            System.out.println("\n" + t.getTeamName());
                            for (Participant member : t.getMembers()) {
                                System.out.println(" - " + member.getName() + " | " + member.getRole() + " | " + member.getPersonalityType());
                            }
                        }
                    }
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("⚠ Invalid choice, try again.");
            }
        }
    }
}
