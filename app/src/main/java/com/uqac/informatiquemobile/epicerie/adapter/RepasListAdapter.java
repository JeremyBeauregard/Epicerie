package com.uqac.informatiquemobile.epicerie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Repas;

import java.util.Calendar;
import java.util.List;

/**
 * Created by paull on 19/04/2016.
 */
public class RepasListAdapter extends ArrayAdapter<Repas> {
    boolean changeColors;
    DataBaseManager dbm;

    public RepasListAdapter(Context context, int textViewResourceId, List<Repas> repas, boolean changeColors) {
        super(context, textViewResourceId, repas);
        this.changeColors = changeColors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_row, null);
        }

        Repas repas = getItem(position);


        if (repas != null) {
            TextView tvNom = (TextView) v.findViewById(R.id.name);
            TextView tvDate = (TextView) v.findViewById(R.id.qte);
            TextView ttp = (TextView) v.findViewById(R.id.pu);

            if (tvNom != null) {
                tvNom.setText(repas.getRecette().getNom());
            }

            if (tvDate != null) {
                //System.out.println("MOIS : "+repas.getDatePreparation().getMonth());

                Calendar cal = Calendar.getInstance();
                cal.setTime(repas.getDatePreparation());

                //Toast.makeText(getContext(), cal.toString(), Toast.LENGTH_SHORT).show();

                int jour  = cal.get(Calendar.DAY_OF_MONTH);




                tvDate.setText("le "+jour + "/" + ((repas.getDatePreparation().getMonth())+1) + "/" + (repas.getDatePreparation().getYear()+1900)+" à "+repas.getDatePreparation().getHours()+":"+repas.getDatePreparation().getMinutes());
            }


                ttp.setText("");

        }

        return v;
    }
}
