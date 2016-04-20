package com.uqac.informatiquemobile.epicerie.activity.recette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.adapter.RecetteListAdapter;
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
        RecetteListAdapter aar = new RecetteListAdapter(getApplicationContext(), R.layout.item_list_row, recettes, true);

        lv.setAdapter(aar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),(parent.getItemAtPosition(position)).toString(),Toast.LENGTH_SHORT).show();

                Bundle data = new Bundle();
                data.putString("recette", new Gson().toJson(parent.getItemAtPosition(position)));
                Intent intent = new Intent();
                intent.putExtras(data);
                setResult(123, intent);

                finish();
            }
        });




    }
}
