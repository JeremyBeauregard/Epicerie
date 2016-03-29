package com.uqac.informatiquemobile.epicerie.metier;

/**
 * Created by Jeremy on 2016-02-13.
 */
public abstract class Nourriture {

    private String nom;

    // Constructeur
    public Nourriture(String nom) {
        setNom(nom);
    }


    // Accesseurs
    public String getNom() {return nom;}
    private void setNom(String value) {nom=value;}
    public abstract int getPrix();



}
