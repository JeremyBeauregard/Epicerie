package com.uqac.informatiquemobile.epicerie.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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


    public ArrayList<Ingredient> getAllIngredient(){
        ArrayList<Ingredient> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient order by nom;", null);
        while(cursor.moveToNext()){
            retour.add(new Ingredient(cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
        }
        cursor.close();
        db.close();
        return retour;
    }

    public Ingredient getIngredientByNm(String nom){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom=\""+nom+"\";", null);
        Log.d("QUERY", "getIngredientByNm: select * from ingredient where nom=\""+nom+"\";");
        cursor.moveToFirst();
        if(cursor.getCount()==0){return null;}
        Ingredient retour=new Ingredient(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        cursor.close();
        db.close();
        return retour;
    }



    public void addIngredient(String nom, int prix){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom = \""+nom+"\"", null);
        cursor.moveToNext();
        if (cursor.getCount()==0){
            Log.d("cursor", "addIngredient: null");
            SQLiteDatabase db2 = helper.getWritableDatabase();
            ContentValues row = new ContentValues();

            row.put("nom", nom);
            row.put("prix", prix);
            row.put("quantite", 0);
            db2.insert("ingredient", null, row);
        } else {
            Log.d("cursor", "addIngredient: not null");
            //cursor.moveToNext();
            ContentValues row = new ContentValues();
            row.put("nom", nom);
            row.put("prix", prix);
            int u = (cursor.getInt(3)+1);
            row.put("quantite", u);
            Log.d("qtte", "addIngredient: "+u);
            SQLiteDatabase db2 = helper.getWritableDatabase();
            int ok = db2.update("ingredient", row, "nom=\""+nom+"\"",null);
            //Log.d("ok", "addIngredient: "+ok);
            db2.close();

        }



        cursor.close();
        db.close();
    }

    public void supprimerIngredient(String nom){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom = \""+nom+"\"", null);
        cursor.moveToNext();
        if (cursor.getInt(3)<=1){

            SQLiteDatabase db2 = helper.getWritableDatabase();
            db2.execSQL("delete from ingredient where nom=\""+nom+"\";");
        } else {

            //cursor.moveToNext();
            ContentValues row = new ContentValues();
            row.put("nom", nom);
            //row.put("prix", prix);
            int u = (cursor.getInt(3)-1);
            row.put("quantite", u);
            Log.d("qtte", "addIngredient: "+u);
            SQLiteDatabase db2 = helper.getWritableDatabase();
            int ok = db2.update("ingredient", row, "nom=\""+nom+"\"",null);
            //Log.d("ok", "addIngredient: "+ok);
            db2.close();

        }



        cursor.close();
        db.close();



    }









    /*public ArrayList<Recette> getRecette(int id){
        ArrayList<Recette> retour = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = new String[]{"0","1"};
        //Cursor cursor = db.query("recette",columns,"id = ?",new String[]{String.valueOf(id),null,null,null});
        //db.query()

        HashMap<Integer, String> mapRecettes = new HashMap<>();

        while(cursor.moveToNext()){
            mapRecettes.put(cursor.getInt(0), cursor.getString(1));
        }

        *//*while(cursor.moveToNext()){
            retour.add(new Ingredient(cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        db.close();
        return retour;*//*
    }
*/



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


}
