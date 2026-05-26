package com.xiana.fe3hguide.model;

public class Paralogue {
    private final String name;
    private final String characters;
    private final String routes;
    private final String chapterWindow;
    private final String rewards;
    private final int part;

    public Paralogue(String name, String characters, String routes,
                     String chapterWindow, String rewards, int part) {
        this.name = name;
        this.characters = characters;
        this.routes = routes;
        this.chapterWindow = chapterWindow;
        this.rewards = rewards;
        this.part = part;
    }

    public String getName() { return name; }
    public String getCharacters() { return characters; }
    public String getRoutes() { return routes; }
    public String getChapterWindow() { return chapterWindow; }
    public String getRewards() { return rewards; }
    public int getPart() { return part; }
}
