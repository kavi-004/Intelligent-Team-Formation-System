package com.iit.OOD.CW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SurveyProcessor {

    public List<Participant> processSurveyData(String filePath) {

        System.out.println("Processing survey data from: " + filePath);

        FileHandler fileHandler = new FileHandler();
        List<Participant> participants = new ArrayList<>();

        try {
            List<Participant> raw = fileHandler.readParticipantsFromCSV(filePath);

            ExecutorService executor = Executors.newFixedThreadPool(4);
            List<Future<Participant>> futures = new ArrayList<>();

            for (Participant p : raw) {
                futures.add(executor.submit(() -> validateAndFixParticipant(p)));
            }

            for (Future<Participant> future : futures) {
                Participant result = future.get();
                if (result != null) participants.add(result);
            }

            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("❌ Error while processing survey data: " + e.getMessage());
        }

        System.out.println("✔ Survey processing complete. Valid participants: " + participants.size());
        return participants;
    }


    // =========================================================
    // VALIDATION + AUTO-FIXING BAD DATA
    // =========================================================
    private Participant validateAndFixParticipant(Participant p) {
        try {
            if (p.getName() == null || p.getName().isBlank())
                throw new IllegalArgumentException("Missing name for participant.");

            if (p.getRole() == null || p.getRole().isBlank())
                throw new IllegalArgumentException("Missing preferred role for: " + p.getName());

            if (p.getSkillLevel() < 1 || p.getSkillLevel() > 5)
                throw new IllegalArgumentException("Invalid skill level for: " + p.getName());

            if (p.getPersonalityScore() < 0 || p.getPersonalityScore() > 100)
                throw new IllegalArgumentException("Invalid personality score for: " + p.getName());

            // Auto-classify personality if missing or invalid
            if (p.getPersonalityType() == null ||
                    p.getPersonalityType().isBlank() ||
                    p.getPersonalityType().equalsIgnoreCase("unknown") ||
                    p.getPersonalityType().equalsIgnoreCase("invalid")) {

                String newType = PersonalityClassifier.classifyPersonality(p.getPersonalityScore());
                p.setPersonalityType(newType);
            }

            return p;

        } catch (Exception ex) {
            System.err.println("⚠ Validation failed: " + ex.getMessage());
            return null;
        }
    }
}
