package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "android.sqlite";
    private static final int DB_VERSION = 1;
    private DBmanager manager;

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        manager = new DBmanager();
    }

    public void onCreate(SQLiteDatabase db) {
//        manager.CreateDB(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

}
