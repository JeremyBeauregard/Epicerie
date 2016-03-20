package com.uqac.informatiquemobile.epicerie.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paull on 20/03/2016.
 */
public class SimpleSQLiteOpenHelper extends SQLiteOpenHelper{

    public SimpleSQLiteOpenHelper(Context context){
        super(context, "main.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ingredient (id integer primary key autoincrement, nom text, prix integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
