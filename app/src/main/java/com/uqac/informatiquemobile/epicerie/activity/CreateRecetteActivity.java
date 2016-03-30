package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

/**
 * Activité qui permet de créer une recette
 */
public class CreateRecetteActivity extends Activity {

    EditText editTextNomRecette, editTextDescRecette;
    Button boutonAjouterIngredient, boutonAjouterRecette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_activity);


        editTextNomRecette = (EditText)findViewById(R.id.editTextName);
        editTextDescRecette = (EditText)findViewById(R.id.editTextDesc);
        boutonAjouterIngredient = (Button)findViewById(R.id.buttonAdd);
        boutonAjouterRecette = (Button)findViewById(R.id.buttonCreate);

        boutonAjouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomRecette.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de la recette", Toast.LENGTH_SHORT).show();
                }  else {
                    Ingredient retour = new Ingredient(editTextNomRecette.getText().toString(), Integer.parseInt(editTextDescRecette.getText().toString()), 1);
                    Bundle data = new Bundle();
                    data.putString("ingredient", new Gson().toJson(retour));
                    Intent intent = new Intent();
                    intent.putExtras(data);


                    System.out.println(Integer.parseInt(editTextDescRecette.getText().toString()));

                    setResult(666, intent);

                    finish();
                }
            }
        });

    }
}
