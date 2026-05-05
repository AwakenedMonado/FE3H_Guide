package com.xiana.fe3hguide.battalions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Battalion;
import com.xiana.fe3hguide.model.Gambit;

public class BattalionDetailActivity extends AppCompatActivity {

    private Battalion battalion;
    private Facade fc;
    private Toolbar toolbar;

    private TextView name;
    private TextView authorityLevel;
    private TextView endurance;
    private TextView prt;
    private TextView rsl;
    private TextView hit;
    private TextView avo;
    private TextView cha;
    private TextView gambitName;
    private ImageView movementImage;
    private TextView movementText;

    private Dialog gambitDialog;
    private TextView dialogGambitName;
    private ImageView dialogGambitTypeIcon;
    private TextView dialogGambitType;
    private TextView dialogGambitMt;
    private TextView dialogGambitHit;
    private TextView dialogGambitRange;
    private TextView dialogGambitDescription;
    private ImageView dialogGambitFormation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battalion_detail);

        fc = Facade.getInstance(this);
        String battalionName = getIntent().getStringExtra("battalionName");
        battalion = fc.getBattalion(battalionName);

        gambitDialog = new Dialog(this);
        gambitDialog.setContentView(R.layout.popup_gambit);

        initComponents();
        setData();
        setupToolbar();
    }

    private void initComponents() {
        toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.textView_battalion_detail_name);
        authorityLevel = findViewById(R.id.textView_battalion_detail_authority);
        endurance = findViewById(R.id.textView_battalion_detail_endurance);
        prt = findViewById(R.id.textView_battalion_detail_prt);
        rsl = findViewById(R.id.textView_battalion_detail_rsl);
        hit = findViewById(R.id.textView_battalion_detail_hit);
        avo = findViewById(R.id.textView_battalion_detail_avo);
        cha = findViewById(R.id.textView_battalion_detail_cha);
        gambitName = findViewById(R.id.textView_battalion_detail_gambit);
        movementImage = findViewById(R.id.imageView_battalion_detail_movement);
        movementText = findViewById(R.id.textView_battalion_detail_movement);

        dialogGambitName = gambitDialog.findViewById(R.id.textView_gambit_name);
        dialogGambitTypeIcon = gambitDialog.findViewById(R.id.imageView_gambit_type);
        dialogGambitType = gambitDialog.findViewById(R.id.textView_gambit_type);
        dialogGambitMt = gambitDialog.findViewById(R.id.textView_gambit_mt);
        dialogGambitHit = gambitDialog.findViewById(R.id.textView_gambit_hit);
        dialogGambitRange = gambitDialog.findViewById(R.id.textView_gambit_range);
        dialogGambitDescription = gambitDialog.findViewById(R.id.textView_gambit_description);
        dialogGambitFormation = gambitDialog.findViewById(R.id.imageView_gambit_formation);
    }

    private void setData() {
        name.setText(battalion.getName());
        authorityLevel.setText(battalion.getAuthorityLevel());
        endurance.setText(battalion.getEndurance());
        prt.setText(battalion.getPrt());
        rsl.setText(battalion.getRsl());
        hit.setText(battalion.getHit());
        avo.setText(battalion.getAvo());
        cha.setText(battalion.getCha());

        String movementType = battalion.getMovementType();
        if (movementType != null && !movementType.isEmpty()) {
            movementText.setText(movementType);
            int resId = getResources().getIdentifier(
                    movementType.toLowerCase(), "drawable", getPackageName());
            if (resId != 0) {
                movementImage.setImageResource(resId);
                movementImage.setVisibility(View.VISIBLE);
            } else {
                movementImage.setVisibility(View.GONE);
            }
        }

        SpannableString underlined = new SpannableString(battalion.getGambitName());
        underlined.setSpan(new UnderlineSpan(), 0, underlined.length(), 0);
        gambitName.setText(underlined);
        gambitName.setTextColor(getResources().getColor(R.color.colorPrimary));

        gambitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGambitPopup(battalion.getGambitName());
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.nav_battalions));
        }
    }

    private void showGambitPopup(String gambitNameStr) {
        Gambit gambit = fc.getGambit(gambitNameStr);
        if (gambit == null) return;

        dialogGambitName.setText(gambit.getName());
        dialogGambitType.setText(gambit.getType());
        dialogGambitMt.setText(gambit.getMt());
        dialogGambitHit.setText(gambit.getHit());
        dialogGambitRange.setText(gambit.getRange());
        dialogGambitDescription.setText(gambit.getDescription());

        int typeResId = getResources().getIdentifier(
                gambit.getType().toLowerCase(), "drawable", getPackageName());
        if (typeResId != 0) {
            dialogGambitTypeIcon.setImageResource(typeResId);
        }

        int formationResId = getResources().getIdentifier(
                gambit.getFormationImage(), "drawable", getPackageName());
        if (formationResId != 0) {
            dialogGambitFormation.setImageResource(formationResId);
        }

        gambitDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (gambitDialog.isShowing()) {
            gambitDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
