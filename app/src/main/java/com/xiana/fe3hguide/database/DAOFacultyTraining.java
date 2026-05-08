package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiana.fe3hguide.model.FacultyTrainer;

import java.util.ArrayList;
import java.util.List;

public class DAOFacultyTraining extends DAO {

    public DAOFacultyTraining(SQLiteDatabase db) {
        super(db);
    }

    public List<FacultyTrainer> getFacultyTrainers() {
        Cursor cursor = db.rawQuery(
                "SELECT name, skills, part1Routes, part2Routes FROM FacultyTraining",
                new String[]{});

        List<FacultyTrainer> trainers = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                trainers.add(new FacultyTrainer(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return trainers;
    }
}
