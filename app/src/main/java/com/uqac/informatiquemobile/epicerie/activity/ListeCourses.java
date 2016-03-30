package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    ArrayList<Ingredient> selectedIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_courses_activity);

        dbm = new DataBaseManager(getApplicationContext());

        //////////////////////////////////////////////////

        final HashMap<String, Ingredient> ingredients = new HashMap<>();
        ingredients.put("Baguette", new Ingredient("Baguette", 50, 2));
        ingredients.put("Tomate", new Ingredient("Tomate", 25, 4));
        ingredients.put("Patate",new Ingredient("Patate", 10, 10));

        //////////////////////////////////////////////////

        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);
        listViewIngredients.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        final ArrayList<String> titres = new ArrayList(ingredients.keySet());


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.courses_row_layout, (List)titres);
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
                } else {
                    selectedIngredients.add(selectedIngredient);
                }

            }
        });



        Button ajouterAuFrigo = (Button)findViewById(R.id.buttonAjouterAuFrigo);
        ajouterAuFrigo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (Ingredient i : selectedIngredients) {
                    for (int x = 0; x < i.getQuantite(); x++) {
                        dbm.addIngredient(i.getNom(), i.getPrix());


                    }
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





    }
}
