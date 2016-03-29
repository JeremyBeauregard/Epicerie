package com.uqac.informatiquemobile.epicerie.metier;

/**
 * Classe qui definit une sorte d'ingredient selon son prix son unite et sa quantite.
 */
public class Ingredient extends Nourriture {

    // Variables locales
    int _prixUnitaire;
    Unite unite;
    int quantite;

    // Constructeurs
    public Ingredient(String nom, int prixUnitaire) {
        super(nom);
        setPrix(prixUnitaire);
    }
    public Ingredient(String nom, int prixUnitaire, int quantite) {
        super(nom);
        setPrix(prixUnitaire);
        this.quantite = quantite;
    }
    public Ingredient(String nom, int prixUnitaire, Unite u) {
        super(nom);
        this.unite = u;
        setPrix(prixUnitaire);
    }



    // Accesseurs
    @Override public int getPrix() {return _prixUnitaire;}
    public int getPrixTotal() {return _prixUnitaire*quantite;}
    private void setPrix(int value) {_prixUnitaire=value;}
    public int getQuantite(){return this.quantite;}

    @Override
    public String toString() {
        return this.getNom()+" | Q : "+this.getQuantite()+" | PU : "+this.getPrix();
    }
}
