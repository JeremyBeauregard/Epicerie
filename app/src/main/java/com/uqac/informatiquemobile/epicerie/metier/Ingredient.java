package com.uqac.informatiquemobile.epicerie.metier;

/**
 * Created by Jeremy on 2016-02-13.
 */
public class Ingredient extends Nourriture {

    // Variables locales
    int _prixUnitaire;
    Unite unite;

    // Constructeur
    public Ingredient(String nom, int prixUnitaire) {
        super(nom);
        setPrix(prixUnitaire);
    }

    public Ingredient(String nom, int prixUnitaire, Unite u) {
        super(nom);
        this.unite = u;
        setPrix(prixUnitaire);
    }



    // Accesseurs
    @Override public int getPrix() {return _prixUnitaire;}
    private void setPrix(int value) {_prixUnitaire=value;}

}
