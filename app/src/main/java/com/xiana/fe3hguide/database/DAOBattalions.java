package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiana.fe3hguide.model.Battalion;
import com.xiana.fe3hguide.model.Gambit;

import java.util.ArrayList;
import java.util.List;

public class DAOBattalions extends DAO {

    public DAOBattalions(SQLiteDatabase db) {
        super(db);
    }

    public List<Battalion> getBattalions() {
        Cursor cursor = db.rawQuery(
                "SELECT b.name, b.authorityLevel, b.endurance, b.prt, b.rsl, b.hit, b.avo, b.cha, " +
                "b.gambit, b.movementType, COALESCE(g.mt, '-') AS gambitMt " +
                "FROM Battalions b LEFT JOIN Gambits g ON b.gambit = g.name",
                new String[]{});

        List<Battalion> battalions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                battalions.add(new Battalion.Builder(cursor.getString(0))
                        .withAuthorityLevel(cursor.getString(1))
                        .withEndurance(cursor.getString(2))
                        .withPrt(cursor.getString(3))
                        .withRsl(cursor.getString(4))
                        .withHit(cursor.getString(5))
                        .withAvo(cursor.getString(6))
                        .withCha(cursor.getString(7))
                        .withGambitName(cursor.getString(8))
                        .withMovementType(cursor.getString(9))
                        .withGambitMt(cursor.getString(10))
                        .build());
            } while (cursor.moveToNext());
        }

        cursor.close();
        return battalions;
    }

    public Battalion getBattalion(String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM Battalions WHERE name = ?",
                new String[]{name});

        Battalion battalion = null;
        if (cursor.moveToFirst()) {
            battalion = new Battalion.Builder(cursor.getString(0))
                    .withAuthorityLevel(cursor.getString(1))
                    .withEndurance(cursor.getString(2))
                    .withPrt(cursor.getString(3))
                    .withRsl(cursor.getString(4))
                    .withHit(cursor.getString(5))
                    .withAvo(cursor.getString(6))
                    .withCha(cursor.getString(7))
                    .withGambitName(cursor.getString(8))
                    .build();
        }

        cursor.close();
        return battalion;
    }

    public Gambit getGambit(String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM Gambits WHERE name = ?",
                new String[]{name});

        Gambit gambit = null;
        if (cursor.moveToFirst()) {
            gambit = new Gambit.Builder(cursor.getString(0))
                    .withType(cursor.getString(1))
                    .withMt(cursor.getString(2))
                    .withHit(cursor.getString(3))
                    .withRange(cursor.getString(4))
                    .withDescription(cursor.getString(5))
                    .withFormationImage(cursor.getString(6))
                    .build();
        }

        cursor.close();
        return gambit;
    }
}
