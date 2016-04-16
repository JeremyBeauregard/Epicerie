package com.uqac.informatiquemobile.epicerie.metier;

import java.util.*;
import java.util.Map.*;

/**
 * Created by Jeremy on 2016-02-13.
 */
public class Recette extends Nourriture {

    // Variables locales
    private HashMap<Nourriture, Float> composition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


    // Constructeur
    public Recette(String nom, HashMap<Nourriture, Float> composition) {
        super(nom);
        this.composition = composition;
    }
    public Recette(String nom, HashMap<Nourriture, Float> composition, int id){
        super(nom);
        this.composition = composition;
        this.id=id;

    }

    public HashMap<Nourriture, Float> getComposition(){
        return this.composition;
    }
    // Retourne le prix de la recette
    @Override public int getPrix() {

        int prix=0;

        // Parcourt la composition pour trouver la quantité et le prix de chaque item
        for(Entry<Nourriture, Float> entry : composition.entrySet()) {

            Nourriture value = entry.getKey();
            float qte = entry.getValue();

            prix += value.getPrix() * qte;

        }

        return prix;

    }

    // Ajoute un item à la recette
    public void addItem(Nourriture item, float quantite) {
        composition.put(item, quantite);
    }


    @Override
    public String toString() {
        return this.getId()+":"+this.getNom();
    }
}
