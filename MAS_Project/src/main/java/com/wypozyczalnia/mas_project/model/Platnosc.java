package com.wypozyczalnia.mas_project.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/* todo
    - Czy w konstruktorze powinno być przypisany statusPlatnosci?
    - W generujFakture() nie powinno być NIP do podania, ponieważ nie każdy klient posiada firmę. Tutaj wystarczy podać numer rezerwacji.
    - W refunduj nie jest potrzebny powód, tutaj tez jako argument numer rezerwacji a powód zwrotu pieniędzy dla całego projektu jest niepotrzebny
    - uporządkować metody i zmienne
*/

public class Platnosc {
    private String numerTransakcji;
    private double kwota;
    private MetodaPlatnosci metodaPlatnosci;
    private StatusPlatnosci statusPlatnosci;
    private Date dataPlatnosci;

    private Wypozyczenie wypozyczenie;
    private String numerFaktury;
    private Date dataFaktury;
    private Date dataRefundacji;

    public Platnosc(Wypozyczenie wypozyczenie, double kwota, MetodaPlatnosci metodaPlatnosci) {
        this.wypozyczenie = wypozyczenie;
        this.kwota = kwota;
        this.metodaPlatnosci = metodaPlatnosci;
        this.statusPlatnosci = StatusPlatnosci.OCZEKUJACA;
        this.dataPlatnosci = new Date();
        this.numerTransakcji = generujNumerTransakcji();
    }

    private String generujNumerTransakcji() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataPart = sdf.format(this.dataPlatnosci);
        int randomPart = (int) (Math.random() * 10000);

        return "TR-" + dataPart + "-" + String.format("%04d", randomPart);
    }

    public boolean zmienStatus(StatusPlatnosci nowyStatus) {
        if (this.statusPlatnosci == StatusPlatnosci.ODRZUCONA && nowyStatus != StatusPlatnosci.OCZEKUJACA) {
            throw new IllegalStateException("Odrzucona płatność może zostać tylko ponownie ustawiona jako oczekująca");
        }

        if (this.statusPlatnosci == StatusPlatnosci.ZATWIERDZONA && nowyStatus != StatusPlatnosci.OCZEKUJACA) {
            throw new IllegalStateException("Zatwierdzona płatność może zostać tylko ponownie ustawiona jako oczekująca");
        }

        this.statusPlatnosci = nowyStatus;

        if (nowyStatus == StatusPlatnosci.ZATWIERDZONA) {
            this.dataPlatnosci = new Date();
        }

        return true;
    }

    public String generujFakture(String nip) {
        if (this.statusPlatnosci != StatusPlatnosci.ZATWIERDZONA) {
            throw new IllegalStateException("Faktura może być wygenerowana tylko dla zatwierdzonej płatności");
        }

        if (this.numerFaktury != null) {
            throw new IllegalStateException("Faktura została już wygenerowana dla tej płatności");
        }

        this.dataFaktury = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataPart = sdf.format(this.dataFaktury);
        int randomPart = (int) (Math.random() * 1000);

        this.numerFaktury = "FV/" + dataPart + "/" + String.format("%03d", randomPart);

        return this.numerFaktury;
    }

    public boolean refunduj(String powod) {
        if (this.statusPlatnosci != StatusPlatnosci.ZATWIERDZONA) {
            throw new IllegalStateException("Tylko zatwierdzona płatność może zostać zrefundowana");
        }

        if (this.dataRefundacji != null) {
            throw new IllegalStateException("Ta płatność została już zrefundowana");
        }

        this.dataRefundacji = new Date();
        this.statusPlatnosci = StatusPlatnosci.ZATWIERDZONA; // Zmiana statusu po refundacji na ZATWIERDZONA

        return true;
    }

    public String getNumerTransakcji() {
        return numerTransakcji;
    }

    public double getKwota() {
        return kwota;
    }

    public MetodaPlatnosci getMetodaPlatnosci() {
        return metodaPlatnosci;
    }

    public StatusPlatnosci getStatusPlatnosci() {
        return statusPlatnosci;
    }

    public Date getDataPlatnosci() {
        return dataPlatnosci;
    }

    public Wypozyczenie getWypozyczenie() {
        return wypozyczenie;
    }

    public String getNumerFaktury() {
        return numerFaktury;
    }

    public Date getDataFaktury() {
        return dataFaktury;
    }

    public Date getDataRefundacji() {
        return dataRefundacji;
    }

}
