package com.xiana.fe3hguide.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.model.Weapon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WeaponAdapter extends ArrayAdapter<Weapon> {

    private final Set<String> expandedKeys = new HashSet<>();

    public WeaponAdapter(Context context, int resource, List<Weapon> weapons) {
        super(context, resource, weapons);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Weapon weapon = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cell_weapon, parent, false);
        }

        TextView rankView = convertView.findViewById(R.id.textView_weapon_rank);
        TextView nameView = convertView.findViewById(R.id.textView_weapon_name);
        TextView typeView = convertView.findViewById(R.id.textView_weapon_type);
        TextView mtView = convertView.findViewById(R.id.textView_weapon_mt);
        TextView hitView = convertView.findViewById(R.id.textView_weapon_hit);
        TextView critView = convertView.findViewById(R.id.textView_weapon_crit);
        TextView rngView = convertView.findViewById(R.id.textView_weapon_rng);
        TextView wtView = convertView.findViewById(R.id.textView_weapon_wt);
        TextView usesView = convertView.findViewById(R.id.textView_weapon_uses);
        TextView effectView = convertView.findViewById(R.id.textView_weapon_effect);

        rankView.setText(weapon.getLvl());
        nameView.setText(weapon.getName());
        typeView.setText(weapon.getType());
        mtView.setText(String.valueOf(weapon.getMt()));
        hitView.setText(String.valueOf(weapon.getHit()));
        critView.setText(String.valueOf(weapon.getCrit()));
        rngView.setText(weapon.getRng());
        wtView.setText(String.valueOf(weapon.getWt()));
        usesView.setText(weapon.getUses() == 0 ? "—" : String.valueOf(weapon.getUses()));

        setRankBadgeColor(rankView, weapon.getLvl());
        setTypeBadgeColor(typeView, weapon.getType());

        String effect = weapon.getEffect();
        if (effect != null && !effect.trim().isEmpty()) {
            String key = weapon.getName() + "|" + weapon.getType();
            boolean expanded = expandedKeys.contains(key);
            effectView.setText(effect);
            effectView.setMaxLines(expanded ? Integer.MAX_VALUE : 2);
            effectView.setEllipsize(expanded ? null : TextUtils.TruncateAt.END);
            effectView.setVisibility(View.VISIBLE);
            effectView.setOnClickListener(v -> {
                if (expandedKeys.contains(key)) expandedKeys.remove(key);
                else expandedKeys.add(key);
                notifyDataSetChanged();
            });
        } else {
            effectView.setVisibility(View.GONE);
            effectView.setOnClickListener(null);
        }

        return convertView;
    }

    private void setRankBadgeColor(TextView view, String rank) {
        int colorRes;
        switch (rank.toUpperCase()) {
            case "D": colorRes = R.color.rank_d; break;
            case "C": colorRes = R.color.rank_c; break;
            case "B": colorRes = R.color.rank_b; break;
            case "A": colorRes = R.color.rank_a; break;
            default:  colorRes = R.color.rank_e; break;
        }
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.OVAL);
        bg.setColor(ContextCompat.getColor(getContext(), colorRes));
        view.setBackground(bg);
    }

    private void setTypeBadgeColor(TextView view, String type) {
        int colorRes;
        switch (type.toLowerCase()) {
            case "lance":    colorRes = R.color.blue_background;   break;
            case "axe":      colorRes = R.color.orange_background; break;
            case "bow":      colorRes = R.color.green_background;  break;
            case "gauntlet": colorRes = R.color.purple_background; break;
            default:         colorRes = R.color.red_background;    break;
        }
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(8f);
        bg.setColor(ContextCompat.getColor(getContext(), colorRes));
        view.setBackground(bg);
    }
}
