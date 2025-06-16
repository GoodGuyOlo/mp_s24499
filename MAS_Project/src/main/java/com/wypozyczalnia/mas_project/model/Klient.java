package com.wypozyczalnia.mas_project.model;

public class Klient extends Uzytkownik {
    private String email;
    private String numerTelefonu;
    private final String pesel;
    private String adres;
    private String numerPrawaJazdy;

    public Klient(String imie, String nazwisko, String login, String haslo, String email, String numerTelefonu, String pesel, String adres, String numerPrawaJazdy) {
        super(imie, nazwisko, login, haslo, Rola.KLIENT);
        this.email = email;
        this.numerTelefonu = numerTelefonu;
        this.pesel = pesel;
        this.adres = adres;
        this.numerPrawaJazdy = numerPrawaJazdy;
    }
}
