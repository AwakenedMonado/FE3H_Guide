package com.xiana.fe3hguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.xiana.fe3hguide.abilities.AbilitiesMainFragment;
import com.xiana.fe3hguide.battalions.BattalionsFragment;
import com.xiana.fe3hguide.characters.navigation.CharactersFragment;
import com.xiana.fe3hguide.facultytraining.FacultyTrainingFragment;
import com.xiana.fe3hguide.lectureQuestions.LectureQuestionsFragment;
import com.xiana.fe3hguide.classes.ClassesFragment;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.supports.SupportsFragment;
import com.xiana.fe3hguide.teaTime.TeaTimeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Facade fc;
    public static Toolbar toolbar;
    private DrawerLayout drawer;
    public static NavigationView navigationView;

    private SharedPreferences sharedPreferences;
    private boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDBConnection();

        initComponents();
        setupComponents();
        addListeners();
        manageDarkMode();

        displayDefaultFragment();
    }

    private void setupDBConnection() {
        fc = Facade.getInstance(this);
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setupComponents() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_lines_white);
    }

    private void addListeners() {
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.i("MainActivity", "slideOffset=" + slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Fragment current = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (current instanceof DropdownDismissible) {
                    ((DropdownDismissible) current).dismissDropdowns();
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Log.i("MainActivity", "Drawer closed");
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_calculator) {
            fragment = new CalculatorFragment();
        } else if (id == R.id.nav_classes) {
            fragment = new ClassesFragment(fc);
        } else if (id == R.id.nav_tea_time) {
            fragment = new TeaTimeFragment(fc);
        } else if (id == R.id.nav_supports) {
            fragment = new SupportsFragment(fc);
        } else if (id == R.id.nav_battalions) {
            fragment = new BattalionsFragment(fc);
        } else if (id == R.id.nav_abilities) {
            fragment = new AbilitiesMainFragment(fc);
        } else if (id == R.id.nav_faculty_training) {
            fragment = new FacultyTrainingFragment(fc);
        } else if (id == R.id.nav_lecture_questions) {
            fragment = new LectureQuestionsFragment(fc);
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
        } else {
            fragment = new CharactersFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void manageDarkMode() {
        sharedPreferences = getSharedPreferences("FireEmblemGuideSharedPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            toolbar.setBackgroundColor(getResources().getColor(R.color.elevation1));
            navigationView.setBackgroundColor(getResources().getColor(R.color.elevation1));
            navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_gray)));
            setTextColorForMenuItem(navigationView.getMenu().getItem(9), R.color.white);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            navigationView.setBackgroundColor(getResources().getColor(R.color.mainBackground));
            navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.mainText)));
            navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.mainText)));
            setTextColorForMenuItem(navigationView.getMenu().getItem(9), R.color.mainText);
        }
    }

    private void setTextColorForMenuItem(MenuItem menuItem, int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)),
                0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }

    public void displayDefaultFragment() {
        int defaultFragment = sharedPreferences.getInt("DefaultTab", 0);
        Fragment fragment = null;

        if (defaultFragment == 1) {
            fragment = new CalculatorFragment();
        } else if (defaultFragment == 2) {
            fragment = new ClassesFragment(fc);
        } else if (defaultFragment == 3) {
            fragment = new TeaTimeFragment(fc);
        } else if (defaultFragment == 4) {
            fragment = new SupportsFragment(fc);
        } else if (defaultFragment == 5) {
            fragment = new BattalionsFragment(fc);
        } else if (defaultFragment == 6) {
            fragment = new AbilitiesMainFragment(fc);
        } else if (defaultFragment == 7) {
            fragment = new FacultyTrainingFragment(fc);
        } else if (defaultFragment == 8) {
            fragment = new LectureQuestionsFragment(fc);
        } else {
            fragment = new CharactersFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
