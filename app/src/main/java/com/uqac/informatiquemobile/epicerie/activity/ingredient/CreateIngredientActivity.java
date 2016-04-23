package com.uqac.informatiquemobile.epicerie.activity.ingredient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

/**
 * Activite qui permet d'ajouter un ingredient au frigo.
 */
public class CreateIngredientActivity extends Activity {

    EditText editTextNomIngredient, editTextPrixIngredient;
    LinearLayout boutonAjouterIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ingredient_activity);





        editTextNomIngredient= (EditText)findViewById(R.id.editTextNomIngredient);
        editTextPrixIngredient = (EditText)findViewById(R.id.editTextPrixIngredient);
        boutonAjouterIngredient = (LinearLayout)findViewById(R.id.boutonAjouter);

        boutonAjouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomIngredient.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de l'ingredient", Toast.LENGTH_SHORT).show();
                } else if (editTextPrixIngredient.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le prix de l'ingredient", Toast.LENGTH_SHORT).show();
                } else {
                    Ingredient retour = new Ingredient(editTextNomIngredient.getText().toString(), Integer.parseInt(editTextPrixIngredient.getText().toString()),1);

                    DataBaseManager dbm =new DataBaseManager(getApplicationContext());
                    dbm.addIngredient(retour);


                    System.out.println(Integer.parseInt(editTextPrixIngredient.getText().toString()));



                    finish();
                }
            }
        });


        //overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_fade);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.slide_in_fade, R.anim.slide_out_top);
    }
}
