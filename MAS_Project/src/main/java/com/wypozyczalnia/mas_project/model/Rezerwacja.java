package com.wypozyczalnia.mas_project.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* todo
    - Czy jest sens rozdzielać status rezerwacji na ZATWIERDZONA i AKTYWNA? Generalnie dopóki nie dojdzie do płatności - czyli albo płatność
    przelewem przed obiorem auta albo karta/gotówka w momencie odbioru auta - to status pozostaje jako oczekujaca_platnosc. Więc może zamienić metodę aktywujRezerwacje
    po prostu na utwórzRezerwacje
    - Czy metody zarządzania rezerwacją nie powinny być u klienta? np. oplacRezerwacje() albo anulujRezerwacje()? Ponieważ według założeń to klient powinien tym zarządzać.
    - uporządkować metody i zmienne
 */

public class Rezerwacja {
    private Date dataRezerwacji;
    private Date dataOd;
    private Date dataDo;
    private StatusRezerwacji statusRezerwacji;
    private Samochod samochod;
    private Klient klient;
    private Lokalizacja lokalizacjaOdbioru;
    private Lokalizacja lokalizacjaZwrotu;
    private double kwotaRezerwacji;
    private StatusPlatnosci statusPlatnosci;
    private String numerRezerwacji;


    public Rezerwacja(Samochod samochod, Klient klient, Date dataOd, Date dataDo, 
                      Lokalizacja lokalizacjaOdbioru, Lokalizacja lokalizacjaZwrotu) {

        if (dataOd.after(dataDo)) {
            throw new IllegalArgumentException("Data rozpoczęcia nie może być późniejsza niż data zakończenia");
        }

        this.dataRezerwacji = new Date();
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.samochod = samochod;
        this.klient = klient;
        this.lokalizacjaOdbioru = lokalizacjaOdbioru;
        this.lokalizacjaZwrotu = lokalizacjaZwrotu;
        this.statusRezerwacji = StatusRezerwacji.OCZEKUJACA_PLATNOSC;
        this.statusPlatnosci = StatusPlatnosci.OCZEKUJACA;
        this.kwotaRezerwacji = obliczKwoteRezerwacji();
        this.numerRezerwacji = generujNumerRezerwacji();
    }

    public boolean oplacRezerwacje() {
        if (this.statusRezerwacji == StatusRezerwacji.OCZEKUJACA_PLATNOSC) {
            this.statusPlatnosci = StatusPlatnosci.ZATWIERDZONA;
            this.statusRezerwacji = StatusRezerwacji.ZATWIERDZONA;
            return true;
        } else if (this.statusRezerwacji == StatusRezerwacji.ANULOWANA) {
            throw new IllegalStateException("Nie można opłacić anulowanej rezerwacji");
        } else {
            // Rezerwacja jest już opłacona
            return false;
        }
    }

    public boolean anulujRezerwacje() {
        if (this.statusRezerwacji == StatusRezerwacji.OCZEKUJACA_PLATNOSC || 
            this.statusRezerwacji == StatusRezerwacji.ZATWIERDZONA) {

            this.statusRezerwacji = StatusRezerwacji.ANULOWANA;
            return true;
        } else if (this.statusRezerwacji == StatusRezerwacji.AKTYWNA) {
            throw new IllegalStateException("Nie można anulować aktywnej rezerwacji");
        } else {
            // Rezerwacja jest już zakończona lub anulowana
            return false;
        }
    }

    public boolean aktywujRezerwacje() {
        if (this.statusRezerwacji == StatusRezerwacji.ZATWIERDZONA) {
            this.statusRezerwacji = StatusRezerwacji.AKTYWNA;
            return true;
        } else {
            return false;
        }
    }

    public boolean zakonczRezerwacje() {
        if (this.statusRezerwacji == StatusRezerwacji.AKTYWNA) {
            this.statusRezerwacji = StatusRezerwacji.ZAKONCZONA;
            return true;
        } else {
            return false;
        }
    }

    public boolean czyAktywna() {
        return this.statusRezerwacji == StatusRezerwacji.AKTYWNA;
    }

    public boolean czyZatwierdzona() {
        return this.statusRezerwacji == StatusRezerwacji.ZATWIERDZONA;
    }

    private double obliczKwoteRezerwacji() {
        long diffInMillies = dataDo.getTime() - dataOd.getTime();
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);

        if (diffInDays < 1) diffInDays = 1; // Minimum 1 dzień

        int dniWeekendowe = 0;
        int dniPowszednie = 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(dataOd);

        for (int i = 0; i < diffInDays; i++) {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                dniWeekendowe++;
            } else {
                dniPowszednie++;
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        double stawkaPodstawowa = samochod.getStawkaPodstawowa();
        double stawkaWeekendowa = samochod.getStawkaWeekendowa();

        return (dniPowszednie * stawkaPodstawowa) + (dniWeekendowe * stawkaWeekendowa);
    }

    private String generujNumerRezerwacji() {
        // Format: REZ-YYYYMMDD-XXXX gdzie XXXX to losowy ciąg cyfr
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataPart = sdf.format(this.dataRezerwacji);
        int randomPart = (int) (Math.random() * 10000);

        return "REZ-" + dataPart + "-" + String.format("%04d", randomPart);
    }

    public Date getDataRezerwacji() {
        return dataRezerwacji;
    }

    public Date getDataOd() {
        return dataOd;
    }

    public Date getDataDo() {
        return dataDo;
    }

    public StatusRezerwacji getStatusRezerwacji() {
        return statusRezerwacji;
    }

    public Samochod getSamochod() {
        return samochod;
    }

    public Klient getKlient() {
        return klient;
    }

    public Lokalizacja getLokalizacjaOdbioru() {
        return lokalizacjaOdbioru;
    }

    public Lokalizacja getLokalizacjaZwrotu() {
        return lokalizacjaZwrotu;
    }

    public double getKwotaRezerwacji() {
        return kwotaRezerwacji;
    }

    public StatusPlatnosci getStatusPlatnosci() {
        return statusPlatnosci;
    }

    public String getNumerRezerwacji() {
        return numerRezerwacji;
    }

}
