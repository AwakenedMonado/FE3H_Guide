package com.xiana.fe3hguide.model;

public class Battalion {

    private String name;
    private String authorityLevel;
    private String endurance;
    private String str;
    private String mag;
    private String hit;
    private String crit;
    private String avo;
    private String prt;
    private String rsl;
    private String cha;
    private String gambitName;
    private String movementType;
    private String gambitMt;

    private Battalion() {}

    public String getName() { return name; }
    public String getAuthorityLevel() { return authorityLevel; }
    public String getEndurance() { return endurance; }
    public String getStr() { return str; }
    public String getMag() { return mag; }
    public String getHit() { return hit; }
    public String getCrit() { return crit; }
    public String getAvo() { return avo; }
    public String getPrt() { return prt; }
    public String getRsl() { return rsl; }
    public String getCha() { return cha; }
    public String getGambitName() { return gambitName; }
    public String getMovementType() { return movementType; }
    public String getGambitMt() { return gambitMt; }

    public static class Builder {
        private final Battalion battalion = new Battalion();

        public Builder(String name) {
            battalion.name = name;
        }

        public Builder withAuthorityLevel(String authorityLevel) {
            battalion.authorityLevel = authorityLevel;
            return this;
        }

        public Builder withEndurance(String endurance) {
            battalion.endurance = endurance;
            return this;
        }

        public Builder withStr(String str) {
            battalion.str = str;
            return this;
        }

        public Builder withMag(String mag) {
            battalion.mag = mag;
            return this;
        }

        public Builder withHit(String hit) {
            battalion.hit = hit;
            return this;
        }

        public Builder withCrit(String crit) {
            battalion.crit = crit;
            return this;
        }

        public Builder withAvo(String avo) {
            battalion.avo = avo;
            return this;
        }

        public Builder withPrt(String prt) {
            battalion.prt = prt;
            return this;
        }

        public Builder withRsl(String rsl) {
            battalion.rsl = rsl;
            return this;
        }

        public Builder withCha(String cha) {
            battalion.cha = cha;
            return this;
        }

        public Builder withGambitName(String gambitName) {
            battalion.gambitName = gambitName;
            return this;
        }

        public Builder withMovementType(String movementType) {
            battalion.movementType = movementType;
            return this;
        }

        public Builder withGambitMt(String gambitMt) {
            battalion.gambitMt = gambitMt;
            return this;
        }

        public Battalion build() { return battalion; }
    }
}
