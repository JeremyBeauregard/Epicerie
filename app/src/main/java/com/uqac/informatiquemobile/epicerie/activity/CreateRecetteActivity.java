package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Nourriture;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Activité qui permet de créer une recette
 */
public class CreateRecetteActivity extends Activity {
    private HashMap<Nourriture, Float> composition=new HashMap<Nourriture, Float>(); //update à l'ajout d'ingrédients

    private ListView listViewIngredients;
    private HashMap<String, Ingredient> ingredients;
    private ArrayList<String> titres;
    private ArrayAdapter<String> adapter;

    EditText editTextNomRecette, editTextDescRecette;
    Button boutonAjouterIngredient, boutonAjouterRecette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recette);


        editTextNomRecette = (EditText)findViewById(R.id.editTextName);
        editTextDescRecette = (EditText)findViewById(R.id.editTextDesc);
        boutonAjouterIngredient = (Button)findViewById(R.id.buttonAdd);
        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);

        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);
        ingredients = new HashMap<>();
        titres = new ArrayList(ingredients.keySet());

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.resultat_recherche_layout, (List)titres);
        listViewIngredients.setAdapter(adapter);

        boutonAjouterRecette = (Button)findViewById(R.id.buttonCreate);

        boutonAjouterRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomRecette.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de la recette", Toast.LENGTH_SHORT).show();
                }  else {


                    Recette recette= new Recette(editTextNomRecette.getText().toString(),composition);

                    DataBaseManager dbm = new DataBaseManager(getApplicationContext());
                    dbm.addRecette(recette);


                    finish();
                }
            }
        });

        boutonAjouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddIngredientActivity.class);
                startActivityForResult(i, 123);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            if(requestCode == 123){

                String jsonIngredient;

                Bundle extras = data.getExtras();
                if (extras != null) {
                    jsonIngredient = extras.getString("ingredient");
                    Toast.makeText(getApplicationContext(), jsonIngredient, Toast.LENGTH_SHORT).show();
                } else {
                    jsonIngredient = null;
                }
                Ingredient ingredientAjout = new Gson().fromJson(jsonIngredient, Ingredient.class);
                Gson g = new Gson();

                titres.add(ingredientAjout.getNom() + " : " + ingredientAjout.getQuantite());
                ingredients.put(ingredientAjout.getNom(), ingredientAjout);
                adapter.notifyDataSetChanged();

                composition.put(ingredientAjout, (float)ingredientAjout.getQuantite());

            }
        }

    }
}
