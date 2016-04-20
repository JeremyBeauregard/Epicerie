package com.uqac.informatiquemobile.epicerie.metier;

import java.util.*;

/**
 * Created by Jeremy on 2016-02-13.
 */
public class Recette extends Nourriture {

    // Variables locales
    private ArrayList<Nourriture> composition;
    private String description;


    // Constructeur
    public Recette(String nom, ArrayList<Nourriture> composition, String description) {
        super(nom);
        this.composition = composition;
        this.description=description;
    }
    public Recette(String nom, ArrayList<Nourriture> composition, int id, String description){
        super(id,nom);
        this.composition = composition;
        this.description=description;


    }

    public ArrayList<Nourriture> getComposition(){
        return this.composition;
    }
    // Retourne le prix de la recette
    @Override public int getPrix() {

        int prix=0;

        // Parcourt la composition pour trouver la quantité et le prix de chaque item
        for(Nourriture nourr : composition) {


            float qte = nourr.getQuantite();

            prix += nourr.getPrix() * qte;

        }

        return prix;

    }

    // Ajoute un item à la recette
    public void addItem(Nourriture item) {
        composition.add(item);
    }


    @Override
    public String toString() {
        return this.getId()+":"+this.getNom();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
