package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by paull on 29/03/2016.
 */
public class ListeCourses extends Activity {

    ListView listViewIngredients;
    ArrayList<Ingredient> selectedIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_courses_activity);

        //////////////////////////////////////////////////

        final HashMap<String, Ingredient> ingredients = new HashMap<>();
        ingredients.put("Baguette", new Ingredient("Baguette", 50, 2));
        ingredients.put("Tomate", new Ingredient("Tomate", 25, 4));
        ingredients.put("Patate",new Ingredient("Patate", 10, 10));

        //////////////////////////////////////////////////

        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);
        listViewIngredients.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<String> titres = new ArrayList(ingredients.values());


        ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(getApplicationContext(), R.layout.courses_row_layout, (List)titres);
        listViewIngredients.setAdapter(adapter);
        listViewIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view;
                Ingredient selectedIngredient = ingredients.get(tv.getText().toString());
                Toast.makeText(getApplicationContext(), selectedIngredient.toString(), Toast.LENGTH_SHORT).show();
            }
        });






    }
}
