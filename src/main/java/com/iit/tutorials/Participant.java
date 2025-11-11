package com.iit.tutorials;

public class Participant {
    private String name;
    private String role;
    private int skillLevel;
    private String game;
    private int personalityScore;

    // Constructor
    public Participant(String name, String role, int skillLevel, String game, int personalityScore) {
        this.name = name;
        this.role = role;
        this.skillLevel = skillLevel;
        this.game = game;
        this.personalityScore = personalityScore;
    }

    // Getters
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getSkillLevel() { return skillLevel; }
    public String getGame() { return game; }
    public int getPersonalityScore() { return personalityScore; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }
    public void setGame(String game) { this.game = game; }
    public void setPersonalityScore(int personalityScore) { this.personalityScore = personalityScore; }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", skillLevel=" + skillLevel +
                ", game='" + game + '\'' +
                ", personalityScore=" + personalityScore +
                '}';
    }
}

