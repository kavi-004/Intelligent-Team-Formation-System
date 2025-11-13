package com.iit.OOD.CW;

public class PersonalityClassifier {

    public static String classifyPersonality(int score) {
        if (score >= 90 && score <= 100) {
            return "Leader";
        } else if (score >= 70 && score <= 89) {
            return "Balanced";
        } else if (score >= 50 && score <= 69) {
            return "Thinker";
        } else {
            return "Unknown"; // out of valid range
        }
    }

    // Optional: validate score range
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }
}
