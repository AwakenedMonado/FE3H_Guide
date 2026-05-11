package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiana.fe3hguide.model.Weapon;

import java.util.ArrayList;
import java.util.List;

public class DAOWeapons extends DAO {

    public DAOWeapons(SQLiteDatabase db) {
        super(db);
    }

    public List<Weapon> getWeapons() {
        Cursor cursor = db.rawQuery(
                "SELECT name, type, lvl, mt, hit, crit, rng, wt, uses, effect FROM Weapons",
                new String[]{});

        List<Weapon> weapons = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                weapons.add(new Weapon(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getInt(8),
                        cursor.getString(9)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return weapons;
    }
}
