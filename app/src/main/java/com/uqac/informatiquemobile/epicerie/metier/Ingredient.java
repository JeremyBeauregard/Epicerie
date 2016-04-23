package com.uqac.informatiquemobile.epicerie.metier;

import java.io.Serializable;

/**
 * Classe qui definit une sorte d'ingredient selon son prix son unite et sa quantite.
 */
public class Ingredient extends Nourriture implements Serializable{

    // Variables locales
    private int _prixUnitaire;
    private Unite unite;


    // Constructeurs
    public Ingredient(String nom, int prixUnitaire) {
        super(nom);
        setPrix(prixUnitaire);
    }
    public Ingredient(String nom, int prixUnitaire, float quantite) {
        super(nom,quantite);
        setPrix(prixUnitaire);
    }
    public Ingredient(int id,String nom, int prixUnitaire, float quantite) {
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
    public Ingredient(int id, String nom){
        super(id,nom);
    }
    public Ingredient(int id, String nom, int prixUnitaire,Unite unite){
        super(id,nom);
        setPrix(prixUnitaire);
        setUnite(unite);
    }
    public Ingredient(int id,String nom, int prixUnitaire, float quantite,Unite unite) {
        super(id,nom,quantite);
        setPrix(prixUnitaire);
        setUnite(unite);


    }
    public Ingredient(String nom, int prixUnitaire, float quantite, Unite unite) {
        super(nom,quantite);
        setPrix(prixUnitaire);
        setUnite(unite);
    }




    // Accesseurs
    @Override public int getPrix() {return _prixUnitaire;}
    public float getPrixTotal() {return _prixUnitaire*this.getQuantite();}
    private void setPrix(int value) {_prixUnitaire=value;}


    @Override
    public String toString() {
        return this.getNom()+" | Q : "+this.getQuantite()+" | PU : "+this.getPrix();
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }
}
