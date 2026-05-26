package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiana.fe3hguide.model.Paralogue;

import java.util.ArrayList;
import java.util.List;

public class DAOParalogues extends DAO {

    public DAOParalogues(SQLiteDatabase db) {
        super(db);
    }

    public List<Paralogue> getParalogues() {
        Cursor cursor = db.query("Paralogues", null, null, null, null, null, null);
        List<Paralogue> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(new Paralogue(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("characters")),
                        cursor.getString(cursor.getColumnIndexOrThrow("routes")),
                        cursor.getString(cursor.getColumnIndexOrThrow("chapterWindow")),
                        cursor.getString(cursor.getColumnIndexOrThrow("rewards")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("part"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
