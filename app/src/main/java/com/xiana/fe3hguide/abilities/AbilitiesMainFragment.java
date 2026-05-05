package com.xiana.fe3hguide.abilities;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.AbilitiesAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Ability;

import java.util.List;

public class AbilitiesMainFragment extends Fragment implements AbilityPopupHost {

    private final Facade fc;

    private Spinner spinner;
    private RecyclerView recyclerView;

    private Dialog dialog;
    private TextView dialogName;
    private ImageView dialogIcon;
    private TextView dialogEffect;
    private TextView dialogOrigin;

    public AbilitiesMainFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_abilities_main, container, false);

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_ability);

        initComponents(layout);
        setupSpinner();
        loadAbilities(fc.getAllAbilities());

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.nav_abilities));

        return layout;
    }

    private void initComponents(LinearLayout layout) {
        spinner = layout.findViewById(R.id.spinner_abilities_main);
        recyclerView = layout.findViewById(R.id.recycler_abilities_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dialogName = dialog.findViewById(R.id.textView_title_ability_name);
        dialogIcon = dialog.findViewById(R.id.ability_icon);
        dialogEffect = dialog.findViewById(R.id.textView_ability_effect);
        dialogOrigin = dialog.findViewById(R.id.textview_ability_origin);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.abilities_types, R.layout.item_spinner);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Ability> abilities;
                switch (position) {
                    case 1: abilities = fc.getSkillLevelAbilities(); break;
                    case 2: abilities = fc.getClassAbilities(); break;
                    case 3: abilities = fc.getClassMasteryAbilities(); break;
                    case 4: abilities = fc.getOtherAbilities(); break;
                    default: abilities = fc.getAllAbilities(); break;
                }
                loadAbilities(abilities);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadAbilities(List<Ability> abilities) {
        recyclerView.setAdapter(new AbilitiesAdapter(abilities, this));
    }

    @Override
    public void showAbilityPopup(Ability ability) {
        dialogName.setText(ability.getName());
        dialogIcon.setImageResource(ability.getIcon());
        dialogEffect.setText(ability.getEffect());
        dialogOrigin.setText(ability.getOrigin());
        dialog.show();
    }

}
