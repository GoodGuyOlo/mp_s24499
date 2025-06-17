package com.wypozyczalnia.mas_project.model;

import java.util.Date;

/* todo
    - wywalić dataPlanowana i żeby były po prostu: dataRozpoczecia i dataZakonczenia.
    - wywalić serwisanta - nie potrzebna nam ta informacja
    - Czy potrzebne jest tyle konstruktorów?
    - zmienić nazwę metody zmienDatePlanowana na zmienDateSerwisu
    - uporządkować metody i zmienne
 */

public class Serwis {
    public static final String TYP_SERWIS = "Serwis";
    public static final String TYP_PRZEGLAD_TECHNICZNY = "Przegląd techniczny";
    public static final String TYP_CZYSZCZENIE = "Czyszczenie";

    private Date dataSerwisu;
    private Date dataPlanowana;
    private Date dataZakonczenia;
    private StatusSerwisu statusSerwisu;
    private String typSerwisu;
    private String opis;
    private double koszt;
    private String serwisant;
    private Samochod samochod;

    public Serwis(Date dataPlanowana, String typSerwisu, String opis, Samochod samochod) {
        this.dataPlanowana = dataPlanowana;
        this.typSerwisu = typSerwisu;
        this.opis = opis;
        this.samochod = samochod;
        this.statusSerwisu = StatusSerwisu.ZAPLANOWANY;
        this.koszt = 0.0;
    }

    public Serwis(Date dataSerwisu, String typSerwisu, String opis, Samochod samochod, String serwisant) {
        this.dataSerwisu = dataSerwisu;
        this.typSerwisu = typSerwisu;
        this.opis = opis;
        this.samochod = samochod;
        this.serwisant = serwisant;
        this.statusSerwisu = StatusSerwisu.W_TRAKCIE;
    }

    public Serwis(Date dataSerwisu, Date dataZakonczenia, String typSerwisu, 
                  String opis, double koszt, String serwisant, Samochod samochod) {
        this.dataSerwisu = dataSerwisu;
        this.dataZakonczenia = dataZakonczenia;
        this.typSerwisu = typSerwisu;
        this.opis = opis;
        this.koszt = koszt;
        this.serwisant = serwisant;
        this.samochod = samochod;
        this.statusSerwisu = StatusSerwisu.ZAKONCZONY;
    }

    public Date getDataSerwisu() {
        return dataSerwisu;
    }

    public Date getDataPlanowana() {
        return dataPlanowana;
    }

    public Date getDataZakonczenia() {
        return dataZakonczenia;
    }

    public StatusSerwisu getStatusSerwisu() {
        return statusSerwisu;
    }

    public String getTypSerwisu() {
        return typSerwisu;
    }

    public String getOpis() {
        return opis;
    }

    public double getKoszt() {
        return koszt;
    }

    public String getSerwisant() {
        return serwisant;
    }

    public Samochod getSamochod() {
        return samochod;
    }

    public void rozpocznijSerwis(String serwisant) {
        if (this.statusSerwisu == StatusSerwisu.ZAPLANOWANY) {
            this.statusSerwisu = StatusSerwisu.W_TRAKCIE;
            this.dataSerwisu = new Date();
            this.serwisant = serwisant;
        } else {
            throw new IllegalStateException("Nie można rozpocząć serwisu, który nie jest zaplanowany");
        }
    }

    public void zakonczSerwis(double koszt, String dodatkowyOpis) {
        if (this.statusSerwisu == StatusSerwisu.W_TRAKCIE) {
            this.statusSerwisu = StatusSerwisu.ZAKONCZONY;
            this.dataZakonczenia = new Date();
            this.koszt = koszt;
            if (dodatkowyOpis != null && !dodatkowyOpis.isEmpty()) {
                this.opis += "\nDodatkowe informacje: " + dodatkowyOpis;
            }
        } else {
            throw new IllegalStateException("Nie można zakończyć serwisu, który nie jest w trakcie");
        }
    }

    public void zmienDatePlanowana(Date nowaData) {
        if (this.statusSerwisu == StatusSerwisu.ZAPLANOWANY) {
            this.dataPlanowana = nowaData;
        } else {
            throw new IllegalStateException("Nie można zmienić daty serwisu, który już się rozpoczął");
        }
    }

    public void anulujSerwis() {
        if (this.statusSerwisu == StatusSerwisu.ZAPLANOWANY) {
            // Nie usuwamy serwisu, ale możemy oznaczać go jako anulowany w jakiś sposób
            // Tutaj po prostu zakładamy, że nie jest już częścią listy planowanych serwisów
        } else {
            throw new IllegalStateException("Nie można anulować serwisu, który już się rozpoczął");
        }
    }
}
