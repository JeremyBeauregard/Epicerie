package com.uqac.informatiquemobile.epicerie.activity.calendrier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Repas;

import java.util.ArrayList;

/**
 * Created by paull on 19/04/2016.
 */
public class RepasPlanifiesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repas_planifies_activity);


        ListView listViewRepas = (ListView)findViewById(R.id.listViewRepasPlanifies);
        Button boutonPlanifierRepas = (Button)findViewById(R.id.buttonOuvrirPlanification);

        boutonPlanifierRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),PlanifierRepasActivity.class);
                startActivity(i);
            }
        });

        DataBaseManager dbm = new DataBaseManager(getApplicationContext());
        ArrayList<Repas> repasPlanifies = dbm.getRepasPlanifies();
        Toast.makeText(getApplicationContext(), repasPlanifies.toString(), Toast.LENGTH_SHORT);


    }
}
