package com.wypozyczalnia.mas_project.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/* todo
    - czy potrzebna jest dataRozpoczecia?
    - uporządkować metody i zmienne
 */

public class Ubezpieczenie {
    public static final String TYP_PODSTAWOWY = "OC+AC";
    public static final String TYP_TRACK = "OC+AC+Track";
    public static final String TYP_OFFROAD = "OC+AC+Offroad";

    private String typ;
    private double kosztRoczny;
    private Date dataWaznosci;
    private String numerPolisy;
    private String nazwaUbezpieczyciela;
    private Date dataRozpoczecia;

    public Ubezpieczenie(String typ, double kosztRoczny, Date dataRozpoczecia, Date dataWaznosci, 
                         String numerPolisy, String nazwaUbezpieczyciela) {
        if (!isValidTyp(typ)) {
            throw new IllegalArgumentException("Nieprawidłowy typ ubezpieczenia. Dostępne typy: TYP_PODSTAWOWY, TYP_TRACK, TYP_OFFROAD");
        }

        this.typ = typ;
        this.kosztRoczny = kosztRoczny;
        this.dataRozpoczecia = dataRozpoczecia;
        this.dataWaznosci = dataWaznosci;
        this.numerPolisy = numerPolisy;
        this.nazwaUbezpieczyciela = nazwaUbezpieczyciela;
    }

    private static boolean isValidTyp(String typ) {
        return TYP_PODSTAWOWY.equals(typ) || TYP_TRACK.equals(typ) || TYP_OFFROAD.equals(typ);
    }

    public void setTyp(String typ) {
        if (!isValidTyp(typ)) {
            throw new IllegalArgumentException("Nieprawidłowy typ ubezpieczenia. Dostępne typy: TYP_PODSTAWOWY, TYP_TRACK, TYP_OFFROAD");
        }
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }

    public double getKosztRoczny() {
        return kosztRoczny;
    }

    public Date getDataWaznosci() {
        return dataWaznosci;
    }

    public Date getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    public String getNumerPolisy() {
        return numerPolisy;
    }

    public String getNazwaUbezpieczyciela() {
        return nazwaUbezpieczyciela;
    }

    public boolean czyWazne() {
        Date dzisiaj = new Date();
        return dataWaznosci.after(dzisiaj);
    }

    public String getOpisTypow() {
        return typ != null ? typ : "Brak ubezpieczenia";
    }

    public static String getDomyslnyTypUbezpieczenia(KategoriaSamochodu kategoria) {
        switch (kategoria) {
            case SPORTOWE:
                return TYP_TRACK;
            case SUV:
                return TYP_OFFROAD;
            default:
                return TYP_PODSTAWOWY;
        }
    }

}
