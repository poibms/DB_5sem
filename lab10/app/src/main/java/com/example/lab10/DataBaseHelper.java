package com.example.lab10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String S_TABLE_NAME = "Students";
    public static final String G_TABLE_NAME = "Groups";
    public static final String ID_GROUP = "_id";
    public static final String FACULTY = "FACULTY";
    public static final String COURSE = "COURSE";
    public static final String NAME = "NAME";
    public static final String HEAD = "HEAD";
    public static final String ID_STUDENT = "ID_STUDENT";
    private static final String DATABASE_NAME = "DB_10";
    private static final int SCHEMA = 1;



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Groups (_id INTEGER PRIMARY KEY , FACULTY TEXT, COURSE TEXT, NAME TEXT, HEAD TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Students (_id INTEGER, ID_STUDENT INTEGER, NAME TEXT, FOREIGN KEY(_id) REFERENCES Groups(_id) on delete cascade on update cascade);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
