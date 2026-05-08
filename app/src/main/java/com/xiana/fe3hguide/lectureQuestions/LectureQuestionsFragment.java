package com.xiana.fe3hguide.lectureQuestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.LectureQuestionsAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.LectureQuestion;

import java.util.ArrayList;
import java.util.List;

public class LectureQuestionsFragment extends Fragment {

    private final Facade fc;
    private List<LectureQuestion> allQuestions;
    private LectureQuestionsAdapter adapter;
    private String phaseFilter = "all";

    public LectureQuestionsFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lecture_questions, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.nav_lecture_questions));

        allQuestions = fc.getLectureQuestions();

        Button btnAll = layout.findViewById(R.id.btnLQAll);
        Button btnPre = layout.findViewById(R.id.btnLQPre);
        Button btnPost = layout.findViewById(R.id.btnLQPost);
        ListView listView = layout.findViewById(R.id.listView_lecture_questions);

        adapter = new LectureQuestionsAdapter(getContext(), new ArrayList<>(allQuestions));
        listView.setAdapter(adapter);

        btnAll.setOnClickListener(v -> { phaseFilter = "all"; applyFilter(); });
        btnPre.setOnClickListener(v -> { phaseFilter = "pre"; applyFilter(); });
        btnPost.setOnClickListener(v -> { phaseFilter = "post"; applyFilter(); });

        return layout;
    }

    private void applyFilter() {
        List<LectureQuestion> filtered = new ArrayList<>();
        for (LectureQuestion q : allQuestions) {
            if ("all".equals(phaseFilter) || phaseFilter.equals(q.getPhase())) {
                filtered.add(q);
            }
        }
        adapter.clear();
        adapter.addAll(filtered);
        adapter.notifyDataSetChanged();
    }
}
