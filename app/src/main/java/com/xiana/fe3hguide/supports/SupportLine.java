package com.xiana.fe3hguide.supports;

public class SupportLine {
    private final String speaker;
    private final String text;
    private final String routeTag;

    public SupportLine(String speaker, String text, String routeTag) {
        this.speaker = speaker;
        this.text = text;
        this.routeTag = routeTag;
    }

    public static SupportLine header(String routeTag) {
        return new SupportLine(null, null, routeTag);
    }

    public boolean isHeader() { return speaker == null; }
    public String getSpeaker() { return speaker; }
    public String getText() { return text; }
    public String getRouteTag() { return routeTag; }
}
