package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

/**
 * Created by paull on 20/03/2016.
 */
public class AddIngredientActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ingredient retour = new Ingredient("Ingredient Retour", 50);
        Bundle data = new Bundle();
        data.putString("ingredient", new Gson().toJson(retour));
        Intent intent = new Intent();
        intent.putExtras(data);

        setResult(666, intent);

        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();




    }
}
