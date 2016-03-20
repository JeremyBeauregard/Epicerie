package com.uqac.informatiquemobile.epicerie.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ajouter un ingredient
            }
        });

        DataBaseManager dbm = new DataBaseManager(getApplicationContext());
        dbm.FixtureIngredients();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients = dbm.getAll("ingredient");
        for (Ingredient i :ingredients) {
            System.out.println(i.getNom()+" : "+i.getPrix()+"\n");
        }

        //test();

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

    private void test() {

        // Crée des ingrédients pour le fun
        Ingredient tranchePain = new Ingredient("Tranche de pain", 0.05f);
        Ingredient banane = new Ingredient("Banane", 0.25f);

        Recette sandwichBanane = new Recette("Sandwich aux bananes");
        sandwichBanane.addItem(tranchePain, 2);
        sandwichBanane.addItem(banane, 1);


        // Récupère le textView pour afficher le résultat à l'écran
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(sandwichBanane.getNom() + ": " + sandwichBanane.getPrix() + "$");

    }
}
