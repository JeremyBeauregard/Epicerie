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
                tvDate.setText("" + repas.getDatePreparation().getDay() + "/" + repas.getDatePreparation().getMonth() + "/" + repas.getDatePreparation().getYear());
            }


                ttp.setText("");

        }

        return v;
    }
}
