package com.uqac.informatiquemobile.epicerie.activity.recette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.adapter.IngredientListAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Nourriture;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class DetailedRecetteActivity extends Activity {
    private DataBaseManager dbm ;
    private ListView listViewIngredients;
    private TextView textViewName;
    private TextView textViewDesc;
    private LinearLayout buttonMod;
    private LinearLayout buttonDel;
    private ArrayList<Nourriture> ingredients;
    private IngredientListAdapter adapter;
    private Recette recette;
    private LinearLayout buttonConsume;
    private TextView textConsume;
    private boolean complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_recette_activity);




        int id = getIntent().getExtras().getInt("id");
        recette=displayinfo(id);
        if(dbm.recetteIsAvailable(recette)!=0){
            textConsume.setText("Ajouter les ingrédients manquants à la liste de courses");
            complete=false;
        }else{
            complete=true;
                    }

        buttonMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRecette = recette.getId();
                Intent mIntent = new Intent(getApplicationContext(), ModifyRecetteActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("id", idRecette);
                mIntent.putExtras(extras);
                startActivityForResult(mIntent, 456);
            }
        });
        buttonDel.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                int id = getIntent().getExtras().getInt("id");

                dbm.deleteRecette(id);


                finish();
            }

        });

        buttonConsume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(complete){
                    for (Nourriture ingredient:recette.getComposition()) {
                        if(ingredient instanceof Ingredient){
                            dbm.supprimerIngredientFrigo((Ingredient)ingredient, ingredient.getQuantite());

                            finish();
                        }

                    }
                }else{
                    float manquant;
                    for (Nourriture ingredient :ingredients) {
                        if(ingredient instanceof Ingredient){
                            float dispo=dbm.ingIsAvailable((Ingredient)ingredient);
                            if(dispo==0){
                                manquant= ingredient.getQuantite();
                                dbm.addIngredientCourses(new Ingredient(ingredient.getId(),ingredient.getNom(),ingredient.getPrix(),manquant));

                            }else if(dispo>0){
                                manquant=dispo;
                                dbm.addIngredientCourses(new Ingredient(ingredient.getId(),ingredient.getNom(),ingredient.getPrix(),manquant));
                            }

                        }

                    }
                    Toast.makeText(getApplicationContext(), "Ingredients ajoutés à la liste de courses", Toast.LENGTH_SHORT).show();
                }
            }
        });


        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private Recette displayinfo(int id){
        dbm = new DataBaseManager(getApplicationContext());
        final Recette recette= dbm.getRecetteById(id);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewDesc=(TextView) findViewById(R.id.textViewDesc);

        buttonMod = (LinearLayout) findViewById(R.id.buttonMod);
        buttonDel = (LinearLayout) findViewById(R.id.buttonDel);
        buttonConsume=(LinearLayout)findViewById(R.id.buttonConsumeIngredients);
        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);
        textConsume=(TextView) findViewById(R.id.textViewConsume);

        textViewName.setText(recette.getNom());
        //textViewDesc.setText("composition.size : "+recette.getComposition().size()+" \nmissing : "+dbm.recetteIsAvailable(recette));
        textViewDesc.setText(recette.getDescription());


        ingredients = new ArrayList(recette.getComposition());

        adapter = new IngredientListAdapter(getApplicationContext(), R.layout.resultat_recherche_layout, (List)ingredients, true);
        listViewIngredients.setAdapter(adapter);
        return recette;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            if(requestCode == 456){



                Bundle extras = data.getExtras();
                if (extras != null) {
                    int id =extras.getInt("id");
                    recette=displayinfo(id);
                }




            }
        }
    }
}
