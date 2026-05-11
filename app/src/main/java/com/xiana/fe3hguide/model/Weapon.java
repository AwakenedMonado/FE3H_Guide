package com.xiana.fe3hguide.model;

public class Weapon {
    private final String name;
    private final String type;
    private final String lvl;
    private final int mt;
    private final int hit;
    private final int crit;
    private final String rng;
    private final int wt;
    private final int uses;
    private final String effect;

    public Weapon(String name, String type, String lvl, int mt, int hit, int crit,
                  String rng, int wt, int uses, String effect) {
        this.name = name;
        this.type = type;
        this.lvl = lvl;
        this.mt = mt;
        this.hit = hit;
        this.crit = crit;
        this.rng = rng;
        this.wt = wt;
        this.uses = uses;
        this.effect = effect;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getLvl() { return lvl; }
    public int getMt() { return mt; }
    public int getHit() { return hit; }
    public int getCrit() { return crit; }
    public String getRng() { return rng; }
    public int getWt() { return wt; }
    public int getUses() { return uses; }
    public String getEffect() { return effect; }
}
