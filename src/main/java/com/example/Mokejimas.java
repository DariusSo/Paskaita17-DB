package com.example;

public class Mokejimas extends  DatabaseTableObject{

    public int paslaugosID;
    public int mokejimoSuma;
    public int darbuotojoID;
    public int klientoID;
    public Mokejimas(int id, int paslaugosID, int mokejimoSuma, int darbuotojoID, int klientoID) {
        super(id);
        this.paslaugosID = paslaugosID;
        this.mokejimoSuma = mokejimoSuma;
        this.darbuotojoID = darbuotojoID;
        this.klientoID = klientoID;
    }
    public Mokejimas(int paslaugosID, int mokejimoSuma, int darbuotojoID, int klientoID) {
        super(0);
        this.paslaugosID = paslaugosID;
        this.mokejimoSuma = mokejimoSuma;
        this.darbuotojoID = darbuotojoID;
        this.klientoID = klientoID;
    }
    @Override
    public String toString(){
        return "Mokejimo ID: " + id + " Paslaugos ID: " + paslaugosID + " Mokejimo suma: " + mokejimoSuma + " Darbuotojo ID: " + darbuotojoID + " Kliento ID: " + klientoID;
    }
}
