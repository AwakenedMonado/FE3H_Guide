package com.xiana.fe3hguide.facultytraining;

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
import androidx.fragment.app.Fragment;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.FacultyTrainingAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.FacultyTrainer;

import java.util.ArrayList;
import java.util.List;

public class FacultyTrainingFragment extends Fragment {

    private final Facade fc;
    private List<FacultyTrainer> allTrainers;
    private ListView listView;

    private String skillFilter = null;  // null = all skills
    private String partFilter = "all";  // "all", "part1", "part2"

    public FacultyTrainingFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_faculty_training, container, false);

        allTrainers = fc.getFacultyTrainers();
        listView = layout.findViewById(R.id.listView_faculty_training);

        setupSkillSpinner(layout);
        setupPartButtons(layout);
        applyFilters();

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.nav_faculty_training));

        return layout;
    }

    private void setupSkillSpinner(View layout) {
        Spinner spinner = layout.findViewById(R.id.facultySkillSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.faculty_skill_options, R.layout.item_spinner);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skillFilter = position == 0 ? null : parent.getItemAtPosition(position).toString();
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupPartButtons(View layout) {
        Button btnAll = layout.findViewById(R.id.btnFacultyAll);
        Button btnPart1 = layout.findViewById(R.id.btnFacultyPart1);
        Button btnPart2 = layout.findViewById(R.id.btnFacultyPart2);

        btnAll.setOnClickListener(v -> { partFilter = "all"; applyFilters(); });
        btnPart1.setOnClickListener(v -> { partFilter = "part1"; applyFilters(); });
        btnPart2.setOnClickListener(v -> { partFilter = "part2"; applyFilters(); });
    }

    private void applyFilters() {
        List<FacultyTrainer> filtered = new ArrayList<>();
        for (FacultyTrainer trainer : allTrainers) {
            if ("part1".equals(partFilter) && "None".equals(trainer.getPart1Routes())) continue;
            if ("part2".equals(partFilter) && "None".equals(trainer.getPart2Routes())) continue;
            if (skillFilter != null && !hasSkill(trainer, skillFilter)) continue;
            filtered.add(trainer);
        }
        listView.setAdapter(new FacultyTrainingAdapter(getActivity(), filtered));
    }

    private boolean hasSkill(FacultyTrainer trainer, String skill) {
        for (String s : trainer.getSkills().split("\\|")) {
            if (s.trim().equals(skill)) return true;
        }
        return false;
    }
}
