package com.uqac.informatiquemobile.epicerie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.List;

/**
 * Created by guilo on 18/04/16.
 */
public class RecetteListAdapter extends ArrayAdapter<Recette> {

    boolean checkmissing;
    DataBaseManager dbm;

    public RecetteListAdapter(Context context, int textViewResourceId, List<Recette> recettes, boolean checkmissing) {
        super(context, textViewResourceId, recettes);
        this.checkmissing =checkmissing;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_recette_layout, null);
        }

        Recette r = getItem(position);

        if (r != null) {

            TextView ttn = (TextView) v.findViewById(R.id.name);
            TextView tta = (TextView) v.findViewById(R.id.availability);
            TableRow trn=(TableRow) v.findViewById(R.id.TableRowname);
            TableRow tra=(TableRow) v.findViewById(R.id.TableRowavalability);



            if(checkmissing){
                dbm=new DataBaseManager(getContext());
                int dispo=dbm.recetteIsAvailable(r);


                switch (dispo){
                    case -1:
                        tta.setText(" Vous n'avez aucun des ingrédients nécessaires");
                        trn.setBackgroundResource(R.color.missing);
                        tra.setBackgroundResource(R.color.missing);

                        break;
                    case 0:
                        tta.setText(" Vous pouvez faire cette recette");
                        trn.setBackgroundResource(R.color.available);
                        tra.setBackgroundResource(R.color.available);
                        break;
                    case 1:
                        tta.setText("Il manque "+dispo+" ingrédient");
                        trn.setBackgroundResource(R.color.incomplete);
                        tra.setBackgroundResource(R.color.incomplete);
                        break;
                    default:
                        tta.setText("Il manque  "+dispo+" ingrédients");
                        tra.setBackgroundResource(R.color.incomplete);
                        trn.setBackgroundResource(R.color.incomplete);

                }
            }



            if (ttn != null) {
                ttn.setText(r.getNom());

            }
            if (tta != null) {


            }


        }

        return v;
    }

}