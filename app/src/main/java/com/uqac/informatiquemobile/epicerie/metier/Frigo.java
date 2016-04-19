package com.uqac.informatiquemobile.epicerie.metier;

import java.util.ArrayList;

/**
 * Created by paull on 15/03/2016.
 */
public class Frigo {
    private ArrayList<Ingredient> contenu;

    public Frigo(){
        this.contenu = new ArrayList<>();
    }

    /**
     * Methode qui permet d'ajouter un ingredient au contenu du frigo.
     * @param ingredient
     */
    public void ajouterIngredient(Ingredient ingredient){
        this.contenu.add(ingredient);
    }
    public void ajouterIngredient(ArrayList<Ingredient> ingredients){
        for (Ingredient i: ingredients) {
            this.ajouterIngredient(i);
        }
    }
    public void ajouterIngredient(Ingredient ingredient, int quantite){
        for (int i = 0 ; i<quantite;i++){
            this.ajouterIngredient(ingredient);
        }
    }




    public void retirerIngredient(Ingredient ingredient, int quantite){
        for (int i = 0 ; i<quantite;i++){
            this.retirerIngredient(ingredient);
        }
    }
    public void retirerIngredient(Ingredient ingredient){
        this.contenu.remove(ingredient);
    }

}
