package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;

import java.sql.Date;

/**
 * Created by paull on 16/04/2016.
 */
public class PlanifierRepasActivity extends Activity {

    Button selectionRecette, planifierRepas;
    DatePicker datePicker;
    TimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planification_recette_activity);

        selectionRecette = (Button) findViewById(R.id.buttonSelection);
        planifierRepas = (Button)findViewById(R.id.buttonPlanifier);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);


        selectionRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelectRecetteActivity.class);
                startActivityForResult(i, 123);
            }
        });

        planifierRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String jour = String.valueOf(datePicker.getDayOfMonth())+"-"+String.valueOf(datePicker.getMonth())+"-"+String.valueOf(datePicker.getYear());
                Date date = new Date(datePicker.getYear()-1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                Toast.makeText(getApplication(), date.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
