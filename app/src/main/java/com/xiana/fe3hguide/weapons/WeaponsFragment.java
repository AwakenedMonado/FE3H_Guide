package com.xiana.fe3hguide.weapons;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.WeaponAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponsFragment extends Fragment {

    private Facade fc;
    private List<Weapon> allWeapons;
    private ListView listView;
    private SearchView searchView;
    private Spinner typeSpinner;
    private Spinner sortSpinner;

    private String typeFilter = "all";
    private String searchFilter = "";
    private int sortSelection = 0;

    public WeaponsFragment(Facade fc) {
        this.fc = fc;
    }

    public WeaponsFragment(Facade fc, String initialSearch) {
        this.fc = fc;
        if (initialSearch != null) {
            this.searchFilter = initialSearch.toLowerCase().trim();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_weapons, container, false);

        allWeapons = fc.getWeapons();

        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    private void initComponents(LinearLayout layout) {
        listView = layout.findViewById(R.id.listView_weapons);
        searchView = layout.findViewById(R.id.weaponsSearchView);
        typeSpinner = layout.findViewById(R.id.weaponsTypeSpinner);
        sortSpinner = layout.findViewById(R.id.weaponsSortSpinner);
    }

    private void setupComponents() {
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.nav_weapons));

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.weapon_type_options, R.layout.item_spinner);
        typeAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.weapon_sort_options, R.layout.item_spinner);
        sortAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        sortSpinner.setAdapter(sortAdapter);

        applyFilterAndSort();
        if (!searchFilter.isEmpty()) {
            searchView.setQuery(searchFilter, false);
            searchView.clearFocus();
        }
    }

    private void addListeners() {
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] options = getResources().getStringArray(R.array.weapon_type_options);
                String selected = options[position];
                typeFilter = selected.equals("All Types") ? "all" : selected.toLowerCase();
                applyFilterAndSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortSelection = position;
                applyFilterAndSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter = newText.toLowerCase().trim();
                applyFilterAndSort();
                return false;
            }
        });
    }

    private void applyFilterAndSort() {
        List<Weapon> filtered = new ArrayList<>();
        for (Weapon w : allWeapons) {
            boolean typeMatch = typeFilter.equals("all") || w.getType().equalsIgnoreCase(typeFilter);
            boolean nameMatch = searchFilter.isEmpty() || w.getName().toLowerCase().contains(searchFilter);
            if (typeMatch && nameMatch) {
                filtered.add(w);
            }
        }

        switch (sortSelection) {
            case 1: // Mt: High → Low
                Collections.sort(filtered, (a, b) -> b.getMt() - a.getMt());
                break;
            case 2: // Hit: High → Low
                Collections.sort(filtered, (a, b) -> b.getHit() - a.getHit());
                break;
            case 3: // Crit: High → Low
                Collections.sort(filtered, (a, b) -> b.getCrit() - a.getCrit());
                break;
            case 4: // Wt: Low → High
                Collections.sort(filtered, (a, b) -> a.getWt() - b.getWt());
                break;
            case 5: // Uses: High → Low (broken weapons last)
                Collections.sort(filtered, (a, b) -> {
                    int ua = a.getUses() == 0 ? -1 : a.getUses();
                    int ub = b.getUses() == 0 ? -1 : b.getUses();
                    return ub - ua;
                });
                break;
            default: // Alphabetical
                Collections.sort(filtered, (a, b) -> a.getName().compareTo(b.getName()));
                break;
        }

        listView.setAdapter(new WeaponAdapter(getContext(), 0, filtered));
    }
}
