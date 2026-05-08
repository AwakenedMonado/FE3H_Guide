package com.xiana.fe3hguide.model;

public class LectureQuestion {
    private final String characterName;
    private final String question;
    private final String bestAnswer;
    private final String phase;

    public LectureQuestion(String characterName, String question, String bestAnswer, String phase) {
        this.characterName = characterName;
        this.question = question;
        this.bestAnswer = bestAnswer;
        this.phase = phase;
    }

    public String getCharacterName() { return characterName; }
    public String getQuestion() { return question; }
    public String getBestAnswer() { return bestAnswer; }
    public String getPhase() { return phase; }
}
