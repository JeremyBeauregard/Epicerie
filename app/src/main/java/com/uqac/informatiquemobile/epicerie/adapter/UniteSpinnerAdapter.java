package com.uqac.informatiquemobile.epicerie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Recette;
import com.uqac.informatiquemobile.epicerie.metier.Unite;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by guilo on 22/04/16.
 */
public class UniteSpinnerAdapter extends ArrayAdapter {


    public UniteSpinnerAdapter(Context context, int textViewResourceId, List<Unite> unites) {
        super(context, textViewResourceId, unites);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_recette_layout, null);
        }

        Unite u = (Unite)getItem(position);

        if (u != null) {

            TextView ttn = (TextView) v.findViewById(R.id.name);
            TextView tta = (TextView) v.findViewById(R.id.availability);


            if (ttn != null) {
                ttn.setText(u.getName());

            }
            if (tta != null) {
                tta.setText(u.getSymbol());

            }


        }

        return v;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_recette_layout, null);
        }

        Unite u = (Unite)getItem(position);

        if (u != null) {

            TextView ttn = (TextView) v.findViewById(R.id.name);
            TextView tta = (TextView) v.findViewById(R.id.availability);


            if (ttn != null) {
                ttn.setText(u.getName());

            }
            if (tta != null) {
                tta.setText(u.getSymbol());

            }


        }

        return v;
    }


}
