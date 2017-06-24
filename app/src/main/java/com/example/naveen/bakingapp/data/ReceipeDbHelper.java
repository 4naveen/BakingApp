package com.example.naveen.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.naveen.bakingapp.data.ReceipeContract.ReceipeEntry;

/**
 * Created by Vamsi Smart on 12-09-2016.
 */
public class ReceipeDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "receipe.db";

    public ReceipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + ReceipeContract.ReceipeEntry.TABLE_NAME + " (" +
                ReceipeContract.ReceipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReceipeContract.ReceipeEntry.COLUMN_RECEIPE_ID+ " TEXT  NOT NULL, " +
                ReceipeContract.ReceipeEntry.COLUMN_RECEIPE_NAME + " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ReceipeContract.ReceipeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
