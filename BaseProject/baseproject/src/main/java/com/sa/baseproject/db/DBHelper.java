package com.sa.baseproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sa on 01/04/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBUser.CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBUser.TABLE_USER);
        onCreate(db);
    }

    public synchronized void close() {
        if (db != null) {
            db.close();
        }
        SQLiteDatabase.releaseMemory();
        super.close();
    }


    SQLiteDatabase getDb() {
        return db;
    }


}
