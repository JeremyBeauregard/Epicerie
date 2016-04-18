package com.uqac.informatiquemobile.epicerie.metier;

import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;

/**
 * Classe qui definit une sorte d'ingredient selon son prix son unite et sa quantite.
 */
public class Ingredient extends Nourriture {

    // Variables locales
    private int _prixUnitaire;
    private Unite unite;


    // Constructeurs
    public Ingredient(String nom, int prixUnitaire) {
        super(nom);
        setPrix(prixUnitaire);
    }
    public Ingredient(String nom, int prixUnitaire, int quantite) {
        super(nom,quantite);
        setPrix(prixUnitaire);
    }
    public Ingredient(int id,String nom, int prixUnitaire, int quantite) {
        super(id,nom,quantite);
        setPrix(prixUnitaire);

    }
    public Ingredient(String nom, int prixUnitaire, Unite u) {
        super(nom);
        this.unite = u;
        setPrix(prixUnitaire);
    }

    public Ingredient(int id,String nom, int prixUnitaire) {
        super(id,nom);
        setPrix(prixUnitaire);

    }


    // Accesseurs
    @Override public int getPrix() {return _prixUnitaire;}
    public int getPrixTotal() {return _prixUnitaire*this.getQuantite();}
    private void setPrix(int value) {_prixUnitaire=value;}


    @Override
    public String toString() {
        return this.getNom()+" | Q : "+this.getQuantite()+" | PU : "+this.getPrix();
    }


}
