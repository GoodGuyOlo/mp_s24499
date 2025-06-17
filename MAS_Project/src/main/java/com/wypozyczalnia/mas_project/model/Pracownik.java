package com.wypozyczalnia.mas_project.model;
import java.util.Date;

/* todo
    - metoda obsłużRezerwację() przekazanie samochodu klientowi i automatyczna zmiana statusu wypożyczenia na w trakcie
    - metoda serwisAuta() która przekazuje samochód do serwisu
    - metoda przyjmijZwrot() odebranie samochodu od klienta i automatyczna zmiana statusu wypożyczenia na zakończoną oraz zwolnienie samochodu do dalszych wypożyczeń
    - uporządkować metody i zmienne
 */

public class Pracownik extends Uzytkownik{
    private Stanowisko stanowisko;
    private final Date dataPrzyjecia;

    public Pracownik(String imie, String nazwisko, String login, String haslo, Stanowisko stanowisko, Date dataPrzyjecia) {
        super(imie, nazwisko, login, haslo, Rola.PRACOWNIK);
        this.stanowisko = stanowisko;
        this.dataPrzyjecia = dataPrzyjecia;
    }

}
