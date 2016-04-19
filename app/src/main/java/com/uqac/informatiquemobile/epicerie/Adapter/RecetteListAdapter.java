package com.uqac.informatiquemobile.epicerie.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import com.uqac.informatiquemobile.epicerie.metier.Ingredient;
import com.uqac.informatiquemobile.epicerie.metier.Recette;

import java.util.List;

/**
 * Created by paull on 17/04/2016.
 */
public class RecetteListAdapter extends ArrayAdapter<Ingredient> {
    public RecetteListAdapter(Context context, int resource,List<Recette> recettes) {
        super(context, resource);


    }

    public View getView(int position, View convertView)


}
