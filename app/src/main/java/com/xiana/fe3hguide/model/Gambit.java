package com.xiana.fe3hguide.model;

public class Gambit {

    private String name;
    private String type;
    private String mt;
    private String hit;
    private String range;
    private String description;
    private String formationImage;

    private Gambit() {}

    public String getName() { return name; }
    public String getType() { return type; }
    public String getMt() { return mt; }
    public String getHit() { return hit; }
    public String getRange() { return range; }
    public String getDescription() { return description; }
    public String getFormationImage() { return formationImage; }

    public static class Builder {
        private final Gambit gambit = new Gambit();

        public Builder(String name) {
            gambit.name = name;
        }

        public Builder withType(String type) {
            gambit.type = type;
            return this;
        }

        public Builder withMt(String mt) {
            gambit.mt = mt;
            return this;
        }

        public Builder withHit(String hit) {
            gambit.hit = hit;
            return this;
        }

        public Builder withRange(String range) {
            gambit.range = range;
            return this;
        }

        public Builder withDescription(String description) {
            gambit.description = description;
            return this;
        }

        public Builder withFormationImage(String formationImage) {
            gambit.formationImage = formationImage;
            return this;
        }

        public Gambit build() { return gambit; }
    }
}
