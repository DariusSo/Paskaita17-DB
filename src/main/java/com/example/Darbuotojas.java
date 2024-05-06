package com.example;

public class Darbuotojas extends DatabaseTableObject{
    public String darbuotojo_vardas;
    public String getDarbuotojo_pavarde;

    public Darbuotojas(int id, String darbuotojo_vardas, String getDarbuotojo_pavarde) {
        super(id);
        this.darbuotojo_vardas = darbuotojo_vardas;
        this.getDarbuotojo_pavarde = getDarbuotojo_pavarde;
    }
    public Darbuotojas(String darbuotojo_vardas, String getDarbuotojo_pavarde) {
        super(0);
        this.darbuotojo_vardas = darbuotojo_vardas;
        this.getDarbuotojo_pavarde = getDarbuotojo_pavarde;
    }
}
