package com.uqac.informatiquemobile.epicerie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paull on 27/04/2016.
 */
public class IngredientCoursesAdapter extends ArrayAdapter<Ingredient> {

    public ArrayList<CheckBox> cb;


    public IngredientCoursesAdapter(Context context, int textViewResourceId, List<Ingredient> ingredients) {
        super(context,  textViewResourceId,ingredients);
        cb = new ArrayList<>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_courses, null);
        }

        Ingredient p = getItem(position);

        if (p != null) {
            TextView ttn = (TextView) v.findViewById(R.id.name);
            TextView ttq = (TextView) v.findViewById(R.id.qte);
            TextView ttp = (TextView) v.findViewById(R.id.pu);
            cb.add((CheckBox) v.findViewById(R.id.cb));
            TableRow tr1=(TableRow) v.findViewById(R.id.TableRow01);
            TableRow tr2=(TableRow) v.findViewById(R.id.TableRow02);




            if (ttn != null) {
                ttn.setText(p.getNom());

            }

            if (ttq != null) {
                ttq.setText(""+p.getQuantite()+" "+p.getUnite().getSymbol());
            }

            if (ttp != null) {
                ttp.setText("prix unitaire : "+p.getPrix());
            }
        }

        //Toast.makeText(getContext(), "WOLOLO", Toast.LENGTH_SHORT).show();


        return v;
    }

}
