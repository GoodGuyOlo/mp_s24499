package com.wypozyczalnia.mas_project.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/* todo
    - zmienna uwagi nie jest potrzebna
    - uporządkować metody i zmienne
 */

public class Wypozyczenie {
    private String numerWypozyczenia;
    private Samochod samochod;
    private Klient klient;
    private Pracownik pracownik;
    private Date dataOd;
    private Date dataDo;
    private StatusWypozyczenia statusWypozyczenia;
    private double koszt;
    private Rezerwacja powiazanaRezerwacja;
    private Lokalizacja lokalizacjaOdbioru;
    private Lokalizacja lokalizacjaZwrotu;
    private String uwagi;
    private Date dataWypozyczenia;
    private Date dataZwrotu;
    private List<Platnosc> platnosci = new ArrayList<>();

    public Wypozyczenie(Rezerwacja rezerwacja, Pracownik pracownik) {
        if (rezerwacja == null || pracownik == null) {
            throw new IllegalArgumentException("Rezerwacja i pracownik nie mogą być null");
        }

        if (rezerwacja.getStatusRezerwacji() != StatusRezerwacji.ZATWIERDZONA && 
            rezerwacja.getStatusRezerwacji() != StatusRezerwacji.AKTYWNA) {
            throw new IllegalArgumentException("Wypożyczenie można utworzyć tylko na podstawie zatwierdzonej lub aktywnej rezerwacji");
        }

        this.dataWypozyczenia = new Date();
        this.powiazanaRezerwacja = rezerwacja;
        this.samochod = rezerwacja.getSamochod();
        this.klient = rezerwacja.getKlient();
        this.pracownik = pracownik;
        this.dataOd = rezerwacja.getDataOd();
        this.dataDo = rezerwacja.getDataDo();
        this.koszt = rezerwacja.getKwotaRezerwacji();
        this.lokalizacjaOdbioru = rezerwacja.getLokalizacjaOdbioru();
        this.lokalizacjaZwrotu = rezerwacja.getLokalizacjaZwrotu();
        this.statusWypozyczenia = StatusWypozyczenia.NOWE;
        this.numerWypozyczenia = generujNumerWypozyczenia();

        if (rezerwacja.getStatusRezerwacji() == StatusRezerwacji.ZATWIERDZONA) {
            rezerwacja.aktywujRezerwacje();
        }
    }

    public boolean rozpocznijWypozyczenie() {
        if (this.statusWypozyczenia == StatusWypozyczenia.NOWE) {
            this.statusWypozyczenia = StatusWypozyczenia.W_TRAKCIE;
            return true;
        }
        return false;
    }

    public boolean zakonczWypozyczenie() {
        if (this.statusWypozyczenia == StatusWypozyczenia.W_TRAKCIE) {
            this.statusWypozyczenia = StatusWypozyczenia.ZAKONCZONE;
            this.dataZwrotu = new Date(); // Bieżąca data jako data zwrotu

            if (powiazanaRezerwacja != null && powiazanaRezerwacja.getStatusRezerwacji() == StatusRezerwacji.AKTYWNA) {
                powiazanaRezerwacja.zakonczRezerwacje();
            }

            return true;
        }
        return false;
    }

    public boolean anulujWypozyczenie(String powod) {
        if (this.statusWypozyczenia == StatusWypozyczenia.NOWE) {
            this.statusWypozyczenia = StatusWypozyczenia.ANULOWANE;
            this.uwagi = powod;
            return true;
        }
        return false;
    }

    private String generujNumerWypozyczenia() {
        // Format: WYP-YYYYMMDD-XXXX gdzie XXXX to losowy ciąg cyfr
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataPart = sdf.format(this.dataWypozyczenia);
        int randomPart = (int) (Math.random() * 10000);

        return "WYP-" + dataPart + "-" + String.format("%04d", randomPart);
    }

    private double obliczOplateDodatkowa() {
        if (lokalizacjaOdbioru != lokalizacjaZwrotu) {
            return 100.0; // Dodatkowa opłata za zwrot w innej lokalizacji
        }
        return 0.0;
    }

    public Platnosc dodajPlatnosc(double kwota, MetodaPlatnosci metodaPlatnosci) {
        Platnosc platnosc = new Platnosc(this, kwota, metodaPlatnosci);
        platnosci.add(platnosc);
        return platnosc;
    }

    public List<Platnosc> getPlatnosci() {
        return new ArrayList<>(platnosci);
    }

    public String generujFakture(String nip) {
        if (platnosci.isEmpty()) {
            throw new IllegalStateException("Brak płatności, dla których można wygenerować fakturę");
        }

        for (int i = platnosci.size() - 1; i >= 0; i--) {
            Platnosc platnosc = platnosci.get(i);
            if (platnosc.getStatusPlatnosci() == StatusPlatnosci.ZATWIERDZONA && 
                platnosc.getNumerFaktury() == null) {
                return platnosc.generujFakture(nip);
            }
        }

        throw new IllegalStateException("Brak zatwierdzonych płatności bez wygenerowanej faktury");
    }

    public String getNumerWypozyczenia() {
        return numerWypozyczenia;
    }

    public Samochod getSamochod() {
        return samochod;
    }

    public Klient getKlient() {
        return klient;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public Date getDataOd() {
        return dataOd;
    }

    public Date getDataDo() {
        return dataDo;
    }

    public StatusWypozyczenia getStatusWypozyczenia() {
        return statusWypozyczenia;
    }

    public double getKoszt() {
        return koszt;
    }

    public Rezerwacja getPowiazanaRezerwacja() {
        return powiazanaRezerwacja;
    }

    public Lokalizacja getLokalizacjaOdbioru() {
        return lokalizacjaOdbioru;
    }

    public Lokalizacja getLokalizacjaZwrotu() {
        return lokalizacjaZwrotu;
    }

    public String getUwagi() {
        return uwagi;
    }

    public Date getDataWypozyczenia() {
        return dataWypozyczenia;
    }

    public Date getDataZwrotu() {
        return dataZwrotu;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }
}
