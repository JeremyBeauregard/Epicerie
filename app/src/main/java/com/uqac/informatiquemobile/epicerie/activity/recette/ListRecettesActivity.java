package com.uqac.informatiquemobile.epicerie.activity.recette;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.adapter.RecetteListAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class ListRecettesActivity extends AppCompatActivity {
    private DataBaseManager dbm;
    private boolean checkmissingingredients=true;
    private ListView listViewRecettes;
    private ArrayList<Recette> recettes;
    private RecetteListAdapter adapterListViewRecettes;
    LinearLayout buttonRechercherRecetteEnLigne;
    SwitchCompat switchcomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recettes_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        switchcomplete=(SwitchCompat) findViewById(R.id.switchcomplete);
        switchcomplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchcomplete.isChecked()){
                    recettes= dbm.getAllRecettes();
                    for (Iterator<Recette> it = recettes.iterator(); it.hasNext(); ) {
                        Recette recette = it.next();
                        if (dbm.recetteIsAvailable(recette)!=0) {
                            it.remove();
                        }
                    }
                    adapterListViewRecettes = new RecetteListAdapter(ListRecettesActivity.this, R.layout.list_recette_layout, recettes,checkmissingingredients);
                    listViewRecettes.setAdapter(adapterListViewRecettes);


                    adapterListViewRecettes.notifyDataSetChanged();
                }else{
                    recettes= dbm.getAllRecettes();
                    adapterListViewRecettes = new RecetteListAdapter(ListRecettesActivity.this, R.layout.list_recette_layout, recettes,checkmissingingredients);
                    listViewRecettes.setAdapter(adapterListViewRecettes);


                    adapterListViewRecettes.notifyDataSetChanged();
                }
            }
        });




        buttonRechercherRecetteEnLigne = (LinearLayout)findViewById(R.id.buttonRechercherRecette);
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

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    protected void onResume(){
        super.onResume();





        recettes= dbm.getAllRecettes();
        adapterListViewRecettes = new RecetteListAdapter(ListRecettesActivity.this, R.layout.list_recette_layout, recettes,checkmissingingredients);
        listViewRecettes.setAdapter(adapterListViewRecettes);


        adapterListViewRecettes.notifyDataSetChanged();



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
