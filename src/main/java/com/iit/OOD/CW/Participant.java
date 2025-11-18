package com.iit.OOD.CW;

public class Participant {
    private String id;
    private String name;
    private String email;
    private String game;
    private int skillLevel;
    private String role;
    private int personalityScore;
    private String personalityType;

    // Constructor
    public Participant(String id, String name, String email, String game,
                       int skillLevel, String role, int personalityScore, String personalityType) {

        this.id = id != null ? id.trim() : "";
        this.name = name != null ? name.trim() : "";
        this.email = email != null ? email.trim() : "";
        this.game = game != null ? game.trim() : "";
        this.skillLevel = skillLevel;
        this.role = role != null ? role.trim() : "";
        this.personalityScore = personalityScore;

        // Auto-generate type if missing or invalid
        if (personalityType == null || personalityType.isBlank()) {
            this.personalityType = PersonalityClassifier.classifyPersonality(personalityScore);
        } else {
            this.personalityType = personalityType.trim();
        }
    }

    // Validation helpers
    public boolean hasValidSkill() {
        return skillLevel >= 1 && skillLevel <= 5;
    }

    public boolean hasValidRole() {
        return role != null && !role.trim().isEmpty();
    }

    public boolean hasValidPersonalityScore() {
        return personalityScore >= 0 && personalityScore <= 100;
    }

    public boolean isValid() {
        return hasValidSkill() && hasValidRole() && hasValidPersonalityScore();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getGame() { return game; }
    public int getSkillLevel() { return skillLevel; }
    public String getRole() { return role; }
    public int getPersonalityScore() { return personalityScore; }
    public String getPersonalityType() { return personalityType; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setGame(String game) { this.game = game; }
    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }
    public void setRole(String role) { this.role = role; }
    public void setPersonalityScore(int personalityScore) { this.personalityScore = personalityScore; }
    public void setPersonalityType(String personalityType) { this.personalityType = personalityType; }

    @Override
    public String toString() {
        return String.format(
                "Participant{id='%s', name='%s', email='%s', game='%s', skillLevel=%d, role='%s', score=%d, type='%s'}",
                id, name, email, game, skillLevel, role, personalityScore, personalityType
        );
    }
}
