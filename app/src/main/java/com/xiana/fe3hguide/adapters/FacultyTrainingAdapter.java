package com.xiana.fe3hguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.model.FacultyTrainer;

import java.util.List;

public class FacultyTrainingAdapter extends ArrayAdapter<FacultyTrainer> {

    public FacultyTrainingAdapter(Context context, List<FacultyTrainer> trainers) {
        super(context, 0, trainers);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FacultyTrainer trainer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cell_faculty_trainer, parent, false);
        }

        ImageView portrait = convertView.findViewById(R.id.imageView_trainer_portrait);
        TextView name = convertView.findViewById(R.id.textView_trainer_name);
        ImageView[] skillViews = {
            convertView.findViewById(R.id.imageView_skill_1),
            convertView.findViewById(R.id.imageView_skill_2),
            convertView.findViewById(R.id.imageView_skill_3),
            convertView.findViewById(R.id.imageView_skill_4),
            convertView.findViewById(R.id.imageView_skill_5)
        };
        TextView badgeP1 = convertView.findViewById(R.id.badge_p1);
        TextView badgeAM = convertView.findViewById(R.id.badge_am);
        TextView badgeVW = convertView.findViewById(R.id.badge_vw);
        TextView badgeSS = convertView.findViewById(R.id.badge_ss);
        TextView badgeCF = convertView.findViewById(R.id.badge_cf);

        name.setText(trainer.getName());

        // Prefer timeskip portrait, fall back to normal
        String portraitKey = trainer.getName().toLowerCase();
        int portraitRes = getContext().getResources().getIdentifier(
                portraitKey + "_timeskip", "drawable", getContext().getPackageName());
        if (portraitRes == 0) {
            portraitRes = getContext().getResources().getIdentifier(
                    portraitKey, "drawable", getContext().getPackageName());
        }
        if (portraitRes != 0) portrait.setImageResource(portraitRes);

        // Skill icons
        String[] skills = trainer.getSkills().split("\\|");
        for (int i = 0; i < skillViews.length; i++) {
            if (i < skills.length) {
                String skill = skills[i].trim();
                int drawableId = getSkillDrawable(skill);
                if (drawableId != 0) skillViews[i].setImageResource(drawableId);
                skillViews[i].setBackground(null);
                skillViews[i].setVisibility(View.VISIBLE);
            } else {
                skillViews[i].setVisibility(View.GONE);
            }
        }

        // Part 1 badge
        badgeP1.setVisibility(!"None".equals(trainer.getPart1Routes()) ? View.VISIBLE : View.GONE);

        // Part 2 route badges
        String p2 = trainer.getPart2Routes();
        boolean allRoutes = "All".equals(p2);
        badgeAM.setVisibility(allRoutes || p2.contains("AM") ? View.VISIBLE : View.GONE);
        badgeVW.setVisibility(allRoutes || p2.contains("VW") ? View.VISIBLE : View.GONE);
        badgeSS.setVisibility(allRoutes || p2.contains("SS") ? View.VISIBLE : View.GONE);
        badgeCF.setVisibility(allRoutes || p2.contains("CF") ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private int getSkillDrawable(String skill) {
        String drawableName;
        switch (skill) {
            case "Brawling": drawableName = "brawl"; break;
            case "Heavy Armor": drawableName = "heavy_armor"; break;
            default: drawableName = skill.toLowerCase(); break;
        }
        return getContext().getResources().getIdentifier(
                drawableName, "drawable", getContext().getPackageName());
    }
}
