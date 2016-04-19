package com.uqac.informatiquemobile.epicerie.activity.recette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqac.informatiquemobile.epicerie.R;
import com.uqac.informatiquemobile.epicerie.metier.RemoteRecette;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by paull on 30/03/2016.
 */
public class RechercheActivity extends Activity{

    Button buttonLancerRecherche;
    EditText editTextTermeRecherche;
    ListView listViewResultats;

    ArrayList<RemoteRecette> recettes;
    public Document doc = null;
    RemoteRecette recetteChoisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_activity);


        editTextTermeRecherche = (EditText)findViewById(R.id.editTextTermeRecherche);
        listViewResultats = (ListView)findViewById(R.id.listViewResultatsRecette);

        buttonLancerRecherche = (Button)findViewById(R.id.buttonLancerRecherche);
        buttonLancerRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String recherche = editTextTermeRecherche.getText().toString();
                if (recherche.equals("")||recherche==null){
                    Toast.makeText(getApplicationContext(), "Entrez un nom pour la recherche", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), recherche, Toast.LENGTH_SHORT).show();
                    recherche.replaceAll(" ", "-");


                        recettes = new ArrayList<>();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    doc = Jsoup.connect("http://www.marmiton.org/recettes/recherche.aspx?aqt=" + recherche).get();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Element listeResultats = doc.getElementsByClass("m_resultats_liste_recherche").first();
                                Elements resultats = listeResultats.getElementsByClass("recette_classique");

                                for (Element e:resultats) {
                                    Elements courant = e.getElementsByAttribute("title");
                                    String titre = courant.first().attr("title");
                                    String lien = "http://www.marmiton.org"+courant.first().attr("href");
                                    //System.out.println(titre+"\n"+lien+"\n\n");

                                    recettes.add(new RemoteRecette(titre, lien));

                                }

                                int i = 0;
                                for (RemoteRecette r : recettes) {
                                    System.out.println(r.getLibelle());
                                    i++;
                                }
                            }
                        });
                    t.start();


                    while (recettes.isEmpty()) {
                    }


                    Toast.makeText(getApplicationContext(), "Resultats OK", Toast.LENGTH_SHORT).show();

                    final HashMap<String, RemoteRecette> listeRecettes = new HashMap<String, RemoteRecette>();
                    for (RemoteRecette r : recettes) {
                        listeRecettes.put(r.getLibelle(), r);
                    }


                    ArrayList<String> titres  = new ArrayList(listeRecettes.keySet());
                    ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.resultat_recherche_layout, (List)titres);
                    listViewResultats.setAdapter(adapter);

                    listViewResultats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView tv = (TextView)view;
                            String nom = tv.getText().toString();

                            recetteChoisie = listeRecettes.get(nom);


                            Thread t2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        recetteChoisie.chargerInfosRecette();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            t2.start();

                            while(recetteChoisie.getCookingTime()==null){

                            }


                            //Toast.makeText(getApplicationContext(), recetteChoisie.toString(), Toast.LENGTH_SHORT).show();
                            recetteChoisie.setContenu(null);
                            recetteChoisie.setPage(null);
                            String g = new Gson().toJson(recetteChoisie);
                            Intent i = new Intent(RechercheActivity.this, AfficherRecetteRecherche.class);
                            //Toast.makeText(getApplicationContext(), g, Toast.LENGTH_SHORT).show();
                            i.putExtra("recette", g);
                            startActivity(i);
                        }
                    });







                }
            }
        });


    }
}
