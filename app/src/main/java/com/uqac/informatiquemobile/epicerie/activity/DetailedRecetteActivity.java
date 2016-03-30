package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class DetailedRecetteActivity extends Activity {

    private ListView listViewIngredient;
    private TextView textViewName;
    private TextView textViewDesc;
    private Button buttonMod;
    private Button buttonDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_recette_activity);




    }
}
