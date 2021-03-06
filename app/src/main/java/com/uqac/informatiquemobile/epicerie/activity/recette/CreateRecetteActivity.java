package com.uqac.informatiquemobile.epicerie.activity.recette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.activity.ingredient.AddIngredientActivity;
import com.uqac.informatiquemobile.epicerie.adapter.IngredientListAdapter;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Nourriture;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité qui permet de créer une recette
 */
public class CreateRecetteActivity extends Activity {


    private ListView listViewIngredients;
    private ArrayList<Nourriture> ingredients;

    private IngredientListAdapter adapter;

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
        ingredients = new ArrayList<Nourriture>();


        adapter = new IngredientListAdapter(getApplicationContext(), R.layout.resultat_recherche_layout, (List)ingredients,false);
        listViewIngredients.setAdapter(adapter);

        boutonAjouterRecette = (Button)findViewById(R.id.buttonCreate);

        boutonAjouterRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomRecette.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de la recette", Toast.LENGTH_SHORT).show();
                }  else {


                    Recette recette= new Recette(editTextNomRecette.getText().toString(),ingredients,editTextDescRecette.getText().toString());

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

        listViewIngredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ingredients.remove(position);


                adapter.notifyDataSetChanged();



            return true;
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
                    //Toast.makeText(getApplicationContext(), jsonIngredient, Toast.LENGTH_SHORT).show();
                } else {
                    jsonIngredient = null;
                }
                Ingredient ingredientAjout = new Gson().fromJson(jsonIngredient, Ingredient.class);


                if(ingredientAjout.getQuantite()!=0){
                    ArrayList<String> listnomIng=new ArrayList<String>();
                    for (Nourriture nouriture:ingredients) {
                        listnomIng.add(nouriture.getNom());
                    }

                    if(listnomIng.contains(ingredientAjout.getNom())){
                        int index=listnomIng.indexOf(ingredientAjout.getNom());
                        if(ingredients.get(index) instanceof Ingredient){
                            Ingredient ingtemp=(Ingredient)ingredients.get(index);
                            ingtemp.setQuantite(ingtemp.getQuantite()+ingredientAjout.getQuantite());
                        }

                    }else{
                        ingredients.add(ingredientAjout);

                    }

                    adapter.notifyDataSetChanged();


                }else{Toast.makeText(getApplicationContext(), "La quantité de l'ingrédient ne peut être nulle", Toast.LENGTH_SHORT).show();}

            }
        }
    }
}
