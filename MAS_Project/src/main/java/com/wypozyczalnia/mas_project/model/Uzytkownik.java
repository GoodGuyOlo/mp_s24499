package com.wypozyczalnia.mas_project.model;

/* todo
    - uporządkować metody i zmienne
 */

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
            return false;
        }
        return this.login.equals(loginInput) && this.haslo.equals(hasloInput);
    }

    public void zmienHaslo(String loginInput, String noweHaslo1, String noweHaslo2) {
        if (!this.login.equals(loginInput)) {
            throw new IllegalArgumentException("Niepoprawny login.");
        }
        if (!noweHaslo1.equals(noweHaslo2)) {
            throw new IllegalArgumentException("Podane hasla nie sa takie same.");
        }
        this.haslo = noweHaslo1;
    }

    public void dezaktywujUzytkownia() {
        this.aktywny = false;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
}
