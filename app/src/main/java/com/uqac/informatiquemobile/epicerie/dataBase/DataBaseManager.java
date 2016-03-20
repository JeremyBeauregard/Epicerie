package com.uqac.informatiquemobile.epicerie.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;

/**
 * Created by paull on 20/03/2016.
 */
public class DataBaseManager {
    private SQLiteDatabase bdd;
    private SQLiteOpenHelper helper;

    public DataBaseManager(Context context){
        helper = new SimpleSQLiteOpenHelper(context);
    }


    public ArrayList<Ingredient> getAll(String table){
        ArrayList<Ingredient> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+table+" order by nom;", null);
        //ContentValues values = new ContentValues();


        while(cursor.moveToNext()){
            retour.add(new Ingredient(cursor.getString(1), cursor.getInt(2)));
        }


        cursor.close();
        db.close();
        return retour;


    }

    public void FixtureIngredients(){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("nom", "patate");
        row.put("prix", 1);
        db.insert("ingredient", null, row);

        row.put("nom", "tomate");
        row.put("prix", 2);
        db.insert("ingredient", null, row);

        row.put("nom", "haricot");
        row.put("prix", 3);
        db.insert("ingredient", null, row);

        db.close();
    }
    public void viderIngredient(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from ingredient");
        db.close();
    }


    public void addIngredient(String nom, int prix){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("nom", nom);
        row.put("prix", prix);
        db.insert("ingredient", null, row);

        db.close();
    }


}
