package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiana.fe3hguide.model.LectureQuestion;

import java.util.ArrayList;
import java.util.List;

public class DAOLectureQuestions extends DAO {

    public DAOLectureQuestions(SQLiteDatabase db) {
        super(db);
    }

    public List<LectureQuestion> getLectureQuestions() {
        Cursor cursor = db.rawQuery(
                "SELECT characterName, question, bestAnswer, phase FROM LectureQuestions",
                new String[]{});

        List<LectureQuestion> questions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                questions.add(new LectureQuestion(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questions;
    }
}
