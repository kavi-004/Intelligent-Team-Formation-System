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
    public Participant(String id, String name, String email, String game, int skillLevel,
                       String role, int personalityScore, String personalityType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.game = game;
        this.skillLevel = skillLevel;
        this.role = role;
        this.personalityScore = personalityScore;
        this.personalityType = personalityType;
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
