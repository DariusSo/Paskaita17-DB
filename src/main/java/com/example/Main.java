package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/pirmaDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException, IOException {
        DatabaseManager db = new DatabaseManager(URL, USERNAME, PASSWORD);
        //Klientas k = Klientas.naujasKlientas();
        //db.registruotiKlienta(k);
//        List<Klientas> kl = db.klientuSarasas();
//        for (Klientas klientas : kl){
//            System.out.println(klientas.toString());
//        }
//        List<Klientas> kl = db.paieska();
//        for (Klientas klientas : kl){
//            System.out.println(klientas.toString());
//        }
        //db.sukurkPaslauga();
        //db.sukurkDarbuotoja();
        //db.klientasPagalID();
        //db.paslaugosPanaudojimas(db.klientasPagalID(), db.darbuotojasPagalID(), db.paslaugaPagalID());
        //db.spausdintiMokejimus();
        //db.registruotiVizita(db.klientasPagalID(), db.paslaugaPagalID(), db.rezervuotasLaikas());
        //db.gautiArtimiausiaVizita();
        //db.suveskMokejima();
        //db.gautiArtimiausiaVizitaPagalKlienta(db.klientasPagalID());
        //db.vizituSarasas();
        //db.vizitasPagalID();
        //db.registruotiMokejima(db.darbuotojasPagalID(), db.vizitasPagalID());
        //db.atsauktiVizita(db.vizitasPagalID());
        //db.klientaiICSV();
        //db.klientaiICSV();
        //db.mokejimuSumos();
        //db.artimiausiasVizSuKlientoInfo();
        db.menu();

    }
}