package com.iit.OOD.CW;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String inputFile = "participants.csv";      // your sample CSV
        String outputFile = "formed_teams.csv";     // final output
        int teamSize = 5;                           // required team size

        SurveyProcessor processor = new SurveyProcessor();
        FileHandler fileHandler = new FileHandler();

        final List<Participant>[] participantsHolder = new List[1];
        final List<Team>[] teamsHolder = new List[1];

        // =========================================================
        // THREAD 1 → PROCESS SURVEY (READ CSV)
        // =========================================================
        Thread surveyThread = new Thread(() -> {
            try {
                System.out.println("🔄 Loading & validating participants...");
                participantsHolder[0] = processor.processSurveyData(inputFile);
            } catch (Exception e) {
                System.out.println("❌ Error in survey processing thread: " + e.getMessage());
            }
        });


        // =========================================================
        // THREAD 2 → FORM TEAMS (AFTER SURVEY THREAD ENDS)
        // =========================================================
        Thread teamThread = new Thread(() -> {
            try {
                // Wait for survey data to finish loading
                while (participantsHolder[0] == null) {
                    Thread.sleep(50);
                }

                List<Participant> participants = participantsHolder[0];

                System.out.println("👥 Forming teams with " + participants.size() + " participants...");

                TeamBuilder builder = new TeamBuilder(participants, teamSize);
                teamsHolder[0] = builder.formTeams();

            } catch (Exception e) {
                System.out.println("❌ Error in team formation thread: " + e.getMessage());
            }
        });


        // Start BOTH threads
        surveyThread.start();
        teamThread.start();

        // =========================================================
        // WAIT FOR THREADS TO FINISH
        // =========================================================
        try {
            surveyThread.join();
            teamThread.join();
        } catch (InterruptedException e) {
            System.out.println("⚠ Thread interrupted: " + e.getMessage());
        }

        // =========================================================
        // SAVE FINAL TEAM CSV
        // =========================================================
        if (teamsHolder[0] != null) {
            fileHandler.saveTeamsToCSV(teamsHolder[0], outputFile);
            System.out.println("🎉 All tasks completed successfully!");
        } else {
            System.out.println("❌ Teams could not be generated.");
        }
    }
}
