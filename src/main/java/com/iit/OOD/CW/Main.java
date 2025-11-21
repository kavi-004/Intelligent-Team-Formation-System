package com.iit.OOD.CW;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String csvFile = "E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\Starter pack\\participants_sample.csv";
        String outputFile = "E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\OOD.CW\\src\\main\\java\\com\\iit\\OOD\\CW\\formed_teams.csv";


        Scanner sc = new Scanner(System.in);
        SurveyProcessor surveyProcessor = new SurveyProcessor();
        FileHandler fileHandler = new FileHandler();

        while (true) {
            System.out.println("\n===== TEAM FORMATION SYSTEM =====");
            System.out.println("1. I am a participant and want to be added to a team");
            System.out.println("2. I want to view already formed teams");
            System.out.println("3. I am the organizer and want to enter team size and form teams");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
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
                    System.out.println("\n--- Viewing Formed Teams ---");
                    List<Team> teams = fileHandler.readTeamsFromCSV(outputFile);
                    if (teams.isEmpty()) {
                        System.out.println("⚠ No teams found.");
                    } else {
                        for (Team t : teams) t.displayTeamDetails();
                    }
                    break;

                case "3":
                    System.out.println("\n--- Organizer Mode Activated ---");

                    List<Participant> participants = fileHandler.readParticipantsFromCSV(csvFile);

                    int teamSize = 0;
                    while (true) {
                        System.out.print("Enter number of participants per team: ");
                        try {
                            teamSize = Integer.parseInt(sc.nextLine().trim());
                            if (teamSize < 2) throw new NumberFormatException();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("⚠ Enter a valid number (>=2).");
                        }
                    }

                    TeamBuilder builder = new TeamBuilder(participants, teamSize);
                    List<Team> formedTeams = builder.formTeams();

                    fileHandler.saveTeamsToCSV(formedTeams, outputFile);

                    System.out.println("✅ Teams created: " + formedTeams.size());
                    System.out.println("✅ CSV saved to: " + outputFile);
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("⚠ Invalid choice, try again.");
            }
        }
    }
}
