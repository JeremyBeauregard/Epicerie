package com.uqac.informatiquemobile.epicerie.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Nourriture;
import com.uqac.informatiquemobile.epicerie.metier.Recette;
import com.uqac.informatiquemobile.epicerie.metier.Repas;
import com.uqac.informatiquemobile.epicerie.metier.Unite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
            retour.add(new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2)));

        }
        cursor.close();
        db.close();
        return retour;
    }
    public ArrayList<Ingredient> getAllIngredientFrigo(){
        ArrayList<Ingredient> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient inner join frigo on frigo.idIngredient=ingredient.id order by nom;", null);
        while(cursor.moveToNext()){
            Unite unite=getUniteById(cursor.getInt(3));


            retour.add(new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getFloat(5),unite));
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
        Unite unite=getUniteById(cursor.getInt(3));

        Ingredient retour=new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), unite);
        cursor.close();
        db.close();
        return retour;
    }

    public Ingredient getIngredientFrigoById(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient inner join frigo on frigo.idIngredient=ingredient.id  where idIngredient=" + id + ";", null);
        Log.d("QUERY", "getIngredientByNm: select * from frigo where id=" + id + ";");
        cursor.moveToFirst();
        if(cursor.getCount()==0){return null;}
        Ingredient retour=new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getFloat(5));
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
     * @param ingredient ingredient à ajouter.
     */
    public boolean addIngredient(Ingredient ingredient){



        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient where nom = \""+ingredient.getNom()+"\"", null);
        cursor.moveToNext();
        if (cursor.getCount()==0){
            Log.d("cursor", "addIngredient: null");
            SQLiteDatabase db2 = helper.getWritableDatabase();
            ContentValues row = new ContentValues();


            int idUnite=ingredient.getUnite().getId();

            row.put("nom", ingredient.getNom());
            row.put("prix", ingredient.getPrix());
            row.put("idUnite",idUnite);

            db2.insert("ingredient", null, row);

            db2.close();

            cursor.close();
            db.close();


            return true;

        } else {

            return false;

        }


    }



    public void addIngredientFrigo(Ingredient ingredient){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from  ingredient i inner join frigo f on f.idIngredient = i.id where nom = \""+ingredient.getNom()+"\"", null);
        Cursor addIngredient = db.rawQuery("select * from  ingredient where nom = \""+ingredient.getNom()+"\"", null);

        cursor.moveToNext();
        if (cursor.getCount()==0){



            addIngredient.moveToFirst();
            int idIngredient = addIngredient.getInt(0);
            addIngredient.close();

            SQLiteDatabase db3 = helper.getWritableDatabase();
            ContentValues row2 = new ContentValues();

            row2.put("idIngredient", idIngredient);
            row2.put("quantite", ingredient.getQuantite());
            db3.insert("frigo", null, row2);

            System.out.println("ajout ingredient inexistant");




        } else {
            Log.d("cursor", "addIngredient: not null");
            //cursor.moveToNext();

            float qtte = cursor.getInt(5);
            int id = cursor.getInt(0);


            ContentValues row = new ContentValues();
            float u = (qtte+ingredient.getQuantite());
            row.put("quantite", u);
            Log.d("qtte", "addIngredient: "+u);
            SQLiteDatabase db2 = helper.getWritableDatabase();
            int ok = db2.update("frigo", row, "idIngredient=\""+id+"\"",null);
            //Log.d("ok", "addIngredient: "+ok);
            db2.close();

            System.out.println("ajout ingredient existant");


        }






        cursor.close();
        db.close();
    }




    /**
     * Methode qui permet de supprimer un ingredient.
     * @param nom Nom de l'ingredient a supprimer.
     */
    public void supprimerIngredientFrigo(String nom){
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
     * Methode qui permet de supprimer un ingredient.
     * @param id ID de l'ingredient a supprimer.
     */
    public void supprimerIngredientFrigo(int id){
        SQLiteDatabase db = helper.getReadableDatabase();

        db.execSQL("delete from frigo where idIngredient=" + id + ";");

        db.close();



    }

    public void supprimerIngredientFrigo(Ingredient i,float quantité){
        int id=i.getId();
        Ingredient ingredient= getIngredientFrigoById(id);
        float qteFrigo=ingredient.getQuantite();
        System.out.println("qtefrigo : "+qteFrigo);
        SQLiteDatabase db = helper.getReadableDatabase();

        db.execSQL("delete from frigo where idIngredient=" + id + ";");

        db.close();

        ingredient.setQuantite(qteFrigo-quantité);
        if (ingredient.getQuantite()<=0){
            this.supprimerIngredientFrigo(ingredient.getId());
        } else {
            addIngredientFrigo(ingredient);
        }






    }

    /**
     * Methode qui permet d'ajouter un ingredient dans la base de donnees.
     */

    public int addRecette(Recette recette){

        ArrayList<Nourriture> composition=recette.getComposition();



        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("nom", recette.getNom());
        row.put("description",recette.getDescription());


        db.insert("recette", null, row);

        final String MY_QUERY = "SELECT last_insert_rowid()";
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int idRecette = cur.getInt(0);
        cur.close();

        for(Nourriture nourr : composition) {


            float qte = nourr.getQuantite();

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
        return idRecette;
    }



    public ArrayList<Recette> getAllRecettes() {

        ArrayList<Recette> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recette order by nom;", null);
        while(cursor.moveToNext()){


            Recette temp =new Recette(cursor.getString(1),new ArrayList<Nourriture>(),cursor.getInt(0), ""/*cursor.getString(2)*/ );
            Cursor cursor2 = db.rawQuery("select idIngredient, quantite from associationRecette where idRecette=" + cursor.getInt(0) + ";", null);
            if(cursor2 !=null && cursor2.moveToFirst()){
                cursor2.moveToPosition(-1);
                while(cursor2.moveToNext()) {

                    int idIng = cursor2.getInt(0);
                    int qte = cursor2.getInt(1);


                    Cursor cursor3 = db.rawQuery("select nom, prix from ingredient where id=" + idIng + ";", null);
                    cursor3.moveToFirst();
                    String nomIng = cursor3.getString(0);
                    int prixIng = cursor3.getInt(1);
                    cursor3.close();

                    Ingredient tempIng=new Ingredient(idIng,nomIng,prixIng,qte);
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

        Recette temp= new Recette("temp",new ArrayList<Nourriture>(),"");
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recette where id="+id+";", null);
        cursor.moveToFirst();
        if(cursor !=null /*&& cursor.moveToFirst()*/){
            //cursor.move(-1);
            temp =new Recette(cursor.getString(1) ,new ArrayList<Nourriture>(), cursor.getInt(0), cursor.getString(2) );
            Cursor cursor2 = db.rawQuery("select idIngredient, quantite from associationRecette where idRecette=" + id + ";", null);
            if(cursor2 !=null && cursor2.moveToFirst()){
                cursor2.move(-1);
                while(cursor2.moveToNext()) {

                    int idIng = cursor2.getInt(0);
                    int qte = cursor2.getInt(1);


                    Cursor cursor3 = db.rawQuery("select nom, prix,idUnite from ingredient where id=" + idIng + ";", null);
                    System.out.println(""+idIng);
                    cursor3.moveToFirst();
                    String nomIng = cursor3.getString(0);
                    int prixIng = cursor3.getInt(1);

                    Unite unite;

                    Cursor cursorUnite=db.rawQuery("select * from unite where id="+ cursor3.getInt(2)+";",null);
                    cursorUnite.moveToFirst();
                    if(cursorUnite.getCount()==0){unite= null;}
                    unite=new Unite(cursorUnite.getInt(0),cursorUnite.getString(1),cursorUnite.getString(2));
                    cursor3.close();

                    Ingredient tempIng=new Ingredient(idIng,nomIng,prixIng,qte,unite);
                    temp.addItem(tempIng);

                }

            }

            cursor2.close();
        }


        cursor.close();
        db.close();
        return temp;
    }

    public void deleteRecette(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        //Cursor cursor = db.rawQuery("DELETE from recette Where id="+id+";", null);
        db.delete("recette", "id" + "=" + id, null);
        //cursor.close();
        //Cursor cursor2 = db.rawQuery("DELETE from recette Where id="+id+";", null);
        db.delete("associationRecette", "idRecette" + "=" + id, null);
        //cursor2.close();
        db.close();
    }



    public void sauvegarderRepas(Repas repas){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idRecette", repas.getRecette().getId());


        Calendar cal = Calendar.getInstance();
        cal.setTime(repas.getDatePreparation());

        //Toast.makeText(getContext(), cal.toString(), Toast.LENGTH_SHORT).show();

        int jour  = cal.get(Calendar.DAY_OF_MONTH);


        String date = repas.getDatePreparation().getYear()+":"+((repas.getDatePreparation().getMonth()))+":"+jour+":"+repas.getDatePreparation().getHours()+":"+repas.getDatePreparation().getMinutes();
        cv.put("dateRepas", date);

        db.insert("repas", null, cv);
        db.close();

        System.out.println("sauvegarde repas");
        Log.d("SAUVEGARDE", "sauvegarderRepas: Date="+date);
    }


    /**
     * Methode qui permet verifier la disponibilite d'un ingredient dans le frigo
     * @param ingredient Ingredient dont il faut verifier la disponibilite.
     * @return -1 si l'ingredient est disponible
     *          0 s'il est manquant
     *          quantite>0 s'il en manque une certaine quantite.
     */
    public float ingIsAvailable(Ingredient ingredient){


        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from frigo where idIngredient=\"" + ingredient.getId() + "\";", null);
        Log.d("QUERY", "IngisAvailable: select * from frigo where idIngredient=\"" + ingredient.getId() + "\";");
        cursor.moveToFirst();
        if(cursor.getCount()==0){return 0;}
        int dispo=cursor.getInt(1);
        cursor.close();
        db.close();

        float diff =dispo-ingredient.getQuantite();
        if (diff<0){
            return -diff;
        }
        return -1;



    }
    /**
     * Methode qui permet verifier la disponibilite des ingredients d'une recette dans le frigo
     * @param recette Recette dont il faut verifier la disponibilite.
     * @return le nombre d'ingredients manquants pour faire la recette ou -1 s'il manque tous les ingrédients
     */
    public int recetteIsAvailable(Recette recette){
        ArrayList<Nourriture> composition=recette.getComposition();

        int missing=0;
        float ingmissing;

        for (Nourriture nourriture: composition) {
            if (nourriture instanceof Ingredient) {
                ingmissing= ingIsAvailable((Ingredient) nourriture);
                if(ingmissing>=0){
                    missing++;
                }
            } else if(nourriture instanceof Recette){
                ingmissing= recetteIsAvailable((Recette)nourriture);
                if(ingmissing>0){
                    missing++;
                }
            }

        }

        if(missing==composition.size()&&missing!=0){
            return -1;
        }else{
            return missing;
        }


    }

    public Recette updateRecette(Recette recette){

        deleteRecette(recette.getId());
        int id=addRecette(recette);
        recette=getRecetteById(id);
        return recette;

    }


    public ArrayList<Repas> getRepasPlanifies(){
        ArrayList<Repas> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from repas;", null);
        while(cursor.moveToNext()){

            int id = cursor.getInt(0);
            Recette r = this.getRecetteById(cursor.getInt(1));
            String stringDate = cursor.getString(2);
            System.out.println("DATE STRING = "+stringDate);
            String[] tabDate = stringDate.split(":");
            Date date = new Date(
                    Integer.parseInt(tabDate[0]),
                    Integer.parseInt(tabDate[1]),
                    Integer.parseInt(tabDate[2]),
                    Integer.parseInt(tabDate[3]),
                    Integer.parseInt(tabDate[4])
            );



            retour.add(new Repas(id,r,date));
            //System.out.println(Integer.parseInt(tabDate[2]));
            System.out.println("Date : "+date);


        }
        cursor.close();
        db.close();

        return retour;

    }

    public ArrayList<Unite> getAllUnites(){
        ArrayList<Unite> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();



        Cursor cursor = db.rawQuery("select * from unite order by nom;", null);
        if(cursor.getCount()==0){
            db.execSQL("INSERT INTO unite VALUES(1, \"Grammes\", \"g\");");
            db.execSQL("INSERT INTO unite VALUES(2, \"millilitres\", \"mL\");");
            db.execSQL("INSERT INTO unite VALUES(3, \"Tasses\", \"tasses\");");
            db.execSQL("INSERT INTO unite VALUES(4, \"Sans unité\", \"\");");
            cursor = db.rawQuery("select * from unite order by nom;", null);
        }

        while(cursor.moveToNext()){
            retour.add(new Unite(cursor.getInt(0),cursor.getString(1), cursor.getString(2)));

        }
        cursor.close();
        db.close();
        return retour;
    }

    public Unite getUniteById(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from unite where id=" + id + ";", null);
        Log.d("QUERY", "getIngredientByNm: select * from unite where id=" + id + ";");
        cursor.moveToFirst();
        if(cursor.getCount()==0){return null;}
        Unite retour=new Unite(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return retour;
    }

    public boolean addUnite(Unite unite){

        System.out.println();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from unite where nom = \""+unite.getName()+"\"", null);
        cursor.moveToNext();
        if (cursor.getCount()==0){
            Log.d("cursor", "addUnite: null");
            SQLiteDatabase db2 = helper.getWritableDatabase();
            ContentValues row = new ContentValues();

            row.put("nom", unite.getName());
            row.put("symbole", unite.getSymbol());

            db2.insert("unite", null, row);

            db2.close();

            cursor.close();
            db.close();


            return true;

        } else {

            return false;

        }


    }



    public void supprimerRepas(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM repas WHERE id="+id+";");
        db.close();
    }






    public void supprimerIngredientCourses(String nom){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from courses inner join ingredient on courses.idIngredient = ingredient.id where nom =\""+nom+"\"", null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        cursor.close();
        db.close();
        this.supprimerIngredientCourses(id);
    }

    /**
     * Methode qui permet de supprimer un ingredient.
     * @param id ID de l'ingredient a supprimer.
     */
    public void supprimerIngredientCourses(int id){
        SQLiteDatabase db2 = helper.getWritableDatabase();
        db2.execSQL("delete from courses where idIngredient="+id);
        db2.close();
    }



    public void addIngredientCourses(Ingredient ingredient){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from  ingredient i inner join courses f on f.idIngredient = i.id where nom = \""+ingredient.getNom()+"\"", null);
        Cursor addIngredient = db.rawQuery("select * from  ingredient where nom = \""+ingredient.getNom()+"\"", null);

        cursor.moveToNext();
        if (cursor.getCount()==0){



            addIngredient.moveToFirst();
            int idIngredient = addIngredient.getInt(0);
            addIngredient.close();

            SQLiteDatabase db3 = helper.getWritableDatabase();
            ContentValues row2 = new ContentValues();

            row2.put("idIngredient", idIngredient);
            row2.put("quantite", ingredient.getQuantite());
            db3.insert("courses", null, row2);

            System.out.println("ajout ingredient inexistant");




        } else {
            Log.d("cursor", "addIngredient: not null");
            //cursor.moveToNext();

            float qtte = cursor.getInt(5);
            int id = cursor.getInt(0);


            ContentValues row = new ContentValues();
            float u = (qtte+ingredient.getQuantite());
            row.put("quantite", u);
            Log.d("qtte", "addIngredient: "+u);
            SQLiteDatabase db2 = helper.getWritableDatabase();
            int ok = db2.update("courses", row, "idIngredient=\""+id+"\"",null);
            //Log.d("ok", "addIngredient: "+ok);
            db2.close();

            System.out.println("ajout ingredient existant");


        }

    }


    public Ingredient getIngredientCoursesById(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient inner join courses on courses.idIngredient=ingredient.id inner join unite on unite.id = ingredient.idUnite where idIngredient=" + id + ";", null);
        //Log.d("QUERY", "getIngredientByNm: select * from frigo where id=" + id + ";");
        cursor.moveToFirst();
        if(cursor.getCount()==0){return null;}
        Ingredient retour=new Ingredient(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getFloat(5), this.getUniteById(cursor.getInt(6)));
        cursor.close();
        db.close();
        return retour;
    }


    public ArrayList<Ingredient> getAllIngredientsCourses(){
        ArrayList<Ingredient> retour = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ingredient inner join courses on courses.idIngredient = ingredient.id order by nom;", null);
        while(cursor.moveToNext()){
            retour.add(this.getIngredientCoursesById(cursor.getInt(0)));
        }
        cursor.close();
        db.close();
        return retour;
    }








}
