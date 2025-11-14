package com.iit.OOD.CW;

import java.util.List;

public class SurveyProcessor {

    public List<Participant> processSurveyData(String filePath) {
        System.out.println("📋 Processing survey data from: " + filePath);

        FileHandler fileHandler = new FileHandler();
        List<Participant> participants = fileHandler.loadParticipants(filePath);

        return participants;
    }
}
