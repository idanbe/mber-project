package com.example.administrator.game_4_in_a_row;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AviramAlkobi on 14/02/2016.
 */
public class DBHelper extends SQLiteOpenHelper {


    // values
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppWinEntries.db";


    // constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    // create Table whit 4 row (STRING , INTEGER , INTEGER , INTEGER)
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Tables.winnersTable.TABLE_NAME + " ( " +
                        Tables.winnersTable.NAME + " TEXT ," +
                        Tables.winnersTable.WINS + " INTEGER ," +
                        Tables.winnersTable.LOSSES + " INTEGER ," +
                        Tables.winnersTable.STAND_OFF + " INTEGER ," +
                        Tables.winnersTable.PERCENT_OF_WINS + " TEXT" + ");"
        );
    }

    // drop table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop table
        db.execSQL("DROP TABLE IF EXIST " + Tables.winnersTable.TABLE_NAME);
        onCreate(db);
    }
}

