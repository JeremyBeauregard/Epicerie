package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by paull on 29/03/2016.
 */
public class ListeCourses extends Activity {

    private DataBaseManager dbm;
    private ListView listViewIngredients;
    private Button ajouterAuFrigo;
    private Button ajouterIngredient;
    private HashMap<String, Ingredient> ingredients;
    private ArrayList<String> titres;
    private ArrayAdapter<String> adapter;
    private int total = 0;
    TextView textViewPrix;
    String prixTotal;

    private ArrayList<Ingredient> selectedIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_courses_activity);

        dbm = new DataBaseManager(getApplicationContext());

        //////////////////////////////////////////////////

        ingredients = new HashMap<>();
        /*ingredients.put("Baguette", new Ingredient("Baguette", 50, 3));
        ingredients.put("Tomate", new Ingredient("Tomate", 25, 3));
        ingredients.put("Patate",new Ingredient("Patate", 10, 3));*/

        //////////////////////////////////////////////////

        textViewPrix = (TextView)findViewById(R.id.textViewPrix);
        textViewPrix.setText("Prix : 0,00$");

        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);
        listViewIngredients.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        titres = new ArrayList(ingredients.keySet());

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.courses_row_layout, (List)titres);
        listViewIngredients.setAdapter(adapter);

        listViewIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String nom = tv.getText().toString().split(" | ")[0];
                //Toast.makeText(getApplicationContext(), "-"+nom+"-", Toast.LENGTH_SHORT).show();
                Ingredient selectedIngredient = ingredients.get(nom);
                //Toast.makeText(getApplicationContext(), selectedIngredient.toString(), Toast.LENGTH_SHORT).show();
                if (selectedIngredients.contains(selectedIngredient)) {
                    selectedIngredients.remove(selectedIngredient);
                    total-=selectedIngredient.getPrixTotal();
                } else {
                    selectedIngredients.add(selectedIngredient);
                    total+=selectedIngredient.getPrixTotal();
                }
                prixTotal = "Prix : "+(double)total/100 + "$";
                textViewPrix.setText(prixTotal);
            }
        });

        ajouterAuFrigo = (Button)findViewById(R.id.buttonAjouterAuFrigo);
        ajouterAuFrigo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (Ingredient i : selectedIngredients) {

                    dbm.addIngredientFrigo(i);

                    for (int x = 0; x < titres.size(); x++) {

                        if (i.getNom().equals(titres.get(x))) {
                            titres.remove(x);
                        } else {
                            //Toast.makeText(getApplicationContext(), i.getNom().toString() + (titres.get(x).toString()), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), titres.toString(), Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();





            }
        });


        ajouterIngredient = (Button)findViewById(R.id.buttonAjouterIngredient);
        ajouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddIngredientActivity.class);
                startActivityForResult(i, 123);
            }
        });








    }

    /**
     * Recuperation de l'ingredient à ajouter.
     * @param requestCode
     * @param resultCode
     * @param data Gson de l'ingredient à ajouter.
     */
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

                titres.add(ingredientAjout.getNom() /*+ " : " + ingredientAjout.getQuantite()*/);
                ingredients.put(ingredientAjout.getNom(), ingredientAjout);
                adapter.notifyDataSetChanged();

            }
        }

    }


}
