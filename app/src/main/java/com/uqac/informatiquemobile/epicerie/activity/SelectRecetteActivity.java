package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.ArrayList;

/**
 * Created by paull on 16/04/2016.
 */
public class SelectRecetteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_recettes);
        ListView lv = (ListView) findViewById(R.id.listRecettes);
        ((Button)findViewById(R.id.buttonRechercherRecette)).setEnabled(false);


        DataBaseManager dbm =new DataBaseManager(getApplicationContext());
        ArrayList<Recette> recettes = dbm.getAllRecettes();
        ArrayAdapter<Recette> aar = new ArrayAdapter<Recette>(getApplicationContext(), android.R.layout.simple_list_item_1, recettes);

        lv.setAdapter(aar);


    }
}
