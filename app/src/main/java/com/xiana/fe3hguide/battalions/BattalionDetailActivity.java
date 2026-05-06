package com.xiana.fe3hguide.battalions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Battalion;
import com.xiana.fe3hguide.model.Gambit;

public class BattalionDetailActivity extends AppCompatActivity {

    // Set to false to revert to the popup link
    private static final boolean INLINE_GAMBIT = true;

    private Battalion battalion;
    private Facade fc;
    private Toolbar toolbar;

    private TextView name;
    private TextView authorityLevel;
    private TextView endurance;
    private TextView str;
    private TextView mag;
    private TextView hit;
    private TextView crit;
    private TextView avo;
    private TextView prt;
    private TextView rsl;
    private TextView cha;
    private TextView gambitName;
    private ImageView movementImage;
    private TextView movementText;

    // Inline gambit views
    private CardView inlineGambitCard;
    private ImageView inlineGambitTypeIcon;
    private TextView inlineGambitName;
    private TextView inlineGambitType;
    private TextView inlineGambitMt;
    private TextView inlineGambitHit;
    private TextView inlineGambitRange;
    private TextView inlineGambitDescription;
    private ImageView inlineGambitFormation;
    private LinearLayout gambitLinkSection;

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
        str = findViewById(R.id.textView_battalion_detail_str);
        mag = findViewById(R.id.textView_battalion_detail_mag);
        hit = findViewById(R.id.textView_battalion_detail_hit);
        crit = findViewById(R.id.textView_battalion_detail_crit);
        avo = findViewById(R.id.textView_battalion_detail_avo);
        prt = findViewById(R.id.textView_battalion_detail_prt);
        rsl = findViewById(R.id.textView_battalion_detail_rsl);
        cha = findViewById(R.id.textView_battalion_detail_cha);
        gambitName = findViewById(R.id.textView_battalion_detail_gambit);
        movementImage = findViewById(R.id.imageView_battalion_detail_movement);
        movementText = findViewById(R.id.textView_battalion_detail_movement);

        inlineGambitCard = findViewById(R.id.card_inline_gambit);
        inlineGambitTypeIcon = findViewById(R.id.imageView_inline_gambit_type);
        inlineGambitName = findViewById(R.id.textView_inline_gambit_name);
        inlineGambitType = findViewById(R.id.textView_inline_gambit_type);
        inlineGambitMt = findViewById(R.id.textView_inline_gambit_mt);
        inlineGambitHit = findViewById(R.id.textView_inline_gambit_hit);
        inlineGambitRange = findViewById(R.id.textView_inline_gambit_range);
        inlineGambitDescription = findViewById(R.id.textView_inline_gambit_description);
        inlineGambitFormation = findViewById(R.id.imageView_inline_gambit_formation);
        gambitLinkSection = findViewById(R.id.layout_gambit_link);

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
        str.setText(battalion.getStr());
        mag.setText(battalion.getMag());
        hit.setText(battalion.getHit());
        crit.setText(battalion.getCrit());
        avo.setText(battalion.getAvo());
        prt.setText(battalion.getPrt());
        rsl.setText(battalion.getRsl());
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

        if (INLINE_GAMBIT) {
            gambitLinkSection.setVisibility(View.GONE);
            Gambit gambit = fc.getGambit(battalion.getGambitName());
            if (gambit != null) {
                inlineGambitName.setText(gambit.getName());
                inlineGambitType.setText(gambit.getType());
                inlineGambitMt.setText(gambit.getMt());
                inlineGambitHit.setText(gambit.getHit());
                inlineGambitRange.setText(gambit.getRange());
                inlineGambitDescription.setText(gambit.getDescription());
                int typeResId = getResources().getIdentifier(
                        gambit.getType().toLowerCase(), "drawable", getPackageName());
                if (typeResId != 0) inlineGambitTypeIcon.setImageResource(typeResId);
                int formationResId = getResources().getIdentifier(
                        gambit.getFormationImage(), "drawable", getPackageName());
                if (formationResId != 0) inlineGambitFormation.setImageResource(formationResId);
            }
        } else {
            inlineGambitCard.setVisibility(View.GONE);
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
