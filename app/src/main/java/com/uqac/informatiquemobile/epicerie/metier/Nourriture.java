package com.uqac.informatiquemobile.epicerie.metier;

/**
 * Created by Jeremy on 2016-02-13.
 */
public abstract class Nourriture {

    private String nom;
    private int id;
    private Float quantite;

    // Constructeur

    public Nourriture(String nom){
        setNom(nom);
    }
    public Nourriture( int id, String nom) {
        setId(id);
        setNom(nom);
    }
    public Nourriture(String nom, float qte){
        setNom(nom);
        setQuantite(qte);
    }
    public Nourriture(int id, String nom, float qte){
        setId(id);
        setNom(nom);
        setQuantite(qte);
    }



    // Accesseurs
    public String getNom() {return nom;}
    public void setNom(String value) {nom=value;}
    public abstract int getPrix();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQuantite() {
        return quantite;
    }

    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }
}
