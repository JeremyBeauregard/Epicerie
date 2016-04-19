package com.uqac.informatiquemobile.epicerie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.activity.calendrier.PlanifierRepasActivity;
import com.uqac.informatiquemobile.epicerie.activity.courses.ListeCourses;
import com.uqac.informatiquemobile.epicerie.activity.ingredient.ListFrigoActivity;
import com.uqac.informatiquemobile.epicerie.activity.recette.ListRecettesActivity;

/**
 * Created by paull on 29/03/2016.
 */
public class MainMenu extends AppCompatActivity {

    private Button buttonProvisions, buttonCourses, buttonCalendrier, buttonRecettes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu_activity);
        buttonProvisions = (Button)findViewById(R.id.buttonFrigo);
        buttonRecettes= (Button)findViewById(R.id.buttonRecettes);
        buttonCalendrier= (Button)findViewById(R.id.buttonCalendrier);
        buttonCourses= (Button)findViewById(R.id.buttonCourses);

        //buttonRecettes.setEnabled(false);
        //buttonCalendrier.setEnabled(false);
        //buttonCourses.setEnabled(false);

        buttonProvisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListFrigoActivity.class);
                startActivity(i);
            }
        });

        buttonCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListeCourses.class);
                startActivity(i);
            }
        });

        buttonRecettes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i =new Intent(getApplicationContext(),ListRecettesActivity.class);
                startActivity(i);

            }
        });

        buttonCalendrier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i =new Intent(getApplicationContext(),PlanifierRepasActivity.class);
                startActivity(i);

            }
        });


    }
}
