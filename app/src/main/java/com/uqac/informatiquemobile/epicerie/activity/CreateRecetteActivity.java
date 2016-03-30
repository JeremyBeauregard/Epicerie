package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Nourriture;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.HashMap;

/**
 * Activité qui permet de créer une recette
 */
public class CreateRecetteActivity extends Activity {
    private HashMap<Nourriture, Float> composition; //update à l'ajout d'ingrédients

    EditText editTextNomRecette, editTextDescRecette;
    Button boutonAjouterIngredient, boutonAjouterRecette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recette);


        editTextNomRecette = (EditText)findViewById(R.id.editTextName);
        editTextDescRecette = (EditText)findViewById(R.id.editTextDesc);
        boutonAjouterIngredient = (Button)findViewById(R.id.buttonAdd);
        boutonAjouterRecette = (Button)findViewById(R.id.buttonCreate);

        boutonAjouterRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomRecette.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de la recette", Toast.LENGTH_SHORT).show();
                }  else {


                    Recette recette= new Recette(editTextNomRecette.getText().toString(),composition);
                    DataBaseManager dbm = new DataBaseManager(getApplicationContext());
                    dbm.addRecette(recette);


                    finish();
                }
            }
        });

    }
}
