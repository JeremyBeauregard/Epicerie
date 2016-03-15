package com.uqac.informatiquemobile.epicerie.metier;

/**
 * Created by Jeremy on 2016-02-13.
 */
public class Ingredient extends Nourriture {

    // Variables locales
    float _prixUnitaire;
    Unite unite;

    // Constructeur
    public Ingredient(String nom, float prixUnitaire) {
        super(nom);
        setPrix(prixUnitaire);
    }

    public Ingredient(String nom, float prixUnitaire, Unite u) {
        super(nom);
        this.unite = u;
        setPrix(prixUnitaire);
    }



    // Accesseurs
    @Override public float getPrix() {return _prixUnitaire;}
    private void setPrix(float value) {_prixUnitaire=value;}

}
