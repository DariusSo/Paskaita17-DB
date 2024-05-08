package com.example;

public class Vizitas extends DatabaseTableObject{
    public int klientoID;
    public int paslaugosID;
    public String rezervuotasLaikas;

    public Vizitas(int klientoID, int paslaugosID, String rezervuotasLaikas) {
        super(0);
        this.klientoID = klientoID;
        this.paslaugosID = paslaugosID;
        this.rezervuotasLaikas = rezervuotasLaikas;
    }

    public Vizitas(int id, int klientoID, int paslaugosID, String rezervuotasLaikas) {
        super(id);
        this.klientoID = klientoID;
        this.paslaugosID = paslaugosID;
        this.rezervuotasLaikas = rezervuotasLaikas;
    }
    @Override
    public String toString(){
        return "Vizito ID: " + id + " Kliento ID: " + paslaugosID + " Paslaugos ID: " + paslaugosID + " Rezervuotas laikas: " + rezervuotasLaikas;
    }
}
