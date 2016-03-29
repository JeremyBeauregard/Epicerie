package com.uqac.informatiquemobile.epicerie.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by paull on 20/03/2016.
 */
public class DataBaseManager {
    private SQLiteDatabase bdd;
    private SQLiteOpenHelper helper;

    public DataBaseManager(Context context){
        helper = new SimpleSQLiteOpenHelper(context);
    }


    public ArrayList<Ingredient> getAllIngredient(){
        ArrayList<Ingredient> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient order by nom;", null);
        while(cursor.moveToNext()){
            retour.add(new Ingredient(cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        db.close();
        return retour;
    }

    public ArrayList<Recette> getRecette(int id){
        ArrayList<Recette> retour = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = new String[]{"0","1"};
        Cursor cursor = db.query("recette",columns,"id = ?",new String[]{String.valueOf(id),null,null,null});
        db.query()

        HashMap<Integer, String> mapRecettes = new HashMap<>();

        while(cursor.moveToNext()){
            mapRecettes.put(cursor.getInt(0), cursor.getString(1));
        }









        /*while(cursor.moveToNext()){
            retour.add(new Ingredient(cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        db.close();
        return retour;*/
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
