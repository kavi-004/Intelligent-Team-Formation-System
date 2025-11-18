package com.iit.OOD.CW;

public class PersonalityClassifier {

    public static String classifyPersonality(int score) {
        if (!isValidScore(score)) return "Invalid";

        if (score >= 90) return "Leader";
        else if (score >= 70) return "Balanced";
        else if (score >= 50) return "Thinker";
        else return "Unknown";
    }

    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }

    public static int getPersonalityWeight(String type) {
        if (type == null) return 0;
        return switch (type) {
            case "Leader" -> 5;
            case "Balanced" -> 4;
            case "Thinker" -> 3;
            case "Supporter" -> 2;
            case "Wildcard" -> 1;
            default -> 0;
        };
    }
}
