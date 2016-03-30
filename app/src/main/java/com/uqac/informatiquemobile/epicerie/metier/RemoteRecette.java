package com.uqac.informatiquemobile.epicerie.metier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by paull on 30/03/2016.
 */
public class RemoteRecette {
    private String libelle;
    private String lien;
    private Document page;
    private Element contenu;

    private String prepTime;
    private String cookingTime;
    private String details;
    private String ingredients;


    public RemoteRecette(String libelle, String lien)  {

        this.libelle = libelle;
        this.lien = lien;


    }

    public void chargerInfosRecette()throws IOException {
        page = Jsoup.connect(this.getLien()).get();
        contenu = page.getElementsByClass("m_content_recette_main").first();

        //System.out.println(contenu);

        prepTime = (contenu.getElementsByClass("preptime").first().getElementsByClass("value-title").first().attr("title")).substring(2);
        cookingTime = (contenu.getElementsByClass("cooktime").first().getElementsByClass("value-title").first().attr("title")).substring(2);
        details = contenu.getElementsByClass("m_content_recette_todo").first().text();
//        ingredients = contenu.getElementsByClass("m_content_recette_ingredients").first().text();

    }

    public String getLibelle() {
        return libelle;
    }

    public String getLien() {
        return lien;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getDetails(){
        return details;
    }

    public void setPage(Document page) {
        this.page = page;
    }

    public void setContenu(Element contenu) {
        this.contenu = contenu;
    }
    public String getIngredients (){
        return this.ingredients;
    }

    @Override
    public String toString() {
        return "RemoteRecette{" +"\n"+
                "libelle='" + libelle + '\'' +"\n"+
                ", lien='" + lien + '\'' +"\n"+
                ", prepTime='" + prepTime + '\'' +"\n"+
                ", cookingTime='" + cookingTime + '\'' +"\n"+
                ", details='" + details + '\'' +"\n"+
                ", ingredients='" + ingredients + '\'' +"\n"+
                '}';
    }
}
