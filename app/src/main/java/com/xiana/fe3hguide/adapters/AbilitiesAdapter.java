package com.xiana.fe3hguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.abilities.AbilityPopupHost;
import com.xiana.fe3hguide.model.Ability;

public class AbilitiesAdapter extends
        RecyclerView.Adapter<AbilitiesAdapter.ViewHolderAbilities> {

    private List<Ability> abilities;
    private AbilityPopupHost host;
    private HashMap<String, Integer> positions;     // Maps names to positions in abilities

    public AbilitiesAdapter(List<Ability> abilities, AbilityPopupHost host){
        this.abilities = abilities;
        positions = new HashMap<>();
        for (int i = 0; i < abilities.size(); i++){
            positions.put(abilities.get(i).getName(), i);
        }
        this.host = host;
    }

    @Override
    public int getItemCount(){
        return abilities.size();
    }

    @Override
    public ViewHolderAbilities onCreateViewHolder(
            ViewGroup parent, int viewType){
        ConstraintLayout cv = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_w_image, parent, false);
        return new ViewHolderAbilities(cv, host, this);
    }

    @Override
    public void onBindViewHolder(ViewHolderAbilities holder, int position){
        ConstraintLayout constraintLayout = holder.constraintLayout;

        // The title is the name of the ability
        TextView title = (TextView) constraintLayout.findViewById(R.id.textView_card_title);
        title.setText(abilities.get(position).getName());

        // The image is the ability icon
        ImageView imageView = (ImageView) constraintLayout.findViewById(R.id.imageView_card_icon);
        String iconName = abilities.get(position).getIcon();
        imageView.setImageResource(constraintLayout.getContext().getResources().getIdentifier(iconName, "drawable", constraintLayout.getContext().getPackageName()));

        // The detail text shows the effect of the ability
        TextView description = (TextView) constraintLayout.findViewById(R.id.textView_card_detail);
        description.setText(abilities.get(position).getEffect());
    }

    public Ability getAbility(String name){
        return abilities.get(positions.get(name));
    }

    public static class ViewHolderAbilities extends RecyclerView.ViewHolder {

        private ConstraintLayout constraintLayout;
        private AbilityPopupHost host;
        private AbilitiesAdapter parent;

        public ViewHolderAbilities(ConstraintLayout v, final AbilityPopupHost host,
                                   final AbilitiesAdapter parent){
            super(v);
            constraintLayout = v;
            this.host = host;
            this.parent = parent;

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView abilityName = (TextView)
                            constraintLayout.findViewById(R.id.textView_card_title);
                    host.showAbilityPopup(parent.getAbility(abilityName.getText().toString()));
                }
            });
        }
    }

}
