package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class DetailedRecetteActivity extends Activity {
    private DataBaseManager dbm ;
    private ListView listViewIngredient;
    private TextView textViewName;
    private TextView textViewDesc;
    private Button buttonMod;
    private Button buttonDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_recette_activity);

        int id = getIntent().getExtras().getInt("id");
        dbm = new DataBaseManager(getApplicationContext());
        Recette recette= dbm.getRecetteById(id);

        textViewName = (TextView) findViewById(R.id.textViewName);
        buttonMod = (Button) findViewById(R.id.buttonMod);

        textViewName.setText(recette.getNom());

        buttonMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
