package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.metier.RemoteRecette;

/**
 * Created by paull on 30/03/2016.
 */
public class AfficherRecetteRecherche extends Activity {

    private TextView textViewTitreRecette;
    private TextView textViewTempsPreparation;
    private TextView textViewTempsCuisson;

    private EditText editTextIngredients;
    private EditText editTextDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat_recherche_activity);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String jsonRemoteRecette;

        if(savedInstanceState==null){
            Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_SHORT ).show();
        }

        try{

            jsonRemoteRecette = extras.getString("recette");

            RemoteRecette recette= new Gson().fromJson(jsonRemoteRecette, RemoteRecette.class);



            textViewTitreRecette = (TextView)findViewById(R.id.textViewTitreRecette);
            textViewTitreRecette.setText(recette.getLibelle());

            textViewTempsCuisson = (TextView)findViewById(R.id.textViewTempsCuisson);
            textViewTempsCuisson.setText("Temps de cuisson :"+recette.getCookingTime());

            textViewTempsPreparation = (TextView)findViewById(R.id.textViewTempsPreparation);
            textViewTempsPreparation.setText("Temps de préparation : "+recette.getPrepTime());

            editTextDetails = (EditText)findViewById(R.id.editTextDetails);
            editTextDetails.setText(recette.getDetails());
            editTextDetails.setEnabled(true);
            editTextDetails.setMaxLines(10);
            editTextDetails.setVerticalScrollBarEnabled(true);
            editTextDetails.setMovementMethod(new ScrollingMovementMethod());

            editTextIngredients = (EditText)findViewById(R.id.editTextIngredients);
            editTextIngredients.setText(recette.getIngredients());
            editTextIngredients.setEnabled(false);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
