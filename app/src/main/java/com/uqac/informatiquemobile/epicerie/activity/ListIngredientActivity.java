package com.uqac.informatiquemobile.epicerie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;

public class ListIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredients_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddIngredientActivity.class);
                startActivityForResult(i, 123);
            }
        });


        DataBaseManager dbm = new DataBaseManager(getApplicationContext());
        dbm.viderIngredient();
        dbm.FixtureIngredients();

        ArrayList<Ingredient> ingredients= dbm.getAll("ingredient");
        for (Ingredient i :ingredients) {
            System.out.println(i.getNom()+" : "+i.getPrix()+"\n");
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123){

            String jsonIngredient;

            Bundle extras = data.getExtras();
            if (extras != null) {
                jsonIngredient = extras.getString("ingredient");
            } else {
                jsonIngredient = null;
            }
            Ingredient ingredientCree = new Gson().fromJson(jsonIngredient, Ingredient.class);


            DataBaseManager dbm = new DataBaseManager(getApplicationContext());
            dbm.addIngredient(ingredientCree.getNom(), ingredientCree.getPrix());


            ArrayList<Ingredient> ingredients= dbm.getAll("ingredient");
            for (Ingredient i :ingredients) {
                System.out.println(i.getNom()+" : "+i.getPrix()+"\n");
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*    private void test() {

        // Crée des ingrédients pour le fun
        Ingredient tranchePain = new Ingredient("Tranche de pain", 0.05f);
        Ingredient banane = new Ingredient("Banane", 0.25f);

        Recette sandwichBanane = new Recette("Sandwich aux bananes");
        sandwichBanane.addItem(tranchePain, 2);
        sandwichBanane.addItem(banane, 1);


        // Récupère le textView pour afficher le résultat à l'écran
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(sandwichBanane.getNom() + ": " + sandwichBanane.getPrix() + "$");

    }*/
}
