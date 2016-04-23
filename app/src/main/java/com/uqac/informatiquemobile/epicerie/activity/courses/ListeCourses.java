package com.uqac.informatiquemobile.epicerie.activity.courses;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.activity.ingredient.AddIngredientActivity;
import com.uqac.informatiquemobile.epicerie.adapter.IngredientListAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;

/**
 * Created by paull on 29/03/2016.
 */
public class ListeCourses extends Activity {

    private DataBaseManager dbm;
    private ListView listViewIngredients;
    private Button ajouterAuFrigo;
    private Button ajouterIngredient;
    private ArrayList<Ingredient> ingredients;
    private IngredientListAdapter adapter;
    private int total = 0;
    TextView textViewPrix;
    String prixTotal;

    private ArrayList<Ingredient> selectedIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_courses_activity);

        dbm = new DataBaseManager(getApplicationContext());

        //////////////////////////////////////////////////

        ingredients = new ArrayList<Ingredient>();
        /*ingredients.put("Baguette", new Ingredient("Baguette", 50, 3));
        ingredients.put("Tomate", new Ingredient("Tomate", 25, 3));
        ingredients.put("Patate",new Ingredient("Patate", 10, 3));*/

        //////////////////////////////////////////////////

        textViewPrix = (TextView)findViewById(R.id.textViewPrix);
        textViewPrix.setText("Prix : 0,00$");

        listViewIngredients = (ListView)findViewById(R.id.listViewIngredients);
        //listViewIngredients.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //titres = new ArrayList(ingredients.keySet());

        adapter = new IngredientListAdapter(getApplicationContext(), R.layout.item_list_row, ingredients, false);
        listViewIngredients.setAdapter(adapter);

        listViewIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(), "-"+nom+"-", Toast.LENGTH_SHORT).show();
                Ingredient selectedIngredient = ingredients.get(position);
                //Toast.makeText(getApplicationContext(), selectedIngredient.toString(), Toast.LENGTH_SHORT).show();
                if (selectedIngredients.contains(selectedIngredient)) {
                    selectedIngredients.remove(selectedIngredient);
                    total-=selectedIngredient.getPrixTotal();
                } else {
                    selectedIngredients.add(selectedIngredient);
                    //Toast.makeText(getApplicationContext(), selectedIngredient.getNom(), Toast.LENGTH_SHORT).show();
                    total+=selectedIngredient.getPrixTotal();
                }
                prixTotal = "Prix : "+(double)total/100 + "$";
                textViewPrix.setText(prixTotal);
            }
        });

        ajouterAuFrigo = (Button)findViewById(R.id.buttonAjouterAuFrigo);
        ajouterAuFrigo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (Ingredient i : selectedIngredients) {

                    dbm.addIngredientFrigo(i);

                    for (int x = 0; x < ingredients.size(); x++) {

                        if (i.getNom().equals(ingredients.get(x))) {
                            //titres.remove(x);
                        } else {
                            //Toast.makeText(getApplicationContext(), i.getNom().toString() + (titres.get(x).toString()), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), titres.toString(), Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();





            }
        });


        ajouterIngredient = (Button)findViewById(R.id.buttonAjouterIngredient);
        ajouterIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddIngredientActivity.class);
                startActivityForResult(i, 123);
            }
        });






        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    /**
     * Recuperation de l'ingredient à ajouter.
     * @param requestCode
     * @param resultCode
     * @param data Gson de l'ingredient à ajouter.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            if(requestCode == 123){

                String jsonIngredient;

                SharedPreferences sp = getSharedPreferences("listeCourses", BIND_AUTO_CREATE);

                String jsonIngredient2 = sp.getString("ingredients", null);

                if (jsonIngredient2==null||ingredients == null){
                    //Toast.makeText(getApplicationContext(), "VIDE", Toast.LENGTH_SHORT).show();
                    ingredients = new ArrayList<>();
                    System.out.println("vide");
                } else {
                    System.out.println(jsonIngredient2);
                    //Toast.makeText(getApplicationContext(), jsonIngredient2, Toast.LENGTH_SHORT).show();
                    ingredients = new Gson().fromJson(jsonIngredient2, ArrayList.class);
                    System.out.println("non vide");
                    System.out.println("ingredient : "+jsonIngredient2.toString());
                }

                Bundle extras = data.getExtras();
                if (extras != null) {
                    jsonIngredient = extras.getString("ingredient");
                    //Toast.makeText(getApplicationContext(), jsonIngredient, Toast.LENGTH_SHORT).show();
                } else {
                    jsonIngredient = null;
                }
                Ingredient ingredientAjout = new Gson().fromJson(jsonIngredient, Ingredient.class);
                //Gson g = new Gson();

                //titres.add(ingredientAjout.getNom() /*+ " : " + ingredientAjout.getQuantite()*/);
                Toast.makeText(getApplicationContext(), ingredientAjout.toString(), Toast.LENGTH_SHORT).show();
                ingredients.add(ingredientAjout);
                Toast.makeText(getApplicationContext(), ingredients.toString(), Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();

                listViewIngredients.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "RESULT", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("ingredients", BIND_AUTO_CREATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String ing = new Gson().toJson(ingredients);

        editor.putString("ingredients", ing);
        Toast.makeText(getApplicationContext(), "ing = "+ing, Toast.LENGTH_SHORT).show();
        editor.commit();


        Toast.makeText(getApplicationContext(), "pref = "+sharedPreferences.getString("ingredients", null), Toast.LENGTH_SHORT).show();






    }


    @Override
    protected void onResume() {
        super.onResume();

        /*SharedPreferences sp = getSharedPreferences("ingredients", BIND_AUTO_CREATE);

        String jsonIngredient = sp.getString("ingredients", null);

        if (jsonIngredient==null&&ingredients.isEmpty()){
            //Toast.makeText(getApplicationContext(), "VIDE", Toast.LENGTH_SHORT).show();
//            ingredients = new ArrayList<>();
            System.out.println("vide");
        } else {
            //Toast.makeText(getApplicationContext(), jsonIngredient, Toast.LENGTH_SHORT).show();
            ingredients = new Gson().fromJson(jsonIngredient, ArrayList.class);
            System.out.println("non vide");
        }

        Toast.makeText(getApplicationContext(), "RESUME", Toast.LENGTH_SHORT).show();*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
