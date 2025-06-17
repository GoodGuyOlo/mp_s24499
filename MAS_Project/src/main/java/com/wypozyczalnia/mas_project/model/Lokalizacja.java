package com.wypozyczalnia.mas_project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/* todo
    - sprawdzDostepneSamochody - czy jest sens aby były dwie takie metody?
    - czy metoda usunSamochow nie powinna być w klasie Samochod? W przypadku gdy samochod przestaje należeć do floty to całkowicie powinniśmy się go pozbyć
    - uporządkować metody i zmienne
 */

public class Lokalizacja {
    private String nazwa;
    private String adres;
    private String telefon;
    private String godzinyOtwarcia;
    private List<Samochod> dostepneSamochody;

    public Lokalizacja(String nazwa, String adres, String telefon, String godzinyOtwarcia) {
        this.nazwa = nazwa;
        this.adres = adres;
        this.telefon = telefon;
        this.godzinyOtwarcia = godzinyOtwarcia;
        this.dostepneSamochody = new ArrayList<>();
    }

    public void dodajSamochod(Samochod samochod) {
        if (!dostepneSamochody.contains(samochod)) {
            dostepneSamochody.add(samochod);
        }
    }

    public void usunSamochod(Samochod samochod) {
        dostepneSamochody.remove(samochod);
    }

    public List<Samochod> sprawdzSamochody(KategoriaSamochodu kategoria) {
        List<Samochod> wynik = new ArrayList<>();

        for (Samochod samochod : dostepneSamochody) {
            if (samochod.getKategoriaSamochodu() == kategoria) {
                wynik.add(samochod);
            }
        }
        return wynik;
    }

    public List<Samochod> sprawdzDostepneSamochody(KategoriaSamochodu kategoria, Date dataOd, Date dataDo) {
        List<Samochod> wynik = new ArrayList<>();

        for (Samochod samochod : dostepneSamochody) {
            if (samochod.getKategoriaSamochodu() == kategoria && samochod.czyDostepny(dataOd, dataDo)) {
                wynik.add(samochod);
            }
        }
        return wynik;
    }

    public List<Samochod> sprawdzDostepneSamochody(Date dataOd, Date dataDo) {
        List<Samochod> wynik = new ArrayList<>();

        for (Samochod samochod : dostepneSamochody) {
            if (samochod.czyDostepny(dataOd, dataDo)) {
                wynik.add(samochod);
            }
        }
        return wynik;
    }

    public boolean zmienLokalizacjeSamochodu(Samochod samochod, Lokalizacja nowaLokalizacja) {
        if (dostepneSamochody.contains(samochod)) {
            dostepneSamochody.remove(samochod);
            nowaLokalizacja.dodajSamochod(samochod);
            return true;
        }
        return false;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getAdres() {
        return adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getGodzinyOtwarcia() {
        return godzinyOtwarcia;
    }

    public List<Samochod> getDostepneSamochody() {
        return new ArrayList<>(dostepneSamochody);
    }

}

