package com.uqac.informatiquemobile.epicerie.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Nourriture;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paull on 20/03/2016.
 */
public class DataBaseManager {
    private SQLiteDatabase bdd;
    private SQLiteOpenHelper helper;

    public DataBaseManager(Context context){
        helper = new SimpleSQLiteOpenHelper(context);
    }

    /**
     * Methode qui permet de recuperer tous les ingredients de la base de donnees.
     * @return ArrayList d'ingredients.
     */
    public ArrayList<Ingredient> getAllIngredient(){
        ArrayList<Ingredient> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient order by nom;", null);
        while(cursor.moveToNext()){
            retour.add(new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            System.out.println(cursor.getString(1)+ cursor.getInt(2)+cursor.getInt(3));
        }
        cursor.close();
        db.close();
        return retour;
    }

    /**
     * Methode qui permet de recuperer un ingredient dans la base de donnees grace a son nom.
     * @param nom nom de l'ingredient a recuperer.
     * @return Ingredient recupere.
     */
    public Ingredient getIngredientByNm(String nom){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom=\"" + nom + "\";", null);
        Log.d("QUERY", "getIngredientByNm: select * from ingredient where nom=\"" + nom + "\";");
        cursor.moveToFirst();
        if(cursor.getCount()==0){return null;}
        Ingredient retour=new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        cursor.close();
        db.close();
        return retour;
    }
    public int getIngredientIdByNm(String nom){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom=\"" + nom + "\";", null);
        Log.d("QUERY", "getIngredientByNm: select * from ingredient where nom=\"" + nom + "\";");
        cursor.moveToFirst();

        int retour=cursor.getInt(0);
        cursor.close();
        db.close();
        return retour;
    }


    /**
     * Methode qui permet d'ajouter un ingredient dans la base de donnees.
     * @param nom Nom de l'ingredient a ajouter.
     * @param prix Prix de l'ingredient a ajouter.
     */
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

    /**
     * Methode qui permet de supprimer un ingredient.
     * @param nom Nom de l'ingredient a supprimer.
     */
    public void supprimerIngredient(String nom){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom = \""+nom+"\"", null);
        cursor.moveToNext();
        if (cursor.getInt(3)<=1){

            SQLiteDatabase db2 = helper.getWritableDatabase();
            db2.execSQL("delete from ingredient where nom=\"" + nom + "\";");
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

    /**
     * Methode qui permet d'ajouter un ingredient dans la base de donnees.
     */

    public void addRecette(Recette recette){

        ArrayList<Nourriture> composition=recette.getComposition();



        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("nom", recette.getNom());


        db.insert("recette", null, row);

        final String MY_QUERY = "SELECT last_insert_rowid()";
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int idRecette = cur.getInt(0);
        cur.close();

        for(Nourriture nourr : composition) {


            int qte = nourr.getQuantite();

            int idIngredient=getIngredientIdByNm(nourr.getNom());
            SQLiteDatabase dbtemp = helper.getWritableDatabase();
            ContentValues row2 = new ContentValues();

            row2.put("idRecette", idRecette);
            row2.put("idIngredient", idIngredient);
            row2.put("quantite",qte);
            dbtemp.insert("associationRecette", null, row2);
            dbtemp.close();

        }








        db.close();
    }



    public ArrayList<Recette> getAllRecettes() {

        ArrayList<Recette> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recette order by nom;", null);
        while(cursor.moveToNext()){


            Recette temp =new Recette(cursor.getString(1),new ArrayList<Nourriture>(),cursor.getInt(0) );
            Cursor cursor2 = db.rawQuery("select idIngredient, quantite from associationRecette where idRecette=" + cursor.getInt(0) + ";", null);
            if(cursor2 !=null && cursor2.moveToFirst()){
                while(cursor2.moveToNext()) {

                    int idIng = cursor2.getInt(0);
                    int qte = cursor2.getInt(1);


                    Cursor cursor3 = db.rawQuery("select nom, prix from ingredient where id=" + idIng + ";", null);
                    cursor3.moveToFirst();
                    String nomIng = cursor3.getString(0);
                    int prixIng = cursor3.getInt(1);
                    cursor3.close();

                    Ingredient tempIng=new Ingredient(nomIng,prixIng,qte);
                    temp.addItem(tempIng);

                }
                cursor2.close();
        }
                retour.add(temp);
        }
        cursor.close();
        db.close();
        return retour;
    }


    public Recette getRecetteById(int id){

        Recette temp= new Recette("temp",new ArrayList<Nourriture>());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recette where id="+id+";", null);
        cursor.moveToFirst();
        if(cursor !=null /*&& cursor.moveToFirst()*/){
            //cursor.move(-1);
            temp =new Recette(""+cursor.getCount()+""+cursor.getString(1) /*"test"cursor.getString(1)*/,new ArrayList<Nourriture>()/*, cursor.getInt(0)*/ );
            Cursor cursor2 = db.rawQuery("select idIngredient, quantite from associationRecette where idRecette=" + id + ";", null);
            if(cursor2 !=null && cursor2.moveToFirst()){
                cursor2.move(-1);
                while(cursor2.moveToNext()) {

                    int idIng = cursor2.getInt(0);
                    int qte = cursor2.getInt(1);


                    Cursor cursor3 = db.rawQuery("select nom, prix from ingredient where id=" + idIng + ";", null);
                    cursor3.moveToFirst();
                    String nomIng = cursor3.getString(0);
                    int prixIng = cursor3.getInt(1);
                    cursor3.close();

                    Ingredient tempIng=new Ingredient(nomIng,prixIng,qte);
                    temp.addItem(tempIng);

                }

            }

            cursor2.close();
        }


        cursor.close();
        db.close();
        return temp;
    }

    public void DeleteRecette(int id){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("DELETE from recette Where id="+id+";", null);
        db.delete("recette", "id" + "=" + id, null) ;

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


/*

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
*/


}
