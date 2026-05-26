package com.xiana.fe3hguide.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.model.Paralogue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ParalogueAdapter extends ArrayAdapter<Paralogue> {

    public interface OnNavigateListener {
        void onWeaponSelected(String name);
        void onBattalionSelected(String name);
    }

    private static final Set<String> NO_TIMESKIP = new HashSet<>(Arrays.asList(
            "rhea", "seteth", "flayn", "hanneman", "manuela",
            "gilbert", "alois", "catherine", "shamir", "jeritza", "anna"
    ));

    private final Set<String> weaponNames;
    private final Set<String> battalionNames;
    private final OnNavigateListener listener;

    public ParalogueAdapter(Context context, List<Paralogue> paralogues,
                            Set<String> weaponNames, Set<String> battalionNames,
                            OnNavigateListener listener) {
        super(context, 0, paralogues);
        this.weaponNames = weaponNames;
        this.battalionNames = battalionNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paralogue item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cell_paralogue, parent, false);
        }

        ImageView portrait1 = convertView.findViewById(R.id.imageView_para_portrait1);
        ImageView portrait2 = convertView.findViewById(R.id.imageView_para_portrait2);
        TextView name       = convertView.findViewById(R.id.textView_para_name);
        TextView partBadge  = convertView.findViewById(R.id.textView_para_part);
        TextView routeBadge = convertView.findViewById(R.id.textView_para_route);
        TextView characters = convertView.findViewById(R.id.textView_para_characters);
        TextView window     = convertView.findViewById(R.id.textView_para_window);
        TextView rewards    = convertView.findViewById(R.id.textView_para_rewards);

        name.setText(item.getName());
        characters.setText("Requires: " + item.getCharacters());
        window.setText(item.getChapterWindow());

        // Part badge
        boolean isPart1 = item.getPart() == 1;
        partBadge.setText(isPart1 ? "Part 1" : "Part 2");
        setRoundedBg(partBadge, ContextCompat.getColor(getContext(),
                isPart1 ? R.color.green_tea : R.color.faction_blue_lions));
        partBadge.setTextColor(ContextCompat.getColor(getContext(),
                isPart1 ? R.color.mainText : R.color.white));

        // Route badge
        routeBadge.setText(getRouteLabel(item.getRoutes()));
        setRoundedBg(routeBadge, ContextCompat.getColor(getContext(),
                getRouteColor(item.getRoutes())));

        // Portraits
        String[] chars = item.getCharacters().split(", ");
        int res1 = getPortraitResId(chars[0], item.getPart());
        if (res1 != 0) {
            portrait1.setImageResource(res1);
            portrait1.setVisibility(View.VISIBLE);
        } else {
            portrait1.setVisibility(View.GONE);
        }
        if (chars.length >= 2) {
            int res2 = getPortraitResId(chars[1], item.getPart());
            if (res2 != 0) {
                portrait2.setImageResource(res2);
                portrait2.setVisibility(View.VISIBLE);
            } else {
                portrait2.setVisibility(View.GONE);
            }
        } else {
            portrait2.setVisibility(View.GONE);
        }

        // Rewards with clickable weapon/battalion links
        rewards.setText(buildRewardsSpan(item.getRewards()));
        rewards.setMovementMethod(LinkMovementMethod.getInstance());
        rewards.setMaxLines(Integer.MAX_VALUE);
        rewards.setEllipsize(null);

        return convertView;
    }

    private SpannableString buildRewardsSpan(String rewards) {
        SpannableString spannable = new SpannableString(rewards);
        String lower = rewards.toLowerCase(Locale.ROOT);
        // Track spanned positions to avoid overlapping spans
        boolean[] spanned = new boolean[rewards.length()];

        applySpans(spannable, lower, spanned, weaponNames, R.color.faction_golden_deer, true);
        applySpans(spannable, lower, spanned, battalionNames, R.color.faction_blue_lions, false);

        return spannable;
    }

    private void applySpans(SpannableString spannable, String lower, boolean[] spanned,
                            Set<String> names, int colorRes, boolean isWeapon) {
        for (String name : names) {
            String nameLower = name.toLowerCase(Locale.ROOT);
            int idx = lower.indexOf(nameLower);
            while (idx >= 0) {
                int end = idx + name.length();
                if (!isRangeSpanned(spanned, idx, end)) {
                    markSpanned(spanned, idx, end);
                    final String matched = name;
                    spannable.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            if (listener == null) return;
                            if (isWeapon) listener.onWeaponSelected(matched);
                            else listener.onBattalionSelected(matched);
                        }
                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(ContextCompat.getColor(getContext(), colorRes));
                            ds.setFakeBoldText(true);
                            ds.setUnderlineText(false);
                        }
                    }, idx, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                idx = lower.indexOf(nameLower, end);
            }
        }
    }

    private boolean isRangeSpanned(boolean[] spanned, int start, int end) {
        for (int i = start; i < end; i++) {
            if (spanned[i]) return true;
        }
        return false;
    }

    private void markSpanned(boolean[] spanned, int start, int end) {
        for (int i = start; i < end; i++) spanned[i] = true;
    }

    private int getPortraitResId(String characterName, int part) {
        String key = characterName.toLowerCase().trim();
        if (key.equals("byleth")) key = "mbyleth";

        // Handle strings like "C support with Rhea" — extract the last word as the character key
        if (key.contains(" ") && getResId(key) == 0) {
            String[] words = key.split("\\s+");
            key = words[words.length - 1];
            if (key.equals("byleth")) key = "mbyleth";
        }

        if (NO_TIMESKIP.contains(key)) {
            return getResId(key);
        } else if (part == 1) {
            int id = getResId(key);
            return id != 0 ? id : getResId(key + "_timeskip");
        } else {
            int id = getResId(key + "_timeskip");
            return id != 0 ? id : getResId(key);
        }
    }

    private int getResId(String name) {
        return getContext().getResources().getIdentifier(
                name, "drawable", getContext().getPackageName());
    }

    private void setRoundedBg(TextView view, int color) {
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(6f);
        bg.setColor(color);
        view.setBackground(bg);
    }

    private String getRouteLabel(String routes) {
        switch (routes) {
            case "All":                                      return "All Routes";
            case "Azure Moon":                               return "Azure Moon";
            case "Crimson Flower":                           return "Crimson Flower";
            case "Verdant Wind":                             return "Verdant Wind";
            case "Silver Snow":                              return "Silver Snow";
            case "Azure Moon, Verdant Wind, Silver Snow":    return "Excl. CF";
            case "Azure Moon, Verdant Wind":                 return "AM + VW";
            default:                                         return routes;
        }
    }

    private int getRouteColor(String routes) {
        switch (routes) {
            case "Azure Moon":        return R.color.faction_blue_lions;
            case "Crimson Flower":    return R.color.faction_black_eagles;
            case "Verdant Wind":      return R.color.faction_golden_deer;
            case "Silver Snow":       return R.color.faction_church;
            case "All":               return R.color.purpleDarkMode;
            default:                  return R.color.dark_gray_background;
        }
    }
}
