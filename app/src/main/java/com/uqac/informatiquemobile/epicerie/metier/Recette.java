package com.uqac.informatiquemobile.epicerie.metier;

import java.util.*;

/**
 * Created by Jeremy on 2016-02-13.
 */
public class Recette extends Nourriture {

    // Variables locales
    private ArrayList<Nourriture> composition;


    // Constructeur
    public Recette(String nom, ArrayList<Nourriture> composition) {
        super(nom);
        this.composition = composition;
    }
    public Recette(String nom, ArrayList<Nourriture> composition, int id){
        super(id,nom);
        this.composition = composition;


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



}
