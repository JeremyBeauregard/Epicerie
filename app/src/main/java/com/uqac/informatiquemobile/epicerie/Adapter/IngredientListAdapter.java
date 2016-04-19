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
import com.uqac.informatiquemobile.epicerie.metier.Ingredient;

import java.util.List;

/**
 * Created by guilo on 16/04/16.
 */
public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    boolean changeColors;
    DataBaseManager dbm;

    public IngredientListAdapter (Context context, int textViewResourceId,List<Ingredient> ingredients, boolean changeColors){
        super(context, textViewResourceId,ingredients);
        this.changeColors=changeColors;
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
            TableRow tr1=(TableRow) v.findViewById(R.id.TableRow01);
            TableRow tr2=(TableRow) v.findViewById(R.id.TableRow02);

            if (ttn != null) {
                ttn.setText(p.getNom());

            }

            if (ttq != null) {
                ttq.setText(""+p.getQuantite());

                if(changeColors){
                    dbm=new DataBaseManager(getContext());
                    int dispo=dbm.ingIsAvailable(p);
                    if (dispo==0){

                        tr1.setBackgroundResource(R.color.missing);
                        tr2.setBackgroundResource(R.color.missing);
                        System.out.println("change colors");
                    }else if (dispo==-1){
                        ttq.setTextColor(getContext().getResources().getColor(R.color.available));
                    }else if(dispo>0){

                        ttq.setText(""+p.getQuantite()+" manque "+dispo);
                        tr1.setBackgroundResource(R.color.incomplete);
                        tr2.setBackgroundResource(R.color.incomplete);
                    }


                }
            }

            if (ttp != null) {
                ttp.setText(""+p.getPrix());
            }
        }

        return v;
    }
}
