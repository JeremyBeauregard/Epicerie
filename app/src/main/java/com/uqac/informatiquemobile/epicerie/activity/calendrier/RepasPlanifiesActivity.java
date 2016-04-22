package com.uqac.informatiquemobile.epicerie.activity.calendrier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.activity.recette.DetailedRecetteActivity;
import com.uqac.informatiquemobile.epicerie.adapter.RepasListAdapter;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Recette;
import com.uqac.informatiquemobile.epicerie.metier.Repas;

import java.util.ArrayList;

/**
 * Created by paull on 19/04/2016.
 */
public class RepasPlanifiesActivity extends Activity {

    RepasListAdapter rla;

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
        System.out.println("Chargement des repas");
        ArrayList<Repas> repasPlanifies = dbm.getRepasPlanifies();
        System.out.println("Repas chargés");
        Toast.makeText(getApplicationContext(), repasPlanifies.toString(), Toast.LENGTH_SHORT);
        rla = new RepasListAdapter(getApplicationContext(), R.layout.item_list_row, repasPlanifies, false);

        listViewRepas.setAdapter(rla);
        listViewRepas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recette recette = ((Repas)(parent.getItemAtPosition(position))).getRecette();

                Intent i = new Intent(getApplicationContext(), DetailedRecetteActivity.class);
                i.putExtra("id", recette.getId());
                startActivity(i);

            }
        });
        /*System.out.println("Lancement service...");
        Intent i = new Intent(getApplicationContext(), DataBaseManager.class);
        startService(i);*/


        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



    }

    @Override
    protected void onResume() {
        super.onResume();
        rla.notifyDataSetChanged();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
