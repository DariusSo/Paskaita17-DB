package com.example;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/pirmaDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException {
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
        db.sukurkPaslauga();
    }
}