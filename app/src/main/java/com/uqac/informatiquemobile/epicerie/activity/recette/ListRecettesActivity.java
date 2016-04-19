package com.uqac.informatiquemobile.epicerie.activity.recette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.adapter.RecetteListAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class ListRecettesActivity extends Activity {
    private DataBaseManager dbm;
    private boolean checkmissingingredients=false;
    private ListView listViewRecettes;
    private ArrayList<Recette> recettes;
    private RecetteListAdapter adapterListViewRecettes;
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




        buttonRechercherRecetteEnLigne = (Button)findViewById(R.id.buttonRechercherRecette);
        buttonRechercherRecetteEnLigne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListRecettesActivity.this, RechercheActivity.class);
                startActivity(i);
            }
        });

        recettes= dbm.getAllRecettes();
        adapterListViewRecettes = new RecetteListAdapter(ListRecettesActivity.this, R.layout.list_recette_layout, recettes,checkmissingingredients);
        listViewRecettes.setAdapter(adapterListViewRecettes);

        listViewRecettes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recette selectedRecette=recettes.get(position);
                int idRecette = selectedRecette.getId();
                Intent mIntent = new Intent(getApplicationContext(), DetailedRecetteActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("id", idRecette);
                mIntent.putExtras(extras);
                startActivity(mIntent);


            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();





        recettes= dbm.getAllRecettes();
        adapterListViewRecettes = new RecetteListAdapter(ListRecettesActivity.this, R.layout.list_recette_layout, recettes,checkmissingingredients);
        listViewRecettes.setAdapter(adapterListViewRecettes);


        adapterListViewRecettes.notifyDataSetChanged();

    }

}
