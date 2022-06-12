package com.example.motivup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final int Version = 1;
    private static final String dbName = "MotivUp";

    public DbHelper(Context context) {
        super(context,dbName,null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS targets (target_id INTEGER PRIMARY KEY AUTOINCREMENT, target_name TEXT, target_type TEXT, last_date TEXT); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS targets");
        onCreate(db);
    }
}
