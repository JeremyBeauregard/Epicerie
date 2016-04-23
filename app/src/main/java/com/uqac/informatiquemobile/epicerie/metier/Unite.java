package com.uqac.informatiquemobile.epicerie.metier;

/**
 * Created by paull on 15/03/2016.
 */
public class Unite {

    private int id;
    private String name;
    private String symbol;

    public Unite(int id, String name, String symbol) {
        this.id = id;
        this.name= name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
