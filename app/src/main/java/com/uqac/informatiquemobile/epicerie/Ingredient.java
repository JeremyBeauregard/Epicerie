package com.uqac.informatiquemobile.epicerie;

/**
 * Created by Jeremy on 2016-02-13.
 */
public class Ingredient extends Nourriture {

    // Variables locales
    float _prixUnitaire;

    // Constructeur
    public Ingredient(String nom, float prixUnitaire) {
        super(nom);
        setPrix(prixUnitaire);
    }

    // Accesseurs
    @Override public float getPrix() {return _prixUnitaire;}
    private void setPrix(float value) {_prixUnitaire=value;}

}
