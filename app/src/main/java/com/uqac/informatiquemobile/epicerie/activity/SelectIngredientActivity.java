package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;

/**
 * Created by paull on 30/03/2016.
 */
public class SelectIngredientActivity extends Activity {
    private DataBaseManager dbm;

    private ListView listViewIngredients;
    private ArrayList<String> listIngredients;
    private ArrayList<Ingredient> ingredients;
    private ArrayAdapter<String> adapterListViewIngredients;
    private TextView textViewPrix;

    private TextView textViewValeurIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredients_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), CreateIngredientActivity.class);
                startActivityForResult(i, 123);
            }
        });


        dbm = new DataBaseManager(getApplicationContext());
        //dbm.viderIngredient();
        //dbm.FixtureIngredients();

        listViewIngredients = (ListView) findViewById(R.id.listIngredients);
        listIngredients = new ArrayList<>();

        ingredients= dbm.getAllIngredient();
        for (Ingredient i :ingredients) {
            //System.out.println(i.getNom()+" : "+i.getPrix()+i.getQuantite()+" : "+"\n");
            listIngredients.add(i.getNom()+" : "+i.getQuantite());
        }

        adapterListViewIngredients = new ArrayAdapter<String>(SelectIngredientActivity.this, android.R.layout.simple_list_item_1, listIngredients);
        listViewIngredients.setAdapter(adapterListViewIngredients);


        listViewIngredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String t = ((TextView) view).getText().toString().split(" ")[0];
                dbm.supprimerIngredient(t);
                Toast.makeText(getApplicationContext(), "Delete : " + t, Toast.LENGTH_SHORT).show();

                Ingredient i = dbm.getIngredientByNm(t);
                if (i != null) {
                    int qtte = i.getQuantite();
                    System.out.println(qtte);
                    ((TextView) view).setText(t + " : " + qtte);
                } else {
                    listIngredients.remove(position);
                    adapterListViewIngredients.notifyDataSetChanged();
                }


                int val = 0;
                ingredients= dbm.getAllIngredient();
                for (Ingredient in:ingredients) {
                    System.out.println(in.getPrixTotal());
                    val = val +in.getPrixTotal();
                }

                textViewValeurIngredients.setText(String.valueOf((double) val / 100));

                return true;
            }
        });

        listViewIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String t = ((TextView) view).getText().toString().split(" ")[0];
                Ingredient i = dbm.getIngredientByNm(t);

                dbm.addIngredient(i.getNom(), i.getPrix());
                Toast.makeText(getApplicationContext(), "Ajout : " + i.getNom()+i.getQuantite(), Toast.LENGTH_SHORT).show();

                ((TextView) view).setText(t+" : "+(i.getQuantite()+1));

                int val = 0;
                ingredients= dbm.getAllIngredient();
                for (Ingredient in:ingredients) {
                    System.out.println(in.getPrixTotal());
                    val = val +in.getPrixTotal();
                }

                textViewValeurIngredients.setText(String.valueOf((double) val / 100));


            }
        });



        textViewValeurIngredients = (TextView)findViewById(R.id.textViewValeur);
        int val = 0;
        for (Ingredient i:ingredients) {
            System.out.println(i.getPrixTotal());
            val = val +i.getPrixTotal();
        }

        textViewValeurIngredients.setText(String.valueOf((double)val/100));




    }

    /**
     * Recuperation de l'ingredient cree.
     * @param requestCode
     * @param resultCode
     * @param data Gson de l'ingredient cree.
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
                    Toast.makeText(getApplicationContext(), jsonIngredient, Toast.LENGTH_SHORT ).show();
                } else {
                    jsonIngredient = null;
                }
                Ingredient ingredientCree = new Gson().fromJson(jsonIngredient, Ingredient.class);
                Gson g = new Gson();
                System.out.println("INGREDIENT :"+g.fromJson(jsonIngredient, Ingredient.class).getPrix());
                System.out.println("INGREDIENT : "+ingredientCree.getNom()+ingredientCree.getPrix()+ingredientCree.getQuantite());

                DataBaseManager dbm = new DataBaseManager(getApplicationContext());
                dbm.addIngredient(ingredientCree.getNom(), ingredientCree.getPrix());
                listIngredients.add(ingredientCree.getNom() + " : " + ingredientCree.getQuantite());
                adapterListViewIngredients.notifyDataSetChanged();



                ArrayList<Ingredient> ingredients= dbm.getAllIngredient();
            }
        }

    }
}
