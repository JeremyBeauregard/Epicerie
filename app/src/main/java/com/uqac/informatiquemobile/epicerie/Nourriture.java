package com.uqac.informatiquemobile.epicerie;

/**
 * Created by Jeremy on 2016-02-13.
 */
public abstract class Nourriture {

    private String _nom;

    // Constructeur
    public void Nourriture(String nom) {
        setNom(nom);
    }


    // Accesseurs
    public String getNom() {return _nom;}
    private void setNom(String value) {_nom=value;}
    public abstract Float getPrix();



}
