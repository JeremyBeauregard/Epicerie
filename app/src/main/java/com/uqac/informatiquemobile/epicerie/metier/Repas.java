package com.uqac.informatiquemobile.epicerie.metier;

import java.util.Date;

/**
 * Created by paull on 17/04/2016.
 */
public class Repas {
    private Recette recette;
    private Date datePreparation;

    public Repas(Recette recette, Date date){
        this.recette = recette;
        this.datePreparation = date;
    }

    public Date getDatePreparation() {
        return datePreparation;
    }

    public Recette getRecette() {
        return recette;
    }
}
