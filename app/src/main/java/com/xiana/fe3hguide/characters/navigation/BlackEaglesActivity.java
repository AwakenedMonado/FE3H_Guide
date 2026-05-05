package com.xiana.fe3hguide.characters.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.core.content.ContextCompat;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.FactionsCardsAdapter;

import java.util.ArrayList;

public class BlackEaglesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FactionsCardsAdapter adapter;
    private RecyclerView factionsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_navigation);

        initComponents();
        setupComponents();
    }

    private void initComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Create an ArrayList with the names of the Black Eagles members
        ArrayList<String> blackEaglesNames = createBlackEaglesNames();
        // Create an ArrayList with their pictures
        ArrayList<Integer> blackEaglesImages = createBlackEaglesImages();
        adapter = new FactionsCardsAdapter(blackEaglesNames, blackEaglesImages, this);

        factionsRecycler = (RecyclerView) findViewById(R.id.characters_factions_recycler);
    }

    private void setupComponents(){
        // Set "Black Eagles characters" as the text in the toolbar
        toolbar.setTitle("Black Eagles characters");
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.faction_black_eagles));
        setSupportActionBar(toolbar);

        // Add back arrow to the toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set adapter for the recycler view
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 4x2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createBlackEaglesNames(){
        // For simplicity and speed purposes, the names are hardcoded
        ArrayList<String> blackEaglesNames = new ArrayList<>();
        blackEaglesNames.add("Edelgard");
        blackEaglesNames.add("Hubert");
        blackEaglesNames.add("Ferdinand");
        blackEaglesNames.add("Bernadetta");
        blackEaglesNames.add("Linhardt");
        blackEaglesNames.add("Caspar");
        blackEaglesNames.add("Dorothea");
        blackEaglesNames.add("Petra");
        return blackEaglesNames;
    }

    private ArrayList<Integer> createBlackEaglesImages(){
        ArrayList<Integer> blackEaglesImages = new ArrayList<>();
        String pkg = getPackageName();
        blackEaglesImages.add(getResources().getIdentifier("edelgard", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("hubert", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("ferdinand", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("bernadetta", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("linhardt", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("caspar", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("dorothea", "drawable", pkg));
        blackEaglesImages.add(getResources().getIdentifier("petra", "drawable", pkg));
        return blackEaglesImages;
    }

    @Override
    // Back arrow on the toolbar returns to previous activity (if there is any)
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}