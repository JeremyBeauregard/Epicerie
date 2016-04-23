package com.uqac.informatiquemobile.epicerie.activity.ingredient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.adapter.UniteSpinnerAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Unite;

import java.util.ArrayList;

/**
 * Activite qui permet d'ajouter un ingredient au frigo.
 */
public class CreateIngredientActivity extends Activity {

    EditText editTextNomIngredient, editTextPrixIngredient;
    Button boutonAjouterIngredient;
    Spinner spinnerUnite;
    UniteSpinnerAdapter uniteSpinnerAdapter;
    ArrayList<Unite> listUnites;
    DataBaseManager dbm;
    Unite uniteAjout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ingredient_activity);





        editTextNomIngredient= (EditText)findViewById(R.id.editTextNomIngredient);
        editTextPrixIngredient = (EditText)findViewById(R.id.editTextPrixIngredient);
        boutonAjouterIngredient = (Button)findViewById(R.id.boutonAjouter);

        spinnerUnite=(Spinner)findViewById(R.id.spinnerUnite);
        dbm=new DataBaseManager(getApplicationContext());
        listUnites=dbm.getAllUnites();


        uniteSpinnerAdapter=new UniteSpinnerAdapter(getApplicationContext(),R.layout.create_ingredient_activity,listUnites);
        spinnerUnite.setAdapter(uniteSpinnerAdapter);


        boutonAjouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNomIngredient.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le nom de l'ingredient", Toast.LENGTH_SHORT).show();
                } else if (editTextPrixIngredient.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer le prix de l'ingredient", Toast.LENGTH_SHORT).show();
                } else {
                    boolean succes;
                    uniteAjout=listUnites.get(spinnerUnite.getSelectedItemPosition());
                    Ingredient retour = new Ingredient(editTextNomIngredient.getText().toString(), Integer.parseInt(editTextPrixIngredient.getText().toString()),uniteAjout);

                    DataBaseManager dbm =new DataBaseManager(getApplicationContext());
                    succes =dbm.addIngredient(retour);

                    if(succes){
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(), "Cet ingredient existe déjà, changez de nom", Toast.LENGTH_SHORT).show();
                    }






                }
            }
        });

    }
}
