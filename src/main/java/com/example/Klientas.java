package com.example;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Klientas extends DatabaseTableObject{
    private String klientoVardas;
    private String klientoPavarde;
    private String gimimoData;
    private String registracijosLaikas;

    private boolean VIP;

    public Klientas(String klientoVardas, String klientoPavarde, String gimimoData, boolean VIP) {
        super(0);
        this.klientoVardas = klientoVardas;
        this.klientoPavarde = klientoPavarde;
        this.gimimoData = gimimoData;
        this.VIP = VIP;
    }

    public Klientas(int id, String klientoVardas, String klientoPavarde, String gimimoData, String registracijosLaikas, boolean VIP) {
        super(id);
        this.klientoVardas = klientoVardas;
        this.klientoPavarde = klientoPavarde;
        this.gimimoData = gimimoData;
        this.registracijosLaikas = registracijosLaikas;
        this.VIP = VIP;
    }

    public String getKlientoVardas() {
        return klientoVardas;
    }

    public void setKlientoVardas(String klientoVardas) {
        this.klientoVardas = klientoVardas;
    }

    public String getKlientoPavarde() {
        return klientoPavarde;
    }

    public void setKlientoPavarde(String klientoPavarde) {
        this.klientoPavarde = klientoPavarde;
    }

    public String getGimimoData() {
        return gimimoData;
    }

    public void setGimimoData(String gimimoData) {
        this.gimimoData = gimimoData;
    }

    public String getRegistracijosLaikas() {
        return registracijosLaikas;
    }

    public void setRegistracijosLaikas(String registracijosLaikas) {
        this.registracijosLaikas = registracijosLaikas;
    }

    public boolean isVIP() {
        return VIP;
    }

    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }

    public static Klientas naujasKlientas(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Įveskite vardą:");
        String vardas = scanner.nextLine();
        System.out.println("Įveskite pavardę:");
        String pavarde = scanner.nextLine();
        System.out.println("Įveskite gimimo datą (yyyy-mm-dd):");
        String gimimoData = scanner.nextLine();
        System.out.println("Ar klientas yra VIP? (true/false):");
        boolean vip = scanner.nextBoolean();
        Klientas klientas = new Klientas(vardas, pavarde, gimimoData, vip);
        return klientas;


    }

    @Override
    public String toString(){
        return "Id: " + id + " | Vardas: " + getKlientoVardas() + " | Pavarde: " + getKlientoPavarde() + " | Gimimo data: " +
                getGimimoData() + " | Registracijos laikas: " + getRegistracijosLaikas() + " | AR vip? " + isVIP();
    }
}
