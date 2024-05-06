package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseManager implements DatabaseManagerInterface{
    private Connection connection;
    Scanner scanner = new Scanner(System.in);
    public DatabaseManager(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registruotiKlienta(Klientas naujasK) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        /*kliento duomenų nuskaitymas */

        String sql = "INSERT INTO klientai (kliento_vardas, kliento_pavarde, gimimo_data, VIP) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, naujasK.getKlientoVardas());
        statement.setString(2, naujasK.getKlientoPavarde());
        statement.setString(3, naujasK.getGimimoData());
        statement.setBoolean(4, naujasK.isVIP());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Klientas sėkmingai įregistruotas.");
        } else {
            System.out.println("Kliento registracija nepavyko.");
        }
    }
    public List<Klientas> klientuSarasas(){
        String sql = "SELECT * FROM klientai";
        PreparedStatement statement = null;
        List<Klientas> sarasas = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("kliento_id");
                String vardas = resultSet.getString("kliento_vardas");
                String pavarde = resultSet.getString("kliento_pavarde");
                String gimimoData = resultSet.getString("gimimo_data");
                String registracijosLaikas = resultSet.getString("registracijos_data");
                boolean VIP = resultSet.getBoolean("VIP");
                Klientas klientas = new Klientas(id, vardas, pavarde, gimimoData, registracijosLaikas, VIP);
                sarasas.add(klientas);

            }
            return sarasas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Klientas> paieska() throws SQLException {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Iveskite varda");
            String paieskaVardas = scanner.nextLine();
            System.out.println("Iveskite pavarde");
            String paieskaPavarde = scanner.nextLine();
            String sql = "SELECT * FROM klientai WHERE kliento_vardas LIKE ? OR kliento_pavarde LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + paieskaVardas + "%");
            statement.setString(2, "%" + paieskaPavarde + "%");
            List<Klientas> sarasas1 = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("kliento_id");
                String vardas = resultSet.getString("kliento_vardas");
                String pavarde = resultSet.getString("kliento_pavarde");
                String gimimoData = resultSet.getString("gimimo_data");
                String registracijosLaikas = resultSet.getString("registracijos_data");
                boolean VIP = resultSet.getBoolean("VIP");
                Klientas klientas = new Klientas(id, vardas, pavarde, gimimoData, registracijosLaikas, VIP);
                sarasas1.add(klientas);

            }
            return sarasas1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void sukurkPaslauga() throws SQLException {
        System.out.println("Iveskite paslaugos pavadinima: ");
        String pavadinimas = scanner.nextLine();
        String sql = "INSERT INTO paslaugos (paslaugos_pavadinimas) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pavadinimas);
        statement.executeUpdate();
    }

}
