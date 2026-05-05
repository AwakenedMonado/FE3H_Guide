package com.xiana.fe3hguide.battalions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.BattalionAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Battalion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BattalionsFragment extends Fragment {

    private Facade fc;
    private List<Battalion> battalions;
    private ListView listView;
    private SearchView searchView;
    private Spinner sortSpinner;

    private Button allButton;
    private Button eButton;
    private Button dButton;
    private Button cButton;
    private Button bButton;
    private Button aButton;

    private String selectedFilter = "all";
    private String searchFilter = "";
    private int sortSelection = 0;

    public BattalionsFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_battalions, container, false);

        battalions = fc.getBattalions();

        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    private void initComponents(LinearLayout layout) {
        listView = layout.findViewById(R.id.listView_battalions);
        searchView = layout.findViewById(R.id.battalionsSearchView);
        sortSpinner = layout.findViewById(R.id.battalionsSortSpinner);
        allButton = layout.findViewById(R.id.battalionsAllButton);
        eButton = layout.findViewById(R.id.battalionsEButton);
        dButton = layout.findViewById(R.id.battalionsDButton);
        cButton = layout.findViewById(R.id.battalionsCButton);
        bButton = layout.findViewById(R.id.battalionsBButton);
        aButton = layout.findViewById(R.id.battalionsAButton);
    }

    private void setupComponents() {
        BattalionAdapter adapter = new BattalionAdapter(getActivity(), 0, battalions);
        listView.setAdapter(adapter);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.nav_battalions));

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.battalion_sort_options, R.layout.item_spinner);
        sortAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        sortSpinner.setAdapter(sortAdapter);
    }

    private void addListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Battalion selected = (Battalion) listView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), BattalionDetailActivity.class);
                intent.putExtra("battalionName", selected.getName());
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter = newText.toLowerCase();
                filterAndSortList();
                return false;
            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortSelection = position;
                filterAndSortList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                searchView.clearFocus();
                selectedFilter = "all";
                filterAndSortList();
            }
        });

        eButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilter = "e";
                filterAndSortList();
            }
        });

        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilter = "d";
                filterAndSortList();
            }
        });

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilter = "c";
                filterAndSortList();
            }
        });

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilter = "b";
                filterAndSortList();
            }
        });

        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilter = "a";
                filterAndSortList();
            }
        });
    }

    private void filterAndSortList() {
        List<Battalion> filtered = new ArrayList<>();
        for (Battalion battalion : battalions) {
            if (searchFilter.isEmpty() || battalion.getName().toLowerCase().contains(searchFilter)) {
                if (selectedFilter.equals("all") ||
                        battalion.getAuthorityLevel().toLowerCase().equals(selectedFilter)) {
                    filtered.add(battalion);
                }
            }
        }

        switch (sortSelection) {
            case 1: // Authority Level: A → E
                Collections.sort(filtered, new Comparator<Battalion>() {
                    @Override
                    public int compare(Battalion a, Battalion b) {
                        return authorityOrder(b.getAuthorityLevel()) - authorityOrder(a.getAuthorityLevel());
                    }
                });
                break;
            case 2: // Endurance: high → low
                Collections.sort(filtered, new Comparator<Battalion>() {
                    @Override
                    public int compare(Battalion a, Battalion b) {
                        return parseStatInt(b.getEndurance()) - parseStatInt(a.getEndurance());
                    }
                });
                break;
            case 3: // Gambit Might: high → low
                Collections.sort(filtered, new Comparator<Battalion>() {
                    @Override
                    public int compare(Battalion a, Battalion b) {
                        return parseStatInt(b.getGambitMt()) - parseStatInt(a.getGambitMt());
                    }
                });
                break;
            default: // Alphabetical
                Collections.sort(filtered, new Comparator<Battalion>() {
                    @Override
                    public int compare(Battalion a, Battalion b) {
                        return a.getName().compareTo(b.getName());
                    }
                });
                break;
        }

        BattalionAdapter adapter = new BattalionAdapter(getContext(), 0, filtered);
        listView.setAdapter(adapter);
    }

    private int authorityOrder(String level) {
        if (level == null) return 0;
        switch (level.toUpperCase()) {
            case "E": return 1;
            case "D": return 2;
            case "C": return 3;
            case "B": return 4;
            case "A": return 5;
            default: return 0;
        }
    }

    private int parseStatInt(String val) {
        if (val == null) return -1;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return -1; }
    }
}
