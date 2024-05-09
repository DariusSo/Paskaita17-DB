package com.example;

import java.io.*;
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


    public void menu() throws SQLException {
        System.out.println("Pasirinkite norima veiksma:" +
                "1 - Uzregistruoti klienta" +
                "2 - Klientu sarasas" +
                "3 - Ieskoti kliento" +
                "4 - Sukurkti paslauga" +
                "5 - Priskirti kaina paslaugai" +
                "6 - Sukurti darbuotoja" +
                "7 - Pasinaudoti paslauga" +
                "8 - Spauzdinti mokejimus" +
                "9 - Registruoti vizita" +
                "10 - Suzinoti artimiausia vizita" +
                "11 - Suzinoti artimiausia kliento vizita" +
                "12 - Vizitu sarasas" +
                "13 - Registruoti mokejima" +
                "14 - Atsaukti vizita" +
                "15 - Irasyti klientus i CSV" +
                "16 - Ikelti klientus is CSV" +
                "17 - Gauti mokejimu statistika" +
                "18 - Parodyti artimiausia vizita su kliento informacija");
        int pasirinkimas = scanner.nextInt();
        scanner.nextLine();
        switch (pasirinkimas){
            case 1:
                Klientas.naujasKlientas();
                break;
            case 2:
                List<Klientas> kl = klientuSarasas();
                for (Klientas klientas : kl){
                System.out.println(klientas.toString());
                }
                break;
            case 3:
                List<Klientas> kl1 = paieska();
                for (Klientas klientas : kl1){
                    System.out.println(klientas.toString());
                }
                break;
            case 4:
                sukurkPaslauga();
                break;
            case 5:
                priskirtiKaina();
                break;
            case 6:
                sukurkDarbuotoja();
                break;
            case 7:
                paslaugosPanaudojimas(klientasPagalID(), darbuotojasPagalID(), paslaugaPagalID());
                break;
            case 8:

                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;
            case 18:
                break;
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
            for(Klientas k : sarasas){
                System.out.println(k.toString());
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
        for(Darbuotojas d : sarasas){
            System.out.println(d.toString());
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
        for(Paslauga p : sarasas){
            System.out.println(p.toString());
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
    public void paslaugosPanaudojimas(int klientas, int darbuotojas, int paslauga) throws SQLException {
        String sql1 = "SELECT *  FROM paslaugos p LEFT JOIN paslaugos_kaina pk ON p.paslaugos_id = pk.paslaugos_id WHERE p.paslaugos_id = ?";

        String sql = "INSERT INTO mokejimai (paslaugos_id, mokejimo_suma, darbuotojo_id, kliento_id) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        PreparedStatement statement1 = connection.prepareStatement(sql1);
        statement1.setInt(1, paslauga);
        ResultSet resultSet = statement1.executeQuery();
        String paslaugos_kaina = null;
        while (resultSet.next()) {
            paslaugos_kaina = resultSet.getString("paslaugos_kaina");
        }
        if(paslaugos_kaina != null){
            statement.setString(2, String.valueOf(paslaugos_kaina));
        }else{
            paslaugos_kaina = String.valueOf(suveskMokejima());
            statement.setString(2, String.valueOf(paslaugos_kaina));
        }

        statement.setString(1, String.valueOf(paslauga));
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
    public double suveskMokejima() throws SQLException {
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
        for(Mokejimas m : mokejimasList){
            System.out.println(m.toString());
        }
        return mokejimasList;
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
    public Vizitas gautiArtimiausiaVizitaPagalKlienta(int klientas) throws SQLException {
        String sql = "SELECT * FROM vizitai WHERE kliento_id = ? ORDER BY rezervuotas_laikas ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, klientas);
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

    public void priskirtiKaina() throws SQLException {
        paslauguSarasas();
        System.out.println("Pasirinkite paslaugos id: ");
        int paslaugosID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Iveskite paslaugos kaina: ");
        double kaina = scanner.nextDouble();
        scanner.nextLine();
        String sql = "INSERT INTO paslaugos_kaina (paslaugos_id, paslaugos_kaina) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, paslaugosID);
        statement.setString(2, String.valueOf(kaina));

        statement.executeUpdate();
    }
    public List<Vizitas> vizituSarasas() throws SQLException {
        List<Vizitas> vizitasList = new ArrayList<>();
        String sql = "SELECT * FROM vizitai";
        PreparedStatement statement = null;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int vizitoID = resultSet.getInt("vizito_id");
            int paslauga = resultSet.getInt("paslaugos_id");
            String rezervuotasLaikas = resultSet.getString("rezervuotas_laikas");
            int paslaugosID = resultSet.getInt("paslaugos_id");
            int klientID = resultSet.getInt("kliento_id");
            Vizitas vizitas = new Vizitas(vizitoID, klientID, paslaugosID, rezervuotasLaikas);
            vizitasList.add(vizitas);
        }
        for(Vizitas v : vizitasList){
            System.out.println(v.toString());
        }
        return vizitasList;
    }
    public int vizitasPagalID() throws SQLException {
        List<Vizitas> vizitasList = vizituSarasas();
        System.out.println("Pasirinkite vizita pagal ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Vizitas v : vizitasList){
            if(v.id == id){
                System.out.println(v.toString());
                System.out.println("_______________________________");
                return v.id;
            }
        }
        System.out.println("Blogas id.");
        klientasPagalID();
        return 0;
    }


    public void registruotiMokejima(int darbuotojas, int vizitas) throws SQLException {
        String sql = "SELECT v.paslaugos_id, p.paslaugos_kaina, v.kliento_id FROM vizitai v INNER JOIN paslaugos_kaina p ON v.paslaugos_id = p.paslaugos_id WHERE v.vizito_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vizitas);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int paslaugosID = resultSet.getInt("v.paslaugos_id");
            String paslaugosKaina = resultSet.getString("p.paslaugos_kaina");
            int klientoID = resultSet.getInt("v.kliento_id");
            String sql3 = "SELECT * FROM klientai WHERE kliento_id = ?";
            PreparedStatement klientoStatement = connection.prepareStatement(sql3);
            klientoStatement.setInt(1, klientoID);
            ResultSet klientoResultSet = klientoStatement.executeQuery();
            boolean arVip = false;
            int nuolaida = 0;
            while(klientoResultSet.next()){
                arVip = klientoResultSet.getBoolean("VIP");
                if (arVip){
                    nuolaida = 25;
                }
            }
            String sql2 = "INSERT INTO mokejimai (paslaugos_id, mokejimo_suma, darbuotojo_id, kliento_id) VALUES (?,?,?,?)";
            PreparedStatement statement2 = connection.prepareStatement(sql2);

            if(paslaugosKaina != null){
                double paslaugosKaina1 = Double.parseDouble(paslaugosKaina) * (nuolaida * 0.01);
                paslaugosKaina = String.valueOf(Double.parseDouble(paslaugosKaina) - paslaugosKaina1);
                statement2.setString(2, String.valueOf(paslaugosKaina));
            }else{
                paslaugosKaina = String.valueOf(suveskMokejima());
                double paslaugosKaina1 = Integer.parseInt(paslaugosKaina) * (nuolaida * 0.01);
                paslaugosKaina = String.valueOf(Double.parseDouble(paslaugosKaina) - paslaugosKaina1);
                statement2.setString(2, String.valueOf(paslaugosKaina));
            }
            statement2.setInt(1, paslaugosID);
            statement2.setInt(3, darbuotojas);
            statement2.setInt(4, klientoID);
            statement2.executeUpdate();
            System.out.println("Mokejimas sekmingai uzregistruotas.");
        }
    }
    public void atsauktiVizita(int vizitas) throws SQLException {
        String sql = "DELETE FROM vizitai WHERE vizito_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vizitas);
        statement.executeUpdate();
        System.out.println("Vizitas atsauktas");
    }
    public void klientaiICSV() throws IOException {
        String path = "C:\\Users\\Darius\\IdeaProjects\\pasizaidimasSuDB\\Paskaita17M\\src\\main\\java\\com\\example\\klientai.csv";
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        List<Klientas> klientai = klientuSarasas();
        for(Klientas k : klientai){
            bufferedWriter.write(k.toCSV());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
    }
    public List<Klientas> klientaiIsCSV() throws IOException {
        String path = "C:\\Users\\Darius\\IdeaProjects\\pasizaidimasSuDB\\Paskaita17M\\src\\main\\java\\com\\example\\klientai.csv";
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<Klientas> klientaiIsCSV = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] l = line.split(",");
            Klientas klientas = new Klientas(Integer.parseInt(l[0]), l[1], l[2], l[3], l[4], Boolean.getBoolean(l[5]));
            klientaiIsCSV.add(klientas);
            klientas.toString();
        }
        return klientaiIsCSV;
    }
    public void mokejimuSumos() throws SQLException {
        String sql = "SELECT SUM(mokejimo_suma) FROM mokejimai";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            String suma = resultSet.getString("SUM(mokejimo_suma)");
            System.out.println("Mokejimu suma: " + suma);
        }
        String sql1 = "SELECT MAX(mokejimo_suma) FROM mokejimai";
        PreparedStatement statement1 = connection.prepareStatement(sql1);
        ResultSet resultSet1 = statement1.executeQuery();
        while(resultSet1.next()){
            String suma = resultSet1.getString("MAX(mokejimo_suma)");
            System.out.println("Didziausias mokejimas: " + suma);
        }
        String sql2 = "SELECT MIN(mokejimo_suma) FROM mokejimai";
        PreparedStatement statement2 = connection.prepareStatement(sql2);
        ResultSet resultSet2 = statement2.executeQuery();
        while(resultSet2.next()){
            String suma = resultSet2.getString("MIN(mokejimo_suma)");
            System.out.println("Maziausias mokejimas: " + suma);
        }

    }
    public void artimiausiasVizSuKlientoInfo() throws SQLException {
        String sql = "SELECT * FROM vizitai v INNER JOIN klientai k ON k.kliento_id = v.kliento_id ORDER BY rezervuotas_laikas ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt("kliento_id");
            String vardas = resultSet.getString("kliento_vardas");
            String pavarde = resultSet.getString("kliento_pavarde");
            String gimimoData = resultSet.getString("gimimo_data");
            String registracijosLaikas = resultSet.getString("registracijos_data");
            boolean VIP = resultSet.getBoolean("VIP");
            Klientas klientas = new Klientas(id, vardas, pavarde, gimimoData, registracijosLaikas, VIP);
            int vizitoID = resultSet.getInt("vizito_id");
            int paslauga = resultSet.getInt("paslaugos_id");
            String rezervuotasLaikas = resultSet.getString("rezervuotas_laikas");
            int paslaugosID = resultSet.getInt("paslaugos_id");
            int klientID = resultSet.getInt("kliento_id");
            Vizitas vizitas = new Vizitas(vizitoID, klientID, paslaugosID, rezervuotasLaikas);
            System.out.println("Vizito info: " + vizitas.toString() + "| Kliento info: " + klientas.toString());
            return;
        }

    }
}
