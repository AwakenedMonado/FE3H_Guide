package com.xiana.fe3hguide.supports;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xiana.fe3hguide.R;

import java.util.List;

public class SupportDialogueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_LEFT = 0;
    private static final int VIEW_RIGHT = 1;
    private static final int VIEW_HEADER = 2;

    private final List<SupportLine> lines;
    private final String char1Name;
    private final String char2Name;
    private final String char1Portrait;
    private final String char2Portrait;

    public SupportDialogueAdapter(List<SupportLine> lines,
                                   String char1Name, String char2Name,
                                   String char1Portrait, String char2Portrait) {
        this.lines = lines;
        this.char1Name = char1Name;
        this.char2Name = char2Name;
        this.char1Portrait = char1Portrait;
        this.char2Portrait = char2Portrait;
    }

    @Override
    public int getItemViewType(int position) {
        SupportLine line = lines.get(position);
        if (line.isHeader()) return VIEW_HEADER;
        if (line.getSpeaker().equals(char1Name)) return VIEW_LEFT;
        if (line.getSpeaker().equals(char2Name)) return VIEW_RIGHT;
        return VIEW_LEFT;  // third-party NPC
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_HEADER) {
            View view = inflater.inflate(R.layout.item_support_header, parent, false);
            return new HeaderViewHolder(view);
        }
        int layout = viewType == VIEW_LEFT ? R.layout.item_support_left : R.layout.item_support_right;
        return new DialogueViewHolder(inflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SupportLine line = lines.get(position);
        Context context = holder.itemView.getContext();

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder h = (HeaderViewHolder) holder;
            h.text.setText(line.getRouteTag());
            int color = getRouteColor(context, line.getRouteTag());
            h.text.setTextColor(color);
            h.lineLeft.setBackgroundColor(color);
            h.lineRight.setBackgroundColor(color);
            return;
        }

        DialogueViewHolder h = (DialogueViewHolder) holder;
        h.name.setText(line.getSpeaker());
        h.text.setText(line.getText());

        // Resolve portrait
        String portraitName = resolvePortrait(line.getSpeaker());
        if (portraitName != null) {
            int resId = context.getResources().getIdentifier(
                    portraitName, "drawable", context.getPackageName());
            if (resId != 0) h.portrait.setImageResource(resId);
        }

        // Route badge
        String routeTag = line.getRouteTag();
        if (routeTag != null && !routeTag.isEmpty()) {
            h.routeTag.setText(routeTag);
            h.routeTag.setVisibility(View.VISIBLE);
            GradientDrawable bg = (GradientDrawable) ContextCompat.getDrawable(
                    context, R.drawable.bg_route_tag).mutate();
            bg.setColor(getRouteColor(context, routeTag));
            h.routeTag.setBackground(bg);
        } else {
            h.routeTag.setVisibility(View.GONE);
        }
    }

    private String resolvePortrait(String speaker) {
        if (speaker.equals(char1Name)) return char1Portrait;
        if (speaker.equals(char2Name)) return char2Portrait;
        if (speaker.equalsIgnoreCase("Gatekeeper")) return "gatekeeper";
        if (speaker.equalsIgnoreCase("Rogue")) return "rogue";
        return "mystery_man";
    }

    private int getRouteColor(Context context, String tag) {
        if (tag == null) return ContextCompat.getColor(context, R.color.faction_ashen_wolves);
        if (tag.contains("Azure Moon") || tag.contains("Blue Lions")) {
            return ContextCompat.getColor(context, R.color.faction_blue_lions);
        } else if (tag.contains("Crimson Flower") || tag.contains("Black Eagles")) {
            return ContextCompat.getColor(context, R.color.faction_black_eagles);
        } else if (tag.contains("Verdant Wind") || tag.contains("Golden Deer")) {
            return ContextCompat.getColor(context, R.color.faction_golden_deer);
        } else if (tag.contains("Silver Snow") || tag.contains("Church")) {
            return ContextCompat.getColor(context, R.color.faction_church);
        } else {
            return ContextCompat.getColor(context, R.color.faction_ashen_wolves);
        }
    }

    static class DialogueViewHolder extends RecyclerView.ViewHolder {
        final ImageView portrait;
        final TextView name;
        final TextView text;
        final TextView routeTag;

        DialogueViewHolder(View itemView) {
            super(itemView);
            portrait = itemView.findViewById(R.id.support_portrait);
            name = itemView.findViewById(R.id.support_name);
            text = itemView.findViewById(R.id.support_text);
            routeTag = itemView.findViewById(R.id.support_route_tag);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        final TextView text;
        final View lineLeft;
        final View lineRight;

        HeaderViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.support_header_text);
            lineLeft = itemView.findViewById(R.id.header_line_left);
            lineRight = itemView.findViewById(R.id.header_line_right);
        }
    }
}
