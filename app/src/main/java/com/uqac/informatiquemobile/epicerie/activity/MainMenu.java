package com.uqac.informatiquemobile.epicerie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.uqac.informatiquemobile.epicerie.R;

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

        buttonRecettes.setEnabled(false);
        buttonCalendrier.setEnabled(false);
        buttonCourses.setEnabled(false);

        buttonProvisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListIngredientActivity.class);
                startActivity(i);
            }
        });



    }
}
