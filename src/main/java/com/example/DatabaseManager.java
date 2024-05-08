package com.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Darbuotojas> darbuotojuSarasas() throws SQLException {
        String sql = "SELECT * FROM darbuotojai";
        PreparedStatement statement = null;
        List<Darbuotojas> sarasas = new ArrayList<>();

        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("darbuotojo_id");
            String vardas = resultSet.getString("darbuotojo_vardas");
            String pavarde = resultSet.getString("darbuotojo_pavarde");
            Darbuotojas darbuotojas = new Darbuotojas(id, vardas, pavarde);
            sarasas.add(darbuotojas);
        }
        return sarasas;
    }
    public List<Paslauga> paslauguSarasas() throws SQLException {
        String sql = "SELECT * FROM paslaugos";
        PreparedStatement statement = null;
        List<Paslauga> sarasas = new ArrayList<>();

        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("paslaugos_id");
            String pavadinimas = resultSet.getString("paslaugos_pavadinimas");
            Paslauga paslauga = new Paslauga(id, pavadinimas);
            sarasas.add(paslauga);
        }
        return sarasas;
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
        String sql = "INSERT INTO paslaugos (paslaugos_pavadinimas, paslaugos_kaina) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pavadinimas);
        statement.executeUpdate();
    }
    public void paslaugosPanaudojimas(int klientas, int darbuotojas, int paslauga, double paslaugosKaina) throws SQLException {

//        String sql = "SELECT *  FROM paslaugos p LEFT JOIN paslaugos_kaina pk ON p.paslaugos_id = pk.paslaugos_id WHERE p.paslaugos_id = 1";
//        PreparedStatement statement = null;
//        List<Darbuotojas> sarasas = new ArrayList<>();
//
//        statement = connection.prepareStatement(sql);
//        ResultSet resultSet = statement.executeQuery();
//
//        while (resultSet.next()) {
//            int id = resultSet.getInt("darbuotojo_id");
//            String vardas = resultSet.getString("darbuotojo_vardas");
//            String pavarde = resultSet.getString("darbuotojo_pavarde");
//            Darbuotojas darbuotojas = new Darbuotojas(id, vardas, pavarde);
//            sarasas.add(darbuotojas);
//        }

        String sql = "INSERT INTO mokejimai (paslaugos_id, mokejimo_suma, darbuotojo_id, kliento_id) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(paslauga));
        statement.setString(2, String.valueOf(paslaugosKaina));
        statement.setString(3, String.valueOf(darbuotojas));
        statement.setString(4, String.valueOf(klientas));
        statement.executeUpdate();
    }

    public void sukurkDarbuotoja() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Iveskite darbuotojo varda: ");
        String vardas = scanner.nextLine();
        System.out.println("Iveskite darbuotojo pavarde: ");
        String pavarde = scanner.nextLine();
        String sql = "INSERT INTO darbuotojai (darbuotojo_vardas, darbuotojo_pavarde) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vardas);
        statement.setString(2, pavarde);
        statement.executeUpdate();
    }

    public Klientas klientoPaieska() throws SQLException {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Iveskite varda: ");
            String paieskaVardas = scanner.nextLine();
            System.out.println("Iveskite pavarde: ");
            String paieskaPavarde = scanner.nextLine();
            String sql = "SELECT * FROM klientai WHERE kliento_vardas = ? AND kliento_pavarde = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, paieskaVardas);
            statement.setString(2, paieskaPavarde);
            ResultSet resultSet = statement.executeQuery();

            Klientas klientas = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("kliento_id");
                String vardas = resultSet.getString("kliento_vardas");
                String pavarde = resultSet.getString("kliento_pavarde");
                String gimimoData = resultSet.getString("gimimo_data");
                String registracijosLaikas = resultSet.getString("registracijos_data");
                boolean VIP = resultSet.getBoolean("VIP");
                klientas = new Klientas(id, vardas, pavarde, gimimoData, registracijosLaikas, VIP);

            }
            if (klientas == null){
                System.out.println("Klientas nerastas. Bandykite dar karta");
                klientoPaieska();
            }
            return klientas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Darbuotojas darbuotojoPaieska() throws SQLException {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Iveskite darbuotojo varda: ");
            String paieskaVardas = scanner.nextLine();
            System.out.println("Iveskite pavarde: ");
            String paieskaPavarde = scanner.nextLine();
            String sql = "SELECT * FROM darbuotojai WHERE darbuotojo_vardas = ? AND darbuotojo_pavarde = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, paieskaVardas);
            statement.setString(2, paieskaPavarde);
            ResultSet resultSet = statement.executeQuery();

            Darbuotojas darbuotojas = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("darbuotojo_id");
                String vardas = resultSet.getString("darbuotojo_vardas");
                String pavarde = resultSet.getString("darbuotojo_pavarde");
                darbuotojas = new Darbuotojas(id, vardas, pavarde);

            }
            if (darbuotojas == null){
                System.out.println("Klientas nerastas. Bandykite dar karta");
                klientoPaieska();
            }
            return darbuotojas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int darbuotojasPagalID() throws SQLException {
        List<Darbuotojas> darbuotojas = darbuotojuSarasas();
        for(Darbuotojas d : darbuotojas){
            System.out.println(d.toString());
        }
        System.out.println("Pasirinkite darbuotoja pagal ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Darbuotojas d : darbuotojas){
            if(d.id == id){
                System.out.println(d.toString());
                System.out.println("_______________________________");
                return d.id;
            }
        }
        System.out.println("Blogas id.");
        darbuotojasPagalID();
        return 0;
    }

    public int paslaugaPagalID() throws SQLException {
        List<Paslauga> paslauga = paslauguSarasas();
        for(Paslauga p : paslauga){
            System.out.println(p.toString());
        }
        System.out.println("Pasirinkite paslauga pagal ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Paslauga p : paslauga){
            if(p.id == id){
                System.out.println(p.toString());
                System.out.println("_______________________________");
                return p.id;
            }
        }
        System.out.println("Blogas id.");
        paslaugaPagalID();
        return 0;
    }
    public int klientasPagalID() throws SQLException {
        List<Klientas> klientas = klientuSarasas();
        for(Klientas k : klientas){
            System.out.println(k.toString());
        }
        System.out.println("Pasirinkite klienta pagal ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Klientas k : klientas){
            if(k.id == id){
                System.out.println(k.toString());
                System.out.println("_______________________________");
                return k.id;
            }
        }
        System.out.println("Blogas id.");
        klientasPagalID();
        return 0;
    }
    public double suveskMokejima(){
        System.out.println("Kokia suma?");
        double suma = scanner.nextDouble();
        scanner.nextLine();
        return suma;

    }
    public List<Mokejimas> mokejimuSarasas() throws SQLException {
        List<Mokejimas> mokejimasList = new ArrayList<>();
        String sql = "SELECT * FROM mokejimai";
        PreparedStatement statement = null;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("mokejimo_id");
            int paslauga = resultSet.getInt("paslaugos_id");
            int suma = resultSet.getInt("mokejimo_suma");
            int darbuotojas = resultSet.getInt("darbuotojo_id");
            int klientas = resultSet.getInt("kliento_id");
            Mokejimas mokejimas = new Mokejimas(id, paslauga, suma, darbuotojas, klientas);
            mokejimasList.add(mokejimas);
        }
        return mokejimasList;
    }
    public void spausdintiMokejimus() throws SQLException {
        List<Mokejimas> mok = mokejimuSarasas();
        for(Mokejimas m : mok){
            System.out.println(m.toString());
        }
    }

    public void registruotiVizita(int klientoID, int paslaugosID, String rezervuotasLaikas) throws SQLException {
        String sql = "INSERT INTO vizitai (kliento_id, paslaugos_id, rezervuotas_laikas) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, klientoID);
        statement.setInt(2, paslaugosID);
        statement.setString(3, rezervuotasLaikas);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Vizitas sekmingai uzregistruotas.");
        } else {
            System.out.println("Kliento registracija nepavyko.");
        }
    }
    public String rezervuotasLaikas(){
        System.out.println("Iveskite rezervacijos laika (YYYY:MM:DD HH:MM:SS)");
        String laikas = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(laikas, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        return String.valueOf(dateTime);
    }
    public Vizitas gautiArtimiausiaVizita() throws SQLException {
        String sql = "SELECT * FROM vizitai ORDER BY rezervuotas_laikas ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Vizitas vizitas = null;
        while (resultSet.next()) {
            int id = resultSet.getInt("vizito_id");
            int klientoId = resultSet.getInt("kliento_id");
            int paslaugosId = resultSet.getInt("paslaugos_id");
            String laikas = resultSet.getString("rezervuotas_laikas");
            vizitas = new Vizitas(id, klientoId, paslaugosId, laikas);
            System.out.println(vizitas.toString());
            return vizitas;
        }
        return vizitas;
    }

    public void pervadinsiuVeliau(){

    }
    public void priskirtiKaina() throws SQLException {
        paslauguSarasas();
        System.out.println("Pasirinkite paslaugos id: ");
        int paslaugosID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Iveskite paslaugos kaina: ");
        int kaina = scanner.nextInt();
        scanner.nextLine();
        String sql = "INSERT INTO Paslaugos (paslaugos_id, kaina) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, paslaugosID);
        statement.setInt(2, kaina);

        statement.executeUpdate();
    }
}
