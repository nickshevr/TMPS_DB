package com.example.nickshevrov.db_tpms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yourfreetime.db";
    private static final int DATABASE_VERSION = 1;

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TMPS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "VALUE INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}