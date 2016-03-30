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
 * Activite qui permet d'ajouter un ingredient au frigo.
 */
public class CreateIngredientActivity extends Activity {

    EditText editTextNomIngredient, editTextPrixIngredient;
    Button boutonAjouterIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_activity);


        editTextNomIngredient= (EditText)findViewById(R.id.editTextNomIngredient);
        editTextPrixIngredient = (EditText)findViewById(R.id.editTextPrixIngredient);
        boutonAjouterIngredient = (Button)findViewById(R.id.boutonAjouter);

        boutonAjouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomIngredient.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de l'ingredient", Toast.LENGTH_SHORT).show();
                } else if (editTextPrixIngredient.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le prix de l'ingredient", Toast.LENGTH_SHORT).show();
                } else {
                    Ingredient retour = new Ingredient(editTextNomIngredient.getText().toString(), Integer.parseInt(editTextPrixIngredient.getText().toString()),1);
                    Bundle data = new Bundle();
                    data.putString("ingredient", new Gson().toJson(retour));
                    Intent intent = new Intent();
                    intent.putExtras(data);


                    System.out.println(Integer.parseInt(editTextPrixIngredient.getText().toString()));

                    setResult(666, intent);

                    finish();
                }
            }
        });

    }
}
