package com.uqac.informatiquemobile.epicerie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.List;

/**
 * Created by guilo on 16/04/16.
 */
public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    public IngredientListAdapter (Context context, int textViewResourceId,List<Ingredient> ingredients){
        super(context, textViewResourceId,ingredients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_row, null);
        }

        Ingredient p = getItem(position);

        if (p != null) {
            TextView ttn = (TextView) v.findViewById(R.id.name);
            TextView ttq = (TextView) v.findViewById(R.id.qte);
            TextView ttp = (TextView) v.findViewById(R.id.pu);

            if (ttn != null) {
                ttn.setText(p.getNom());
            }

            if (ttq != null) {
                ttq.setText(""+p.getQuantite());
            }

            if (ttp != null) {
                ttp.setText(""+p.getPrix());
            }
        }

        return v;
    }
}
