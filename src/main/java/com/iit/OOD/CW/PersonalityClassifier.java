package com.iit.OOD.CW;

public class PersonalityClassifier {

    /**
     * Classifies the personality of a participant based on their score.
     * Score must be between 0 and 100.
     */
    public static String classifyPersonality(int score) {
        if (!isValidScore(score)) {
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
    }

    /**
     * Valid personality score 0–100
     */
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }

    /**
     * Convert personality category to a numerical weight
     * This helps when creating balanced 5-member groups.
     */
    public static int getPersonalityWeight(String type) {
        switch (type) {
            case "Leader": return 5;
            case "Strategist": return 4;
            case "Collaborator": return 3;
            case "Supporter": return 2;
            case "Wildcard": return 1;
            default: return 0;
        }
    }
}
