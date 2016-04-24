package com.uqac.informatiquemobile.epicerie.metier;

import java.util.Date;

/**
 * Created by paull on 17/04/2016.
 */
public class Repas {
    private int id;
    private Recette recette;
    private Date datePreparation;

    public Repas(Recette recette, Date date){
        this.recette = recette;
        this.datePreparation = date;
    }
    public Repas(int id, Recette recette, Date date){
        this.recette = recette;
        this.datePreparation = date;
        this.id = id;
    }

    public Date getDatePreparation() {
        return datePreparation;
    }

    public Recette getRecette() {
        return recette;
    }

    public int getId(){
        return this.id;
    }
}
