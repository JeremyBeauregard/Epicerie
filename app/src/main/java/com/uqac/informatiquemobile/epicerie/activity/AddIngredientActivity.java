package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
public class AddIngredientActivity extends Activity {
    private DataBaseManager dbm;

    private ListView listViewIngredients;
    private ArrayList<String> listIngredients;
    private ArrayList<Ingredient> ingredients;
    private ArrayAdapter<String> adapterListViewIngredients;
    private TextView textViewPrix;
    private String resultat;

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
                startActivity(i);
            }
        });




        dbm = new DataBaseManager(getApplicationContext());

        listViewIngredients = (ListView) findViewById(R.id.listIngredients);
        listIngredients = new ArrayList<>();

        ingredients= dbm.getAllIngredient();
        for (Ingredient i :ingredients) {
            listIngredients.add(i.getNom()+" : "+i.getQuantite());
        }

        adapterListViewIngredients = new ArrayAdapter<String>(AddIngredientActivity.this, android.R.layout.simple_list_item_1, listIngredients);
        listViewIngredients.setAdapter(adapterListViewIngredients);



        listViewIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO

                final String tv = ((TextView)view).getText().toString();
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(AddIngredientActivity.this);
                View promptsView = li.inflate(R.layout.input_quantite_layout, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        AddIngredientActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Ingredient temp = dbm.getIngredientByNm(tv);
                                        if(userInput.getText().toString().length()!=0){
                                            resultat = userInput.getText().toString();

                                            Ingredient retour = new Ingredient(tv, temp.getPrix(), Integer.parseInt(resultat));
                                            //Toast.makeText(getApplicationContext(), retour.toString(), Toast.LENGTH_SHORT).show();
                                            Bundle data = new Bundle();
                                            data.putString("ingredient", new Gson().toJson(retour));
                                            Intent intent = new Intent();
                                            intent.putExtras(data);
                                            setResult(123, intent);
                                        }else {
                                            Toast.makeText(getApplicationContext(), "La quantité ne peut être nulle", Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


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

    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<Ingredient> ingredients= dbm.getAllIngredient();

        listIngredients.removeAll(listIngredients);

        for (Ingredient i :ingredients) {
            listIngredients.add(i.getNom());
        }

        adapterListViewIngredients.notifyDataSetChanged();

    }
}
