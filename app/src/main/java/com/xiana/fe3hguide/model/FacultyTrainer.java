package com.xiana.fe3hguide.model;

public class FacultyTrainer {

    private final String name;
    private final String skills;       // pipe-delimited, e.g. "Sword|Faith|Flying"
    private final String part1Routes;  // "All" or "None"
    private final String part2Routes;  // "All", "AM|VW|SS", "AM", "VW", "CF", "None"

    public FacultyTrainer(String name, String skills, String part1Routes, String part2Routes) {
        this.name = name;
        this.skills = skills;
        this.part1Routes = part1Routes;
        this.part2Routes = part2Routes;
    }

    public String getName() { return name; }
    public String getSkills() { return skills; }
    public String getPart1Routes() { return part1Routes; }
    public String getPart2Routes() { return part2Routes; }
}
