package com.iit.OOD.CW;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String csvFile = "E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\Starter pack\\participants_sample.csv"; // preloaded CSV of other participants
        String outputFile = "formed_teams.csv";

        Scanner sc = new Scanner(System.in);
        SurveyProcessor surveyProcessor = new SurveyProcessor();
        FileHandler fileHandler = new FileHandler();

        // Step 1: Collect survey input from the single participant (user)
        Participant userParticipant = surveyProcessor.collectSurveyInput();
        userParticipant = surveyProcessor.validateParticipant(userParticipant);

        if (userParticipant == null) {
            System.out.println("❌ User input invalid. Exiting.");
            return;
        }
        System.out.println("✔ User participant added.");

        // Step 2: Load other participants from CSV
        List<Participant> participants = fileHandler.readParticipantsFromCSV(csvFile);

        // Step 3: Organizer inputs team size
        System.out.print("Enter number of players per team: ");
        int teamSize = 0;
        while (true) {
            try {
                teamSize = Integer.parseInt(sc.nextLine());
                if (teamSize < 2) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Enter a valid number (>=2).");
            }
        }

        // Step 4: Form teams using TeamBuilder (pass userParticipant explicitly)
        TeamBuilder builder = new TeamBuilder(userParticipant, participants, teamSize);
        List<Team> teams = builder.formTeams();

        // Step 5: Save teams to CSV
        fileHandler.saveTeamsToCSV(teams, outputFile);

        System.out.println("✅ Teams created: " + teams.size());
        System.out.println("✅ CSV saved to: " + outputFile);
    }
}
