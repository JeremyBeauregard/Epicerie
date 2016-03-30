package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class ListRecettesActivity extends Activity {
    private DataBaseManager dbm;

    private ListView listViewRecettes;
    private ArrayList<String> listRecettes;
    ArrayList<Recette> recettes;
    private ArrayAdapter<String> adapterListViewRecettes;
    Button buttonRechercherRecetteEnLigne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recettes_activity);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), CreateRecetteActivity.class);
                startActivity(i);
            }
        });

        dbm = new DataBaseManager(getApplicationContext());

        listViewRecettes = (ListView) findViewById(R.id.listRecettes);
        listRecettes = new ArrayList<>();



        buttonRechercherRecetteEnLigne = (Button)findViewById(R.id.buttonRechercherRecette);
        buttonRechercherRecetteEnLigne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListRecettesActivity.this, RechercheActivity.class);
                startActivity(i);
            }
        });

        adapterListViewRecettes = new ArrayAdapter<String>(ListRecettesActivity.this, android.R.layout.simple_list_item_1, listRecettes);
        listViewRecettes.setAdapter(adapterListViewRecettes);



    }

    @Override
    protected void onResume(){
        super.onResume();



        listRecettes.removeAll(listRecettes);

        recettes= dbm.getAllRecettes();
        for (Recette i :recettes) {

            listRecettes.add(i.getNom());
        }

        adapterListViewRecettes.notifyDataSetChanged();

    }
}
