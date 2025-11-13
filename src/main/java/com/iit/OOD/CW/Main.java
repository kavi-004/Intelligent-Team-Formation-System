package com.iit.OOD.CW;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 🧩 Intelligent Team Formation System ===");

        // Step 1: Load and process survey data
        SurveyProcessor processor = new SurveyProcessor();
        List<Participant> participants = processor.processSurveyData("\"E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\Starter pack\\participants_sample.csv\"");

        // Step 2: Build teams — use constructor that takes participants and team size
        TeamBuilder builder = new TeamBuilder(participants, 3);
        List<Team> teams = builder.formTeams(); // no-arg formTeams()

        // Step 3: Print teams to console
        for (Team t : teams) {
            System.out.println(t);
        }

        // Step 4: Save teams to file
        FileHandler.writeTeamsToFile("formed_teams.txt", teams);

        System.out.println("🏁 Team formation complete!");
    }
}
