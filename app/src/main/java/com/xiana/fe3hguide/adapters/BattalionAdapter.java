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
import com.xiana.fe3hguide.model.Battalion;

import java.util.List;

public class BattalionAdapter extends ArrayAdapter<Battalion> {

    public BattalionAdapter(Context context, int resource, List<Battalion> battalions) {
        super(context, resource, battalions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Battalion battalion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cell_battalion, parent, false);
        }

        TextView name = convertView.findViewById(R.id.textView_battalion_name);
        TextView authority = convertView.findViewById(R.id.textView_battalion_authority);
        ImageView movementImage = convertView.findViewById(R.id.imageView_battalion_movement);

        name.setText(battalion.getName());
        authority.setText(battalion.getAuthorityLevel());

        String movementType = battalion.getMovementType();
        if (movementType != null && !movementType.isEmpty()) {
            int resId = getContext().getResources().getIdentifier(
                    movementType.toLowerCase(), "drawable", getContext().getPackageName());
            if (resId != 0) {
                movementImage.setImageResource(resId);
                movementImage.setVisibility(View.VISIBLE);
            } else {
                movementImage.setVisibility(View.INVISIBLE);
            }
        } else {
            movementImage.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
