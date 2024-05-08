package com.example;

import java.util.Scanner;

public class Paslauga extends DatabaseTableObject{
    public String pavadinimas;


    public Paslauga(int id, String pavadinimas) {
        super(id);
        this.pavadinimas = pavadinimas;

    }
    public Paslauga(String pavadinimas){
        super(0);
        this.pavadinimas = pavadinimas;

    }

    @Override
    public String toString(){
        return "Id: " + id + " | Pavadinimas: " + pavadinimas;
    }
}
