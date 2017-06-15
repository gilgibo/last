package com.example.gibo.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gi bo on 2017-06-15.
 */

public class DB{
    private static final String DATABASE_NAME = "StoryDB";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase db;
    private DatabaseHelper DBHelper;
    private Context Ctx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sql = "Create table if not exists questdata (" +
                    "id text primary key," +
                    "quest text not null);" ;
            try
            {
                db.execSQL(sql);
            }
            catch (SQLiteException e)
            {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("Drop table if exists questdata");
            onCreate(db);
        }
    }

    public DB(Context context) {
        this.Ctx = context;
    }

    public DB open() throws SQLException
    {
        DBHelper = new DatabaseHelper(Ctx,DATABASE_NAME,null,DATABASE_VERSION);
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        db.close();
    }

    public void insert(String id, String quest)
    {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("quest", quest);
        db.insert("questdata",null,values);
    }

}
