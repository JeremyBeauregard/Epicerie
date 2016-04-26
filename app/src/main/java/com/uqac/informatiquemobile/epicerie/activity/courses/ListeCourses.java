package com.uqac.informatiquemobile.epicerie.activity.courses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.activity.ingredient.AddIngredientActivity;
import com.uqac.informatiquemobile.epicerie.adapter.IngredientListAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;

/**
 * Created by paull on 29/03/2016.
 */
public class ListeCourses extends AppCompatActivity {

    private DataBaseManager dbm;
    private ListView listViewIngredients;
    private Button ajouterAuFrigo;
    private Button ajouterIngredient;
    private ArrayList<Ingredient> ingredients;
    private IngredientListAdapter adapter;
    private int total = 0;
    TextView textViewPrix;
    String prixTotal;

    private ArrayList<Ingredient> selectedIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_courses_activity);

        dbm = new DataBaseManager(getApplicationContext());
        ingredients = dbm.getAllIngredientsCourses();
        textViewPrix = (TextView)findViewById(R.id.textViewPrix);
        textViewPrix.setText("Prix : 0,00$");
        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);


        adapter = new IngredientListAdapter(getApplicationContext(), R.layout.item_list_row, ingredients, false);
        listViewIngredients.setAdapter(adapter);

        listViewIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Ingredient selectedIngredient = ingredients.get(position);

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

                    dbm.supprimerIngredientCourses(i.getId());
                    ingredients.remove(i);

                }

                selectedIngredients = new ArrayList<Ingredient>();


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



        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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

                } else {
                    jsonIngredient = null;
                }

                Ingredient ingredientAjout = new Gson().fromJson(jsonIngredient, Ingredient.class);
                ingredients.add(ingredientAjout);
                adapter.notifyDataSetChanged();
                listViewIngredients.setAdapter(adapter);

                dbm.addIngredientCourses(ingredientAjout);


            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
