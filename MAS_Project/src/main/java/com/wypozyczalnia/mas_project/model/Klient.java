package com.wypozyczalnia.mas_project.model;

import java.util.ArrayList;
import java.util.List;

/* todo
    - Czy nie wartobyłoby połączyc metody dodajRezerwacje() i dokonajRezerwacji() jako po prostu nowaRezerwacja()?
    - Rezerwacje mają swój status w ENUM StatusRezerwacji, który nie jest wykorzystany. Zmiana statusu rezerwacji na ANULOWANE
    automatycznie powinna anulować rezerwację, pytanie czy potrzebna jest metoda do tego?
    - Raczej nie jest potrzebna metoda getAktywneRezerwacje(), w getRezerwacje() powinna być możliwość po prostu przefiltrowania po różnych statusach.
    - czy zmienne powinny być final?
    - uporządkować metody i zmienne
 */

public class Klient extends Uzytkownik {
    private String email;
    private String numerTelefonu;
    private final String pesel;
    private String adres;
    private String numerPrawaJazdy;
    private List<Rezerwacja> rezerwacje;

    public Klient(String imie, String nazwisko, String login, String haslo, String email, String numerTelefonu, String pesel, String adres, String numerPrawaJazdy) {
        super(imie, nazwisko, login, haslo, Rola.KLIENT);
        this.email = email;
        this.numerTelefonu = numerTelefonu;
        this.pesel = pesel;
        this.adres = adres;
        this.numerPrawaJazdy = numerPrawaJazdy;
        this.rezerwacje = new ArrayList<>();
    }

    public boolean anulujRezerwacje(Rezerwacja rezerwacja) {
        if (rezerwacje.contains(rezerwacja)) {
            return rezerwacja.anulujRezerwacje();
        }
        return false;
    }

    public List<Rezerwacja> getRezerwacje() {
        return new ArrayList<>(rezerwacje);
    }

    public List<Rezerwacja> getAktywneRezerwacje() {
        List<Rezerwacja> aktywne = new ArrayList<>();
        for (Rezerwacja rez : rezerwacje) {
            if (rez.czyAktywna() || rez.czyZatwierdzona()) {
                aktywne.add(rez);
            }
        }
        return aktywne;
    }

    public void dodajRezerwacje(Rezerwacja rezerwacja) {
        if (rezerwacja != null && rezerwacja.getKlient() == this) {
            rezerwacje.add(rezerwacja);
        } else {
            throw new IllegalArgumentException("Rezerwacja musi być powiązana z tym klientem");
        }
    }

    public Rezerwacja dokonajRezerwacji(Samochod samochod, java.util.Date dataOd, java.util.Date dataDo,
                                        Lokalizacja lokalizacjaOdbioru, Lokalizacja lokalizacjaZwrotu) {
        if (!lokalizacjaOdbioru.getDostepneSamochody().contains(samochod)) {
            throw new IllegalArgumentException("Samochód nie jest dostępny w wybranej lokalizacji odbioru");
        }
        Rezerwacja rezerwacja = new Rezerwacja(samochod, this, dataOd, dataDo, lokalizacjaOdbioru, lokalizacjaZwrotu);
        dodajRezerwacje(rezerwacja);
        return rezerwacja;
    }

}
