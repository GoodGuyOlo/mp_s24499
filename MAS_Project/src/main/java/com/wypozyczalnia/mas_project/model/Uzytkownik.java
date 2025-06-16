package com.wypozyczalnia.mas_project.model;

public abstract class Uzytkownik {
    private final String imie;
    private String nazwisko;
    private final String login;
    private String haslo;
    private Rola rola;
    private boolean aktywny;

    public Uzytkownik(String imie, String nazwisko, String login, String haslo, Rola rola) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.login = login;
        this.haslo = haslo;
        this.rola = rola;
        this.aktywny = true;
    }

    public boolean zaloguj(String loginInput, String hasloInput) {
        if (!aktywny) {
            return false; // Jeśli użytkownik nieaktywny, nie zaloguje.
        }
        return this.login.equals(loginInput) && this.haslo.equals(hasloInput);
    }

    public void zmienHaslo(String loginInput, String noweHaslo1, String noweHaslo2) {
        // Sprawdzamy, czy podany login zgadza się z loginem użytkownika.
        if (!this.login.equals(loginInput)) {
            throw new IllegalArgumentException("Niepoprawny login.");
        }
        // Sprawdzamy, czy oba podane nowe hasła są identyczne.
        if (!noweHaslo1.equals(noweHaslo2)) {
            throw new IllegalArgumentException("Podane hasła nie są takie same.");
        }

        // Aktualizujemy hasło.
        this.haslo = noweHaslo1;
    }

    public void dezaktywujUzytkownia() {
        this.aktywny = false;
    }

}
