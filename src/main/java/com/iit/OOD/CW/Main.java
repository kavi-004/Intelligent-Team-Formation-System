package com.iit.OOD.CW;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Intelligent Team Formation System");

        // Step 1: Load participants
        String filePath = "E:\\Y02\\sem 1\\CM2601 Object oriented development (PROG)\\CW\\Starter pack\\participants_sample.csv";
        List<Participant> participants = FileHandler.readParticipantsFromCSV(filePath);

        System.out.println( participants.size() + " participants loaded successfully!");

        // Step 2: Take user input to classify into a team
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== Add Yourself to a Team ===");

        System.out.print("Enter your ID: ");
        String id = sc.nextLine();

        System.out.print("Enter your Name: ");
        String name = sc.nextLine();

        System.out.print("Enter your Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Preferred Game: ");
        String game = sc.nextLine();

        System.out.print("Enter Skill Level (1-10): ");
        int skill = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Preferred Role: ");
        String role = sc.nextLine();

        // --- Personality Questions ---
        System.out.println("\n=== 5-Question Personality Survey (Rate 1–5) ===");
        String[] questions = {
                "Q1: I enjoy taking the lead and guiding others during group activities.",
                "Q2: I prefer analyzing situations and coming up with strategic solutions.",
                "Q3: I work well with others and enjoy collaborative teamwork.",
                "Q4: I am calm under pressure and help maintain team morale.",
                "Q5: I like making quick decisions and adapting in dynamic situations."
        };

        int score = 0;
        for (String question : questions) {
            System.out.println(question);
            System.out.print("Your answer (1-5): ");
            score += Integer.parseInt(sc.nextLine());
        }

        int personalityScore = score * 4; // scale to 100
        String personalityType = PersonalityClassifier.classifyPersonality(personalityScore);

        Participant newUser = new Participant(id, name, email, game, skill, role, personalityScore, personalityType);
        participants.add(newUser);

        // --- Ask user for team size ---
        System.out.print("\nEnter the number of participants per team: ");
        int teamSize = Integer.parseInt(sc.nextLine());

        // Step 3: Build teams of user-defined size
        TeamBuilder builder = new TeamBuilder(participants, teamSize);
        List<Team> teams = builder.formTeams();

        // Step 4: Print all teams
        System.out.println("\n=== Formed Teams ===");
        for (Team t : teams) {
            System.out.println(t);
        }

        // Step 5: Print the team containing the new user
        System.out.println("\n=== Your Team Placement ===");
        for (Team t : teams) {
            if (t.getMembers().contains(newUser)) {
                System.out.println("You have been placed in " + t.getTeamName() + ":");
                System.out.println(t);
                break;
            }
        }

        // Step 6: Save teams to CSV inside package
        FileHandler fh = new FileHandler();
        fh.saveTeamsToCSV(teams, "src/main/java/com/iit/OOD/CW/formed_teams.csv");

        System.out.println("🏁 Team formation complete!");
    }
}
