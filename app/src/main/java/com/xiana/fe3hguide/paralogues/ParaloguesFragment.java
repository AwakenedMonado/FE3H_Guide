package com.xiana.fe3hguide.paralogues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.ParalogueAdapter;
import com.xiana.fe3hguide.battalions.BattalionsFragment;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Battalion;
import com.xiana.fe3hguide.model.Paralogue;
import com.xiana.fe3hguide.model.Weapon;
import com.xiana.fe3hguide.weapons.WeaponsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParaloguesFragment extends Fragment
        implements ParalogueAdapter.OnNavigateListener {

    private static final String[] ROUTE_KEYS = {
            "all", "Azure Moon", "Crimson Flower", "Verdant Wind", "Silver Snow"
    };

    private final Facade fc;
    private List<Paralogue> allParalogues;
    private ParalogueAdapter adapter;
    private int partFilter = 0;
    private String routeFilter = "all";
    private String searchFilter = "";

    public ParaloguesFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_paralogues, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.nav_paralogues));

        allParalogues = fc.getParalogues();

        Set<String> weaponNames = new HashSet<>();
        for (Weapon w : fc.getWeapons()) weaponNames.add(w.getName());
        Set<String> battalionNames = new HashSet<>();
        for (Battalion b : fc.getBattalions()) battalionNames.add(b.getName());

        SearchView searchView = layout.findViewById(R.id.paraSearchView);
        Button btnAll   = layout.findViewById(R.id.btnParaAll);
        Button btnPart1 = layout.findViewById(R.id.btnParaPart1);
        Button btnPart2 = layout.findViewById(R.id.btnParaPart2);
        Spinner routeSpinner = layout.findViewById(R.id.paraRouteSpinner);
        ListView listView = layout.findViewById(R.id.listView_paralogues);

        adapter = new ParalogueAdapter(getContext(), new ArrayList<>(),
                weaponNames, battalionNames, this);
        listView.setAdapter(adapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.paralogue_route_options, R.layout.item_spinner);
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        routeSpinner.setAdapter(spinnerAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                searchFilter = newText.toLowerCase();
                applyFilter();
                return false;
            }
        });

        btnAll.setOnClickListener(v -> { partFilter = 0; applyFilter(); });
        btnPart1.setOnClickListener(v -> { partFilter = 1; applyFilter(); });
        btnPart2.setOnClickListener(v -> { partFilter = 2; applyFilter(); });

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                routeFilter = ROUTE_KEYS[position];
                applyFilter();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        applyFilter();
        return layout;
    }

    @Override
    public void onWeaponSelected(String name) {
        Fragment fragment = new WeaponsFragment(fc, name);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onBattalionSelected(String name) {
        Fragment fragment = new BattalionsFragment(fc, name);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void applyFilter() {
        List<Paralogue> filtered = new ArrayList<>();
        for (Paralogue p : allParalogues) {
            boolean partMatch = partFilter == 0 || p.getPart() == partFilter;
            boolean routeMatch = routeFilter.equals("all")
                    || p.getRoutes().equals("All")
                    || p.getRoutes().contains(routeFilter);
            boolean nameMatch = searchFilter.isEmpty()
                    || p.getName().toLowerCase().contains(searchFilter)
                    || p.getCharacters().toLowerCase().contains(searchFilter);
            if (partMatch && routeMatch && nameMatch) filtered.add(p);
        }
        Collections.sort(filtered, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        adapter.clear();
        adapter.addAll(filtered);
        adapter.notifyDataSetChanged();
    }
}
