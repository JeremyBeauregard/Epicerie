package com.uqac.informatiquemobile.epicerie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;

import java.util.ArrayList;

/**
 * Created by Guillaume2 on 30/03/2016.
 */
public class ListRecettesActivity extends Activity {
    private DataBaseManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recettes_activity);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), CreateRecetteActivity.class);
                startActivity(i);
            }
        });

        dbm = new DataBaseManager(getApplicationContext());



    }
}
