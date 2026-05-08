package com.xiana.fe3hguide.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.model.LectureQuestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectureQuestionsAdapter extends ArrayAdapter<LectureQuestion> {

    private final Set<String> expandedKeys = new HashSet<>();

    public LectureQuestionsAdapter(Context context, List<LectureQuestion> questions) {
        super(context, 0, questions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LectureQuestion item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cell_lecture_question, parent, false);
        }

        ImageView portrait = convertView.findViewById(R.id.imageView_lq_portrait);
        TextView name = convertView.findViewById(R.id.textView_lq_name);
        TextView phaseBadge = convertView.findViewById(R.id.badge_lq_phase);
        TextView question = convertView.findViewById(R.id.textView_lq_question);
        TextView answer = convertView.findViewById(R.id.textView_lq_answer);

        name.setText(item.getCharacterName());
        question.setText(item.getQuestion());
        answer.setText(item.getBestAnswer());

        boolean isPre = "pre".equals(item.getPhase());
        phaseBadge.setText(isPre ? "Part 1" : "Part 2");
        phaseBadge.setBackgroundColor(ContextCompat.getColor(getContext(),
                isPre ? R.color.green_tea : R.color.faction_blue_lions));
        phaseBadge.setTextColor(ContextCompat.getColor(getContext(),
                isPre ? R.color.mainText : R.color.white));

        // Pre-timeskip uses normal portrait; post-timeskip prefers the timeskip version
        String portraitKey = item.getCharacterName().toLowerCase();
        int portraitRes;
        if (isPre) {
            portraitRes = getContext().getResources().getIdentifier(
                    portraitKey, "drawable", getContext().getPackageName());
            if (portraitRes == 0) {
                portraitRes = getContext().getResources().getIdentifier(
                        portraitKey + "_timeskip", "drawable", getContext().getPackageName());
            }
        } else {
            portraitRes = getContext().getResources().getIdentifier(
                    portraitKey + "_timeskip", "drawable", getContext().getPackageName());
            if (portraitRes == 0) {
                portraitRes = getContext().getResources().getIdentifier(
                        portraitKey, "drawable", getContext().getPackageName());
            }
        }
        if (portraitRes != 0) portrait.setImageResource(portraitRes);

        // Expand/collapse question text on click
        String key = portraitKey + "|" + item.getPhase() + "|" + item.getQuestion();
        boolean isExpanded = expandedKeys.contains(key);
        question.setMaxLines(isExpanded ? Integer.MAX_VALUE : 2);
        question.setEllipsize(isExpanded ? null : TextUtils.TruncateAt.END);

        question.setOnClickListener(v -> {
            if (expandedKeys.contains(key)) {
                expandedKeys.remove(key);
            } else {
                expandedKeys.add(key);
            }
            notifyDataSetChanged();
        });

        return convertView;
    }
}
