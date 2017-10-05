package com.ghc2015.womensp2p;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user_post.db";
    public static final String LOG_TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v("tag", "helper inst");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("tag", "oncreate");
        final String SQL_CREATE_SESSION_TABLE =
                "CREATE TABLE " + DatabaseContract.PostEntry.TABLE_NAME + " (" +
                        DatabaseContract.PostEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.PostEntry.COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                        DatabaseContract.PostEntry.COLUMN_LOCATION + " TEXT DEFAULT NULL, " +
                        DatabaseContract.PostEntry.COLUMN_REPORT + " BOOLEAN NOT NULL, " +
                        DatabaseContract.PostEntry.COLUMN_USER + " TEXT NOT NULL, " +
                        DatabaseContract.PostEntry.COLUMN_POST_TEXT + " TEXT NOT NULL, " +
                        DatabaseContract.PostEntry.COLUMN_IMAGE + " BLOB);";

        db.execSQL(SQL_CREATE_SESSION_TABLE);
    }

    /**
     * This will be the SQL necessary to migrate existing data to a new version. Will be
     * triggered when DATABASE_VERSION is greater than the version used to create
     * the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("Tag,", "onupgradecalled");
        switch (oldVersion) {
            // Version 2 adds ViewEntry.COLUMN_UPLOADED_TIME 1-> 2
            case 1:
                // Current version is 1
                break;
        }

    }
}