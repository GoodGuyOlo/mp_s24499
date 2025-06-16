package com.wypozyczalnia.mas_project.model;
import java.util.Date;

public class Pracownik extends Uzytkownik{
    private Stanowisko stanowisko;
    private final Date dataPrzyjecia;

    public Pracownik(String imie, String nazwisko, String login, String haslo, Stanowisko stanowisko, Date dataPrzyjecia) {
        super(imie, nazwisko, login, haslo, Rola.PRACOWNIK);
        this.stanowisko = stanowisko;
        this.dataPrzyjecia = dataPrzyjecia;
    }

}
