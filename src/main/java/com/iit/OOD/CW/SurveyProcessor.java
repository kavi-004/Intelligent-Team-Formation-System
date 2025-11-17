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

            // Use a thread pool to validate and process participants concurrently
            ExecutorService executor = Executors.newFixedThreadPool(4);
            List<Future<Participant>> futures = new ArrayList<>();

            for (Participant p : raw) {
                futures.add(executor.submit(() -> validateParticipant(p)));
            }

            for (Future<Participant> f : futures) {
                Participant processed = f.get();
                if (processed != null) {
                    participants.add(processed);
                }
            }

            executor.shutdown();

        } catch (Exception e) {
            System.err.println("Error while processing survey data: " + e.getMessage());
        }

        return participants;
    }

    private Participant validateParticipant(Participant p) {
        try {
            if (p.getRole() == null || p.getRole().isEmpty()) {
                throw new IllegalArgumentException("Missing preferred role for: " + p.getName());
            }

            if (p.getPersonalityScore() < 0 || p.getPersonalityScore() > 100) {
                throw new IllegalArgumentException("Invalid personality score for: " + p.getName());
            }

            if (p.getSkillLevel() < 1 || p.getSkillLevel() > 5) {
                throw new IllegalArgumentException("Invalid skill level for: " + p.getName());
            }

            return p;

        } catch (Exception ex) {
            System.err.println("Validation failed: " + ex.getMessage());
            return null; // skip invalid entries
        }
    }
}
