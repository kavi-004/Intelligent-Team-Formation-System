package com.iit.OOD.CW;

public class PersonalityClassifier {

    // Method to classify personality based on score
    public static String classifyPersonality(int totalScore) {
        // Scale score to 100 (since 5 questions × 5 max points = 25 → scale ×4)
        int scaledScore = totalScore * 4;

        if (scaledScore >= 90 && scaledScore <= 100) {
            return "Leader";
        } else if (scaledScore >= 70 && scaledScore <= 89) {
            return "Balanced";
        } else if (scaledScore >= 50 && scaledScore <= 69) {
            return "Thinker";
        } else {
            return "Invalid Score";
        }
    }
}
