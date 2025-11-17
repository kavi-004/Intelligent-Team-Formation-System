package com.iit.OOD.CW;

public class PersonalityClassifier {

    /**
     * Classifies the personality of a participant based on their score.
     * Score must be between 0 and 100.
     */
    public static String classifyPersonality(int score) {
        try {
            if (!isValidScore(score)) {
                System.err.println("⚠ Invalid personality score: " + score);
                return "Invalid";
            }

            if (score >= 90 && score <= 100) {
                return "Leader";
            } else if (score >= 70 && score <= 89) {
                return "Balanced";
            } else if (score >= 50 && score <= 69) {
                return "Thinker";
            } else {
                return "Unknown";
            }

        } catch (Exception e) {
            System.err.println("⚠ Error classifying personality: " + e.getMessage());
            return "Invalid";
        }
    }


    /**
     * Checks if the personality score is valid (0–100)
     */
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }


    /**
     * Converts personality type to a numerical weight.
     * Used for more refined team balancing.
     * New personality types can be added here later.
     */
    public static int getPersonalityWeight(String type) {
        if (type == null) return 0;

        return switch (type) {
            case "Leader" -> 5;
            case "Balanced" -> 4;
            case "Thinker" -> 3;
            case "Supporter" -> 2;
            case "Wildcard" -> 1;
            default -> 0; // Unknown types won't break team balancing
        };
    }

}
