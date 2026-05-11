package com.xiana.fe3hguide.supports;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiana.fe3hguide.R;

import java.util.ArrayList;
import java.util.List;

public class SupportsTextActivity extends AppCompatActivity {

    private static final String[] ROUTE_KEYWORDS = {
        // Routes and factions
        "Azure Moon", "Verdant Wind", "Silver Snow", "Crimson Flower",
        "Black Eagles", "Blue Lions", "Golden Deer", "route", "Route",
        "Alternative", "Alternate", "post-time skip", "post-war", "Non-Black Eagles",
        // Character alive/dead state
        "alive", "dead", "kidnapping",
        // Recruitment and paralogue conditions
        "recruited", "paralogue", "Lonato",
        // Game phase conditions
        "Academy Phase", "War Phase", "White Clouds", "Post Time Skip",
        "Part 1", "Part 2", "Part I", "Part II", "Phase 1", "Phase 2",
        // Support rank conditions
        "Support Rank", "support is achieved",
        // Misc conditions
        "Byleth is female", "Pre-1.0.2", "Post-1.0.2"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supports_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Support conversations");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        String rank = intent.getStringExtra("supportRank");
        String rawText = intent.getStringExtra("supportText");
        String char1Name = intent.getStringExtra("char1Name");
        String char2Name = intent.getStringExtra("char2Name");
        String char1Portrait = intent.getStringExtra("char1Portrait");
        String char2Portrait = intent.getStringExtra("char2Portrait");

        int rankDrawableRes = getRankDrawable(rank);
        ImageView rankImage = (ImageView) findViewById(R.id.imageView_support_rank);
        TextView rankText = (TextView) findViewById(R.id.textView_support_rank);
        if (rankDrawableRes != 0) {
            rankImage.setImageResource(rankDrawableRes);
            rankImage.setVisibility(View.VISIBLE);
            rankText.setVisibility(View.GONE);
        } else {
            rankText.setText(rank);
            rankText.setVisibility(View.VISIBLE);
            rankImage.setVisibility(View.GONE);
        }

        List<SupportLine> lines = parseDialogue(sanitize(rawText), char1Name, char2Name);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_support_dialogue);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new SupportDialogueAdapter(
                lines, char1Name, char2Name, char1Portrait, char2Portrait));
    }

    private String sanitize(String text) {
        if (text == null) return "";
        // Strip literal backslash-u hex sequences that appear as raw text in the data files
        text = text.replaceAll("\\\\u[0-9A-Fa-f]{4}", "");
        // Strip replacement char (U+FFFD), BOM (U+FEFF), word joiner (U+2060),
        // and zero-width spaces/joiners (U+200B, U+200C, U+200D)
        text = text.replaceAll("[\ufffd\ufeff\u2060\u200b\u200c\u200d]", "");
        return text.trim();
    }

    private List<SupportLine> parseDialogue(String rawText, String char1, String char2) {
        List<SupportLine> result = new ArrayList<>();
        if (rawText == null || rawText.isEmpty()) return result;

        String[] segments = rawText.split("\\\\n");
        String currentSpeaker = null;
        String currentRouteTag = null;
        StringBuilder currentText = new StringBuilder();

        for (String segment : segments) {
            segment = segment.trim();
            if (segment.isEmpty()) continue;

            // Format 2: standalone route tag on its own line (e.g. "Azure Moon" or "Verdant Wind/Silver Snow")
            if (isStandaloneRouteTag(segment)) {
                if (currentSpeaker != null) {
                    String text = currentText.toString().trim();
                    if (!text.isEmpty()) {
                        result.add(new SupportLine(currentSpeaker, text, currentRouteTag));
                    }
                    currentText = new StringBuilder();
                    currentRouteTag = null;
                }
                result.add(SupportLine.header(segment));
                continue;
            }

            // Format 1: inline route annotation like "(Azure Moon only) Speaker: text"
            String routeTag = null;
            String processedSegment = segment;
            if (segment.startsWith("(")) {
                int close = segment.indexOf(')');
                if (close > 0 && close < segment.length() - 1) {
                    String candidate = segment.substring(1, close);
                    if (isRouteTag(candidate)) {
                        routeTag = candidate;
                        processedSegment = segment.substring(close + 1).trim();
                    }
                }
            }

            String speaker = detectSpeaker(processedSegment, char1, char2);
            if (speaker != null) {
                if (currentSpeaker != null) {
                    String text = currentText.toString().trim();
                    if (!text.isEmpty()) {
                        result.add(new SupportLine(currentSpeaker, text, currentRouteTag));
                    }
                }
                currentSpeaker = speaker;
                currentRouteTag = routeTag;
                currentText = new StringBuilder();
                String afterColon = processedSegment.substring(processedSegment.indexOf(':') + 1).trim();
                if (!afterColon.isEmpty()) {
                    currentText.append(afterColon);
                }
            } else if (routeTag != null && currentSpeaker != null) {
                // Route-tagged dialogue variant with no speaker change — own bubble with badge
                String text = currentText.toString().trim();
                if (!text.isEmpty()) {
                    result.add(new SupportLine(currentSpeaker, text, currentRouteTag));
                }
                currentRouteTag = routeTag;
                String dialogueText = processedSegment.startsWith(":")
                        ? processedSegment.substring(1).trim()
                        : processedSegment;
                currentText = new StringBuilder(dialogueText);
            } else {
                // Choice line or plain continuation
                String formatted = formatLine(segment);
                if (currentText.length() > 0) currentText.append("\n");
                currentText.append(formatted);
            }
        }

        if (currentSpeaker != null) {
            String text = currentText.toString().trim();
            if (!text.isEmpty()) {
                result.add(new SupportLine(currentSpeaker, text, currentRouteTag));
            }
        }

        return result;
    }

    private boolean isRouteTag(String content) {
        for (String kw : ROUTE_KEYWORDS) {
            if (content.contains(kw)) return true;
        }
        return false;
    }

    // Standalone route tag: its own line with no colon, either bare ("Azure Moon") or
    // fully parenthesized ("(If conversation takes place before Lonato's death)")
    private boolean isStandaloneRouteTag(String segment) {
        if (segment.contains(":")) return false;
        if (!segment.startsWith("(")) return isRouteTag(segment);
        // Fully-parenthesized condition tag — entire segment wrapped in ()
        if (segment.endsWith(")")) return isRouteTag(segment.substring(1, segment.length() - 1));
        return false;
    }

    private String detectSpeaker(String line, String char1, String char2) {
        if (char1 != null && line.startsWith(char1 + ":")) return char1;
        if (char2 != null && line.startsWith(char2 + ":")) return char2;
        // Skip choice lines
        if (line.startsWith("Choice ")) return null;
        // Detect any third-party speaker via "Name: text" pattern
        int colon = line.indexOf(':');
        if (colon >= 3 && colon <= 25 && colon < line.length() - 1) {
            String name = line.substring(0, colon);
            if (name.matches("[A-Z][a-zA-Z'-]+(?: [A-Z][a-zA-Z'-]+){0,2}")) {
                return name;
            }
        }
        return null;
    }

    private String formatLine(String line) {
        if (line.startsWith("Choice 1 response: ")) return "(1) " + line.substring("Choice 1 response: ".length());
        if (line.startsWith("Choice 2 response: ")) return "(2) " + line.substring("Choice 2 response: ".length());
        if (line.startsWith("Choice 3 response: ")) return "(3) " + line.substring("Choice 3 response: ".length());
        if (line.startsWith("Choice 1: ")) return "(1) " + line.substring("Choice 1: ".length());
        if (line.startsWith("Choice 2: ")) return "(2) " + line.substring("Choice 2: ".length());
        if (line.startsWith("Choice 3: ")) return "(3) " + line.substring("Choice 3: ".length());
        return line;
    }

    private int getRankDrawable(String rank) {
        if (rank == null) return 0;
        switch (rank) {
            case "C Support":  return R.drawable.csupport;
            case "C+ Support": return R.drawable.cplusupport;
            case "B Support":  return R.drawable.bsupport;
            case "B+ Support": return R.drawable.bplusupport;
            case "A Support":  return R.drawable.asupport;
            case "A+ Support": return R.drawable.aplusupport;
            case "S Support":  return R.drawable.ssupport;
            default:           return 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
