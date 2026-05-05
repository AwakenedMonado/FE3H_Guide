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

public class BlueLionsActivity extends AppCompatActivity {

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

        // Create an ArrayList with the names of the Blue Lions members
        ArrayList<String> blueLionsNames = createBlueLionsNames();
        // Create an ArrayList with their pictures
        ArrayList<Integer> blueLionsImages = createBlueLionsImages();
        adapter = new FactionsCardsAdapter(blueLionsNames, blueLionsImages, this);

        factionsRecycler = (RecyclerView) findViewById(R.id.characters_factions_recycler);
    }

    private void setupComponents(){
        // Set "Blue Lions characters" as the text in the toolbar
        toolbar.setTitle("Blue Lions characters");
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.faction_blue_lions));
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

    private ArrayList<String> createBlueLionsNames(){
        // For simplicity and speed purposes, the names are hardcoded
        ArrayList<String> blueLionsNames = new ArrayList<>();
        blueLionsNames.add("Dimitri");
        blueLionsNames.add("Dedue");
        blueLionsNames.add("Felix");
        blueLionsNames.add("Sylvain");
        blueLionsNames.add("Ingrid");
        blueLionsNames.add("Ashe");
        blueLionsNames.add("Annette");
        blueLionsNames.add("Mercedes");
        return blueLionsNames;
    }

    private ArrayList<Integer> createBlueLionsImages(){
        ArrayList<Integer> blueLionsImages = new ArrayList<>();
        String pkg = getPackageName();
        blueLionsImages.add(getResources().getIdentifier("dimitri", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("dedue", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("felix", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("sylvain", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("ingrid", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("ashe", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("annette", "drawable", pkg));
        blueLionsImages.add(getResources().getIdentifier("mercedes", "drawable", pkg));
        return blueLionsImages;
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