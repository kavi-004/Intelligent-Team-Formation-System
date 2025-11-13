package com.iit.OOD.CW;

import java.util.List;

public class SurveyProcessor {

    public List<Participant> processSurveyData(String filePath) {
        System.out.println("📋 Processing survey data from: " + filePath);
        List<Participant> participants = FileHandler.readParticipantsFromCSV(filePath);
        System.out.println("✅ " + participants.size() + " participants loaded successfully!");
        return participants;
    }
}
